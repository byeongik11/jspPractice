package jsp.member.action;

import javax.servlet.http.*;

import jsp.common.action.*;
import jsp.member.model.*;

public class MemberModifyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ActionForward forward = new ActionForward();
		
		//세션이 가지고 있는 로그인id 정보를 가져온다.
		HttpSession session = request.getSession();
		String id = session.getAttribute("sessionID").toString();
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberBean member = dao.getUserInfo(id);
		
		//ModifyForm.jsp의 회원정보를 전달하기 위해 request에 bean 세팅
		
		request.setAttribute("memberInfo", member);
		
		forward.setRedirect(false);
		forward.setNextPath("ModifyForm.do"); 	//회원수정화면으로 
		return forward;
	}

}
