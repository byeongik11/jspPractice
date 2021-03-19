package jsp.member.action;

import javax.servlet.http.*;

import jsp.common.action.*;
import jsp.member.model.*;
/*
	회원가입을 처리하는 action 클래스
	JoinForm.jsp에서 넘겨받은 정보를 이용하여 회원가입을 처리한다.
*/
public class MemberJoinAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8");		//인코딩 설정
		
		ActionForward forward = new ActionForward();
		
		MemberDAO dao = MemberDAO.getInstance();
		
		MemberBean member = new MemberBean();
		// JoinForm.jsp에서 name에 대한 값을 파라미터로 받아와서 변수에 저장
		
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");
		String birthyy = request.getParameter("birthyy");
		String birthmm = request.getParameterValues("birthmm")[0];
		System.out.println("birthmm : " + birthmm);
		String birthdd = request.getParameter("birthdd");
		String mail1 = request.getParameter("mail1");
		String mail2 = request.getParameterValues("mail2")[0];
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		
		member.setId(id);
		member.setPassword(password);
		member.setName(name);
		member.setGender(gender);
		member.setBirthyy(birthyy);
		member.setBirthmm(birthmm);
		member.setBirthdd(birthdd);
		member.setMail1(mail1);
		member.setMail2(mail2);
		member.setPhone(phone);
		member.setAddress(address);
		
		//회원가입 실행
		dao.insertMember(member);
		
		//가입성공
		forward.setRedirect(true);
		forward.setNextPath("Result.do");
		
		
		//가입성공 메시지를 세션에 담는다.
		request.getSession().setAttribute("msg", "1");
		
		
		return forward;
	}

}
