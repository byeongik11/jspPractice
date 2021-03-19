package jsp.visit.model;

import java.sql.*;

import jsp.common.util.*;

// 방문자 관련 처리 dao
public class VisitCountDAO {
	
	private static VisitCountDAO instance;
	
	public VisitCountDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public static VisitCountDAO getInstance() {
		if(instance == null) {
			instance = new VisitCountDAO();
		}
		return instance;
	}

	
	// 총방문자수를 증가시킨다.
	public void setTotalCount() throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			StringBuffer sql = new StringBuffer(); //버퍼를 사용
			sql.append("INSERT INTO visit (v_date) VALUES (sysdate)");
			// 방문자수를 증가시키려고 테이블에 현재 날짜 값을 추가시킴
			// 커넥션 가져옴
			
			conn = DBConnection.getConnection();
			
			conn.setAutoCommit(false);
			
			pstmt = conn.prepareStatement(sql.toString());
			
			pstmt.executeUpdate();
			
			conn.commit();
			
		}catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(conn!=null) {
					conn.close();
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	// 총 방문자수를 가져온다. totalCount 총 방문자수를 리턴한다.
	public int getTotalCount() {		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int totalCount = 0;
		
		try {
	//visit 테이블의 데이터 수를 가져온다. 데이터수= 총 방문자수
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT count(*) AS TotalCnt FROM visit");
			
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql.toString());	// 스트링버퍼를 쓰면 문자열로 변환해야함
			rs = pstmt.executeQuery();
			
			// 방문자수를 변수에 담음
			if(rs.next()) {
				totalCount = rs.getInt("TotalCnt");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn!=null) {
					conn.close();
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return totalCount;
	}
	
/*
	오늘 방문자수를 가져온다.
	todayCount : 오늘 방문자
*/	
	public int getTodayCount() {					
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int todayCount = 0;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT COUNT(*) AS TodayCnt FROM VISIT ");
			sql.append("WHERE TO_DATE(V_DATE,'YYYY-MM-DD') = TO_DATE(sysdate,'YYYY-MM-DD') ");
			
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				todayCount = rs.getInt("TodayCnt");
				System.out.println("오늘방문자수 : " + todayCount);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn!=null) {
					conn.close();
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return todayCount;
	}

}
