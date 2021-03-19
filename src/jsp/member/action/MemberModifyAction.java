package jsp.member.action;

import javax.servlet.http.*;

import jsp.common.action.*;
import jsp.member.model.*;
/*
ModifyForm.jsp에서 변경할 회원정보를 입력 후 입력회원정보를 가져와서 수정 처리를 하는 action 클래스
*/
public class MemberModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		ActionForward forward = new ActionForward();
		
		MemberDAO dao = MemberDAO.getInstance();
		
		//세션이 가지고 있는 로그인 id정보를 가져온다.
		
		HttpSession session = request.getSession();
		String id = session.getAttribute("sessionID").toString();
		
		MemberBean member = new MemberBean();
		String password = request.getParameter("password");
		String mail1 = request.getParameter("mail1");
		String mail2 = request.getParameterValues("mail2")[0];
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		
		member.setId(id);
		member.setPassword(password);
		member.setMail1(mail1);
		member.setMail2(mail2);
		member.setPhone(phone);
		member.setAddress(address);
		
		//수정처리
		dao.updateMember(member);
		
		forward.setRedirect(true);
		forward.setNextPath("Result.do");
		
		session.setAttribute("msg", "0");  //회원 수정성공시 0이라는 메시지를 세션에 담는다.
		
		return forward;
	}

}
