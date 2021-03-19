package jsp.member.model;

import java.sql.*;
import java.sql.Date;
import java.util.*;

import jsp.common.util.*;
public class MemberDAO {

	private static MemberDAO instance;
	
	private MemberDAO() {
		
	}
	
	public static MemberDAO getInstance() {
		if(instance == null) {
			instance = new MemberDAO();
		}
		return instance;
	}
	
	public Date StringToDate(MemberBean member) {
		
		String year = member.getBirthyy();
		String month = member.getBirthmm();
		String day = member.getBirthdd();
		
		Date birthday = Date.valueOf(year + "-" + month + "-" + day);
		
		
		return birthday;
		
	}
	
	public void insertMember(MemberBean member) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO jsp_member VALUES(?,?,?,?,?,?,?,?,sysdate)";
		try {
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false);
			
			StringToDate(member);
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getName());
			pstmt.setString(4, member.getGender());
			pstmt.setDate(5, StringToDate(member));
			pstmt.setString(6, member.getMail1() + "@" + member.getMail2());
			pstmt.setString(7, member.getPhone());
			pstmt.setString(8, member.getAddress());
			
			pstmt.executeUpdate();
			
			conn.commit();
			System.out.println("commit");
			
		}catch (Exception e) {
			System.out.println("rollback");
			conn.rollback();
			e.printStackTrace();
		}finally {
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
	}
	
	public MemberBean getUserInfo(String id) {				//id로 회원검색
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberBean member = null;
		String sql = "SELECT * FROM jsp_member WHERE id=?";
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String birthday = rs.getDate("birth").toString();
				String year = birthday.substring(0,4);
				String month = birthday.substring(5,7);
				String day = birthday.substring(8,10);
				
				//이메일을 @기준으로 자름
				String mail = rs.getString("mail");
				int idx = mail.indexOf("@");
				String mail1 = mail.substring(0,idx);
				String mail2 = mail.substring(idx+1);
				
				member = new MemberBean();
				member.setId(rs.getString("id"));
				member.setPassword(rs.getString("password"));
				member.setName(rs.getString("name"));
				member.setGender(rs.getString("gender"));
				member.setBirthyy(year);
				member.setBirthmm(month);
				member.setBirthdd(day);
				member.setMail1(mail1);
				member.setMail2(mail2);
				member.setPhone(rs.getString("phone"));
				member.setAddress(rs.getString("address"));
				member.setReg(rs.getTimestamp("reg"));
			}
		} catch (Exception e) {
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
		return member;
	}
	
	public void updateMember(MemberBean member) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "UPDATE jsp_member SET password=?,mail=?,phone=?,address=? WHERE id=?";
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			conn.setAutoCommit(false);
			
			pstmt.setString(1, member.getPassword());
			pstmt.setString(2, member.getMail1()+"@"+member.getMail2());
			pstmt.setString(3, member.getPhone());
			pstmt.setString(4, member.getAddress());
			pstmt.setString(5, member.getId());
			
			pstmt.executeUpdate();
			
			conn.commit();
			
			
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
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
		
	}
	
	@SuppressWarnings("resource")
	public int deleteMember(String id, String pw) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String dbpw = "";		//db의 password 값을 임시보관할 변수
		int x= -1;
		String slt_pw = "SELECT password FROM jsp_member WHERE id=?";
		String sql = "DELETE FROM jsp_member WHERE id = ?";
		
		try {
			conn = DBConnection.getConnection();
			conn.setAutoCommit(false);
			
			pstmt = conn.prepareStatement(slt_pw);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dbpw = rs.getString("password");
				if(dbpw.equals(pw)) {			//db에 있는 pw와 탈퇴폼에서의 pw를 비교
					pstmt = conn.prepareStatement(sql);  
					pstmt.setString(1, id);
					pstmt.executeUpdate();
					conn.commit();
					x = 1;
				} else {
					x = 0; //비밀번호 다름
					conn.rollback();
				}
			}
		} catch (Exception e) {
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
		
		return x;
	}
	
	public int loginCheck(String id, String pw) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String dbPW = "";
		int x = -1;
		String sql = "SELECT password FROM jsp_member WHERE id=?";
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dbPW = rs.getString("password");
				
				if(dbPW.equals(pw)) {
					x = 1;	//인증성공
					System.out.println(x);
				} else {
					x = 0; //인증실패
					System.out.println(x);
				}
				
			} else {
				x = -1; // 아이디 없을 경우
				System.out.println(x);
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
		
		return x;
	}
	// 모든 회원정보를 가져옴
	// 회원 list를 리턴
	public ArrayList<MemberBean> getMemberList() {
		ArrayList<MemberBean> memberList = new ArrayList<MemberBean>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberBean member = null;
		
		try {
			StringBuffer query = new StringBuffer();
			query.append("SELECT * FROM JSP_MEMBER");
			
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(query.toString());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				member = new MemberBean();
				
				String id = rs.getString("id");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				String birth = rs.getDate("birth").toString();
				String mail = rs.getString("mail");
				String phone = rs.getString("phone");
				String address = rs.getString("address");
				Timestamp reg = rs.getTimestamp("reg");
				
				member.setId(id);
				member.setPassword(password);
				member.setName(name);
				member.setGender(gender);
				member.setBirthyy(birth);
				member.setMail1(mail);
				member.setPhone(phone);
				member.setAddress(address);
				member.setReg(reg);
				memberList.add(member);
				
			} 
		} catch (Exception e) {
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
					pstmt.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return memberList;
	}
}
