package jsp.guestbook.model;

import java.sql.*;
import java.util.*;

import jsp.common.util.*;


public class GuestbookDAO 
{
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private static GuestbookDAO instance;
	
	private GuestbookDAO(){}
	public static GuestbookDAO getInstance(){
		if(instance==null)
			instance=new GuestbookDAO();
		return instance;
	}
	
	// 시퀀스를 가져온다.
	public int getSeq()
	{
		int result = 1;
		
		try {
			conn = DBConnection.getConnection();
			
			// 시퀀스 값을 가져온다. (DUAL : 시퀀스 값을 가져오기위한 임시 테이블)
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT guestbook_no_seq.NEXTVAL FROM DUAL");
			
			pstmt = conn.prepareStatement(sql.toString());
			// 쿼리 실행
			rs = pstmt.executeQuery();
			
			if(rs.next())	result = rs.getInt(1);

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		
		close();
		return result;	
	} // end getSeq
	
	
	public boolean guestbookInsert(GuestbookBean guestbook) throws SQLException {
		
		boolean result = false;
		
		try {
			
			conn = DBConnection.getConnection();
			
			//수동커밋
			conn.setAutoCommit(false);
			
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO GUESTBOOK");
			sql.append("(guestbook_no, guestbook_id, guestbook_password, guestbook_content");
			sql.append(" ,guestbook_group, guestbook_parent, guestbook_date)");
			sql.append("VALUES(?,?,?,?,?,?,sysdate)");
			
			int no = guestbook.getGuestbook_no();	//글번호
			int group = guestbook.getGuestbook_group(); //그룹번호
			int parent = guestbook.getGuestbook_parent();//부모글번호
			
			if(parent == 0) {
				group = no;		//부모글일경우 그룹번호와 글번호가 동일
			}
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, no);
			pstmt.setString(2, guestbook.getGuestbook_id());
			pstmt.setString(3, guestbook.getGuestbook_password());
			pstmt.setString(4, guestbook.getGuestbook_content());
			pstmt.setInt(5, group);
			pstmt.setInt(6, parent);
			
			int flag = pstmt.executeUpdate();
			
			if(flag > 0) {
				result = true;
				conn.commit();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			close();
		}
		
		
		return result;
		
	}
	
	public int getGuestbookCount() {		//방명록 목록 갯수구하기
		int result = 0;
		
		try {
			conn = DBConnection.getConnection();
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT COUNT(*) FROM guestbook");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			close();
		}
		return result;
	}
	
	
	public ArrayList<GuestbookBean> getGuestbookList(int pageNum) { 
		ArrayList<GuestbookBean> list = new ArrayList<GuestbookBean>();
		
		try {
			conn = DBConnection.getConnection();
			
			StringBuffer sql = new StringBuffer();
            sql.append("SELECT * FROM");
            sql.append(" (SELECT  ROWNUM AS rnum, data.* FROM ");
            sql.append("    (SELECT LEVEL, guestbook_no, guestbook_id,");
            sql.append("            guestbook_password, guestbook_content,");
            sql.append("            guestbook_group, guestbook_parent, guestbook_date");
            sql.append("    FROM GUESTBOOK");
            sql.append("    START WITH guestbook_parent = 0");
            sql.append("    CONNECT BY PRIOR guestbook_no = guestbook_parent");
            sql.append("    ORDER SIBLINGS BY guestbook_group desc)");              
            sql.append(" data) ");
            sql.append("WHERE rnum>=? and rnum<=?");

            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setInt(1, pageNum);
            pstmt.setInt(2, pageNum+4);
            
            rs = pstmt.executeQuery();
            
            while(rs.next()) {
            	GuestbookBean guestbook = new GuestbookBean();
            	guestbook.setGuestbook_no(rs.getInt("guestbook_no"));
            	guestbook.setGuestbook_id(rs.getString("guestbook_id"));
            	guestbook.setGuestbook_password(rs.getString("guestbook_password"));
            	guestbook.setGuestbook_content(rs.getString("guestbook_content"));
            	guestbook.setGuestbook_group(rs.getInt("guestbook_group"));
            	guestbook.setGuestbook_parent(rs.getInt("guestbook_parent"));
            	guestbook.setGuestbook_date(rs.getDate("guestbook_date"));
            	list.add(guestbook);
            }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}
	
	//방명록에서 하나의 정보를 가져온다.
	public GuestbookBean getGuestbook(int g_num) {
		GuestbookBean guestbook = null;
		
		try {
			conn = DBConnection.getConnection();
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM guestbook WHERE guestbook_no = ?");
			
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, g_num);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				guestbook = new GuestbookBean();
				guestbook.setGuestbook_level(rs.getInt("LEVEL"));
				guestbook.setGuestbook_no(rs.getInt("guestbook_no"));
				guestbook.setGuestbook_id(rs.getString("guestbook_id"));
				guestbook.setGuestbook_password(rs.getString("guestbook_password"));
				guestbook.setGuestbook_content(rs.getString("guestbook_content"));
				guestbook.setGuestbook_group(rs.getInt("guestbook_group"));
				guestbook.setGuestbook_parent(rs.getInt("guestbook_parent"));
				guestbook.setGuestbook_date(rs.getDate("guestbook_date"));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return guestbook;
	}
	
	
	
	// DB 자원해제
	private void close()
	{
		try {
			if ( pstmt != null ){ pstmt.close(); pstmt=null; }
			if ( rs!=null) {rs.close(); rs=null;}
			if ( conn != null ){ conn.close(); conn=null;	}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	} // end close()
	
	
	
	
	
	
}
