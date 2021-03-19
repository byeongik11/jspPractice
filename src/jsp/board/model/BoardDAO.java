package jsp.board.model;

import java.sql.*;
import java.util.*;

import jsp.common.util.*;

public class BoardDAO {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private static BoardDAO instance;
	
	private BoardDAO() {}

	
	public static BoardDAO getInstance() {
		if(instance == null) {
			instance = new BoardDAO();
		}
		return instance;
	}
	
	
	//시퀀스를 가져온다.
	public int getSeq() {
		int result = 1;
		
		try {
			conn = DBConnection.getConnection();
			
			//시퀀스 값을 가져온다.
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT board_num.nextval FROM dual");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	//글삽입
	public boolean boardInsert(BoardBean board) throws SQLException {
		boolean result = false;
		
		try {
			conn = DBConnection.getConnection();
			
			StringBuffer sql = new StringBuffer();
			
			sql.append("INSERT INTO member_board");
			sql.append("(board_num,board_id,board_subject,board_content,board_file");
			sql.append(",board_re_ref,board_re_lev,board_re_seq,board_count,board_date)");
			sql.append("VALUES(?,?,?,?,?,?,?,?,?,sysdate)");
			// 시퀀스 값을 글번호와 그룹번호로 사용
			int num = board.getBoard_num();
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, num);
			pstmt.setString(2, board.getBoard_id());
			pstmt.setString(3, board.getBoard_subject());
			pstmt.setString(4, board.getBoard_content());
			pstmt.setString(5, board.getBoard_file());
			
			if(board.getBoard_re_seq()==0) {		//re_seq==0은 답변글이 없는 경우, 부모글일경우
				pstmt.setInt(6, num);
			} else {
				pstmt.setInt(6, board.getBoard_re_ref());
			}
			pstmt.setInt(7, board.getBoard_re_lev());
			pstmt.setInt(8, board.getBoard_re_seq());
			pstmt.setInt(9, 0);
			
			int flag = pstmt.executeUpdate();
			
			if(flag > 0) {
				result = true;
				conn.commit();
				System.out.println("commit");
			}
		} catch (Exception e) {
			conn.rollback();
			System.out.println("rollback");
			e.printStackTrace();
		} finally {
			try {
				if(pstmt!=null) {
					pstmt.close();
				}
				
				if(conn!=null) {
					conn.close();
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	//게시글 목록 개수구하기
	public int getBoardListCount(HashMap<String, Object> listOpt) {
		int result = 0;
		String opt = (String)listOpt.get("opt"); //검색옵션(제목,내용,글쓴이 등);
		String condition = (String)listOpt.get("condition");	//검색내용
		
		try {
			conn = DBConnection.getConnection();
			StringBuffer sql = new StringBuffer();
			
			if(opt == null) {		//전체글 갯수, option을 선택하지 않았을 때의 전체 게시글 갯수
				sql.append("SELECT count(*) FROM member_board");
				pstmt = conn.prepareStatement(sql.toString());
				
				// String Buffer를 비움
				sql.delete(0, sql.toString().length());
				
			} else if(opt.equals("0")) {	//제목으로 검색한 글의 갯수
				sql.append("SELECT count(*) FROM member_board WHERE board_subejct like ?");
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1, '%'+condition+'%');
				// String Buffer를 비움
				sql.delete(0, sql.toString().length());
				
			} else if(opt.equals("1")) {	//내용으로 검색한 글의 갯수
				sql.append("SELECT count(*) FROM member_board WHERE board_content like ?");
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1, '%'+condition+'%');
				// String Buffer를 비움
				sql.delete(0, sql.toString().length());
				
			} else if(opt.equals("2")) {	//제목+내용으로 검색한 글의 갯수
				sql.append("SELECT count(*) FROM member_board");
				sql.append("WHERE board_subject like ? or board_content like ?");
				pstmt.setString(1, '%'+condition+'%');
				pstmt.setString(2, '%'+condition+'%');
				
				sql.delete(0, sql.toString().length());

			} else if(opt.equals("3")) {	//글쓴이/작성자로 검색한 글의 개수
				sql.append("SELECT count(*) FROM member_board");
				sql.append("WHERE board_id like ?");
				pstmt.setString(1, '%'+condition+'%');

				sql.delete(0, sql.toString().length());

				
			}
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) {
					rs.close();
				}
				if(pstmt!=null) {
					pstmt.close();
				}
				if(conn!=null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	//글 목록 가져오기
	public ArrayList<BoardBean> getBoardList(HashMap<String, Object> listOpt) {
		ArrayList<BoardBean> list = new ArrayList<BoardBean>();
		
		String opt = (String)listOpt.get("opt");	//검색옵션(제목, 내용, 글쓴이 등)
		String condition = (String)listOpt.get("condition"); //검색내용
		int start = (Integer)listOpt.get("start"); 			 //현재 페이지 번호
		
		try {
			conn = DBConnection.getConnection();
			StringBuffer sql = new StringBuffer();
			
			// 글목록 전체를 보여줄때
			
			if(opt == null) {
				// board_re_ref 그룹번호의 내림차순 정렬 후 동일한 그룹번호 일때는 
				// board_re_seq 답변글 순서의 오름차순으로 정렬한 후
				// 10개의 글을 한번에 보여주는 쿼리
				
				sql.append("select * from ");
                sql.append("(select rownum rnum, BOARD_NUM, BOARD_ID, BOARD_SUBJECT");
                sql.append(", BOARD_CONTENT, BOARD_FILE, BOARD_COUNT, BOARD_RE_REF");
                sql.append(", BOARD_RE_LEV, BOARD_RE_SEQ, BOARD_DATE ");
                sql.append("FROM");
                sql.append(" (select * from MEMBER_BOARD order by BOARD_RE_REF desc, BOARD_RE_SEQ asc)) ");
                sql.append("where rnum>=? and rnum<=?");

                pstmt =conn.prepareStatement(sql.toString());
                pstmt.setInt(1, start);
                pstmt.setInt(2, start+9);
                
                sql.delete(0, sql.toString().length());
                
			}  else if(opt.equals("0")) // 제목으로 검색
            {
                sql.append("select * from ");
                sql.append("(select rownum rnum, BOARD_NUM, BOARD_ID, BOARD_SUBJECT");
                sql.append(", BOARD_CONTENT, BOARD_FILE, BOARD_DATE, BOARD_COUNT");
                sql.append(", BOARD_RE_REF, BOARD_RE_LEV, BOARD_RE_SEQ ");
                sql.append("FROM ");
                sql.append("(select * from MEMBER_BOARD where BOARD_SUBJECT like ? ");
                sql.append("order BY BOARD_RE_REF desc, BOARD_RE_SEQ asc)) ");
                sql.append("where rnum>=? and rnum<=?");
                
                pstmt = conn.prepareStatement(sql.toString());
                pstmt.setString(1, "%"+condition+"%");
                pstmt.setInt(2, start);
                pstmt.setInt(3, start+9);
                
                sql.delete(0, sql.toString().length());
            }
            else if(opt.equals("1")) // 내용으로 검색
            {
                sql.append("select * from ");
                sql.append("(select rownum rnum, BOARD_NUM, BOARD_ID, BOARD_SUBJECT");
                sql.append(", BOARD_CONTENT, BOARD_FILE, BOARD_DATE, BOARD_COUNT");
                sql.append(", BOARD_RE_REF, BOARD_RE_LEV, BOARD_RE_SEQ ");
                sql.append("FROM ");
                sql.append("(select * from MEMBER_BOARD where BOARD_CONTENT like ? ");
                sql.append("order BY BOARD_RE_REF desc, BOARD_RE_SEQ asc)) ");
                sql.append("where rnum>=? and rnum<=?");
                
                pstmt = conn.prepareStatement(sql.toString());
                pstmt.setString(1, "%"+condition+"%");
                pstmt.setInt(2, start);
                pstmt.setInt(3, start+9);
                
                sql.delete(0, sql.toString().length());
            }
            else if(opt.equals("2")) // 제목+내용으로 검색
            {
                sql.append("select * from ");
                sql.append("(select rownum rnum, BOARD_NUM, BOARD_ID, BOARD_SUBJECT");
                sql.append(", BOARD_CONTENT, BOARD_FILE, BOARD_DATE, BOARD_COUNT");
                sql.append(", BOARD_RE_REF, BOARD_RE_LEV, BOARD_RE_SEQ ");
                sql.append("FROM ");
                sql.append("(select * from MEMBER_BOARD where BOARD_SUBJECT like ? OR BOARD_CONTENT like ? ");
                sql.append("order BY BOARD_RE_REF desc, BOARD_RE_SEQ asc)) ");
                sql.append("where rnum>=? and rnum<=?");
                
                pstmt = conn.prepareStatement(sql.toString());
                pstmt.setString(1, "%"+condition+"%");
                pstmt.setString(2, "%"+condition+"%");
                pstmt.setInt(3, start);
                pstmt.setInt(4, start+9);
                
                sql.delete(0, sql.toString().length());
            }
            else if(opt.equals("3")) // 글쓴이로 검색
            {
                sql.append("select * from ");
                sql.append("(select rownum rnum, BOARD_NUM, BOARD_ID, BOARD_SUBJECT");
                sql.append(", BOARD_CONTENT, BOARD_FILE, BOARD_DATE, BOARD_COUNT");
                sql.append(", BOARD_RE_REF, BOARD_RE_LEV, BOARD_RE_SEQ ");
                sql.append("FROM ");
                sql.append("(select * from MEMBER_BOARD where BOARD_ID like ? ");
                sql.append("order BY BOARD_RE_REF desc, BOARD_RE_SEQ asc)) ");
                sql.append("where rnum>=? and rnum<=?");
                
                pstmt = conn.prepareStatement(sql.toString());
                pstmt.setString(1, "%"+condition+"%");
                pstmt.setInt(2, start);
                pstmt.setInt(3, start+9);
                
                sql.delete(0, sql.toString().length());
            }
            
            rs = pstmt.executeQuery();
            while(rs.next())
            {
                BoardBean board = new BoardBean();
                board.setBoard_num(rs.getInt("BOARD_NUM"));
                board.setBoard_id(rs.getString("BOARD_ID"));
                board.setBoard_subject(rs.getString("BOARD_SUBJECT"));
                board.setBoard_content(rs.getString("BOARD_CONTENT"));
                board.setBoard_file(rs.getString("BOARD_FILE"));
                board.setBoard_count(rs.getInt("BOARD_COUNT"));
                board.setBoard_re_ref(rs.getInt("BOARD_RE_REF"));
                board.setBoard_re_lev(rs.getInt("BOARD_RE_LEV"));
                board.setBoard_re_seq(rs.getInt("BOARD_RE_SEQ"));
                board.setBoard_date(rs.getDate("BOARD_DATE"));
                list.add(board);
            }
			
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) {
					rs.close();
				}
				if(pstmt!=null) {
					pstmt.close();
				}
				if(conn!=null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}

	//게시글 상세보기
	public BoardBean getDetail(int boardNum) {
		BoardBean board = null;
		
		try {
			conn = DBConnection.getConnection();
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM member_board WHERE board_num = ?");
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, boardNum);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				board = new BoardBean();
				board.setBoard_num(boardNum);
				board.setBoard_id(rs.getString("board_id"));
				board.setBoard_subject(rs.getString("board_subject"));
				board.setBoard_content(rs.getString("board_content"));
				board.setBoard_file(rs.getString("board_file"));
				board.setBoard_count(rs.getInt("board_count"));
				board.setBoard_re_ref(rs.getInt("board_re_ref"));
				board.setBoard_re_lev(rs.getInt("board_re_lev"));
				board.setBoard_re_seq(rs.getInt("board_re_seq"));
				board.setBoard_date(rs.getDate("board_date"));
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) {
					rs.close();
				}
				if(pstmt!=null) {
					pstmt.close();
				}
				if(conn!=null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return board;
	}

	//조회수 증가
	public boolean updateCount(int boardNum) throws SQLException {
		boolean result = false;
		
		try {
			conn = DBConnection.getConnection();
			
			StringBuffer sql = new StringBuffer();
			
			sql.append("UPDATE member_board SET board_count = board_count+1");
			sql.append("WHERE board_num =?");
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, boardNum);
			
			int flag = pstmt.executeUpdate();
			if(flag > 0) {
				result = true;
				conn.commit();
			}
		}catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			try {
				if(pstmt!=null) {
					pstmt.close();
				}
				if(conn!=null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}


	public boolean updateReSeq(BoardBean board) throws SQLException {		//답글 순서 seq를 1증가시킴
		boolean result = false;
		
		int ref = board.getBoard_re_ref();	//그룹번호(원본글 번호)
		int seq = board.getBoard_re_seq();	//답변글 순서
		
		try {
			StringBuffer sql = new StringBuffer();
			
			conn = DBConnection.getConnection();	//DB연결
			conn.setAutoCommit(false);  			//수동커밋
			
			//ref(그룹번호)와 seq(답글순서)를 확인하여 원본 글에 다른 답변 글이 잇으면,
			//답변 글 중 답변 글보다 상위에 있는 글의 seq보다 높은 글의 seq값을 1씩 증가시킨다.
			sql.append("UPDATE member_board SET board_re_seq = board_re_seq+1");
			sql.append("WHERE board_re_ref = ? AND board_re_seq > ?");
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, ref);
			pstmt.setInt(2, seq);
			
			int flag = pstmt.executeUpdate();
			
			if(flag > 0) {
				result = true;
				conn.commit();
			} 
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			try {
				
				if(pstmt!=null) {
					pstmt.close();
				}
				
				if(conn!=null) {
					conn.close();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
		
	}

}
