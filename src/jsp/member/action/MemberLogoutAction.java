package jsp.member.action;

import javax.servlet.http.*;

import jsp.common.action.*;

/*로그아웃을 하는 클래스*/
public class MemberLogoutAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = new ActionForward();
		
		//로그아웃시 세션을 삭제
		
//		request.getSession().invalidate();
		request.getSession().removeAttribute("sessionID"); //로그아웃시 방문자수가 증가하게 되므로 sessionID 만 삭제
		
		//로그아웃후 메인화면으로 이동
		forward.setRedirect(true);
		forward.setNextPath("MainForm.do");
		return forward;
	}

}
