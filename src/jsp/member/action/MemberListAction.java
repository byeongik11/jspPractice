package jsp.member.action;

import java.util.*;

import javax.servlet.http.*;

import jsp.common.action.*;
import jsp.member.model.*;
//관리자로 로그인시 모든 회원정보를 보여주는 action
public class MemberListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = new ActionForward();
		
		//회원정보를 가져옴
		
		MemberDAO dao = MemberDAO.getInstance();
		ArrayList<MemberBean> memberList = dao.getMemberList();
		
		// MemberListForm.jsp에 회원정보를 전달하기위해 request에 MemberBean을 세팅
		request.setAttribute("memberList", memberList);
		
		//request를 유지해야 되므로 forward방식으로 보낸다.
		forward.setRedirect(false);
		forward.setNextPath("MemberListForm.do");
		return forward;
	}

}
