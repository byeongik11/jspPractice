package jsp.visit.action;

import javax.servlet.annotation.*;
import javax.servlet.http.*;

import jsp.visit.model.*;

// 방문자 수를 계산하는 클래스
@WebListener
public class VisitSessionListener implements HttpSessionListener{

	@Override
	public void sessionCreated(HttpSessionEvent sessionEve) {
		// 세션이 새로 생성되면 EXECUTE 메서드가 실행됨
		if(sessionEve.getSession().isNew()) {		//세션이 생성되면 메서드 실행, 새로운 객체인지 아닌지 검사
			execute(sessionEve);
		}
		
		
	}

	private void execute(HttpSessionEvent sessionEve) {
		VisitCountDAO dao = VisitCountDAO.getInstance();
		
		try {
			dao.setTotalCount();		//전체 방문자수 증가
			
			int totalCount = dao.getTotalCount();	//총 방문자수
			
			int todayCount = dao.getTodayCount();  	//일일 방문자수
			
			HttpSession session = sessionEve.getSession();
			
			//세션에 방문자수를 담음
			
			session.setAttribute("totalCount", totalCount);
			session.setAttribute("todayCount", todayCount);
		} catch (Exception e) {
			System.out.println("===== 방문자 카운터 오류 =====");
			e.printStackTrace();
		}
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		
	}

}
