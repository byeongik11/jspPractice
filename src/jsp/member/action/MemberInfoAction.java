package jsp.member.action;

import javax.servlet.http.*;

import jsp.common.action.*;
import jsp.member.model.*;
/*
현재 로그인한 사용자의 회원정보를 보여주는 action 클래스
*/
public class MemberInfoAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = new ActionForward();
		
		// 세션이 가지고 있는 로그인한 id정보를 가져온다.
		HttpSession session = request.getSession();
		String id = session.getAttribute("sessionID").toString();
		
		//아이디에 해당하는 회원정보를 가져온다.
		MemberDAO dao = MemberDAO.getInstance();
		MemberBean member = dao.getUserInfo(id);
		
		//UserInfoForm.jsp에 회원정보를 bean객체를 통해 전달
		request.setAttribute("memberInfo", member);
		
		forward.setRedirect(false);
		forward.setNextPath("UserInfoForm.do");
		
		return forward;
	}

}
