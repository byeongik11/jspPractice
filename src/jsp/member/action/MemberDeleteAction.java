package jsp.member.action;

import java.io.*;

import javax.servlet.http.*;

import jsp.common.action.*;
import jsp.member.model.*;

/*
회원삭제 작업을 처리하는 action 클래스
*/
public class MemberDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = new ActionForward();
		
		//세션이 가지고 있는 로그인 id의 정보를 가져온다.
		HttpSession session = request.getSession();
		String id = session.getAttribute("sessionID").toString();
		String password = request.getParameter("password");
		
		
		try {
			
		MemberDAO dao = MemberDAO.getInstance();
		int check = dao.deleteMember(id, password);
		
		if(check != 1) {
			System.out.println("회원 삭제 실패");
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('회원탈퇴에 실패하였습니다')");
			out.println("location.href='DeleteForm.do'");
			out.println("</script>");
			return null;
		} else {
//			session.invalidate();
			session.removeAttribute("sessionID");
			forward.setRedirect(true);
			forward.setNextPath("Result.do");
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return forward;
	}

}
