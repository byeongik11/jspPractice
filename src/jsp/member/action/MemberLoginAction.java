package jsp.member.action;

import javax.servlet.http.*;

import jsp.common.action.*;
import jsp.member.model.*;

public class MemberLoginAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = new ActionForward();
		HttpSession session = request.getSession();  //세션을 가져온다.
		
		//LoginForm.jsp에서 name에 대한 값을 파라미터로 가져와서 변수에 대입
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		//db에서 아이디,비밀번호 검사
		
		MemberDAO dao = MemberDAO.getInstance();
		
		int check = dao.loginCheck(id, password);
		System.out.println("로그인 체크 " + check);
		if(check==0) {								//비밀번호가 틀릴경우, 로그인화면으로 이동
			
			request.setAttribute("fail", "0");  	// 0이면 로그인실패, request에 담음
			forward.setRedirect(false);
			forward.setNextPath("LoginForm.do");	// 컨트롤러를 거쳐서 로그인화면으로 이동
			
		} else if(check == -1) {					//아이디가 없을 경우, 로그인화면으로 이동
			
			request.setAttribute("fail", "-1");
			forward.setRedirect(false);
			forward.setNextPath("LoginForm.do");
			
		} else {									//로그인 성공시
			session.setAttribute("sessionID", id);	//세션에 아이디 저장
			
			forward.setRedirect(true);
			forward.setNextPath("MainForm.do");		//로그인 성공후 메인화면으로 이동
		}
		return forward;
	}

}
