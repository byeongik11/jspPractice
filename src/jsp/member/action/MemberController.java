package jsp.member.action;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import jsp.common.action.*;

/**
 * Servlet implementation class MemberController
 */
@WebServlet("*.do")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request,response);
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		int cmdIdx = requestURI.lastIndexOf("/")+1;
		
		String command = requestURI.substring(cmdIdx);
		
		//URI, Command 확인
		System.out.println("requestURI : " + requestURI);
		System.out.println("command : " + command);
		
		ActionForward forward = null;
		Action action = null;
		
		String form = "MainForm.jsp?contentPage=member/";
		
		
		try {
			if(command.equals("MainForm.do")) {				//메인화면으로
				forward = new ActionForward();
				forward.setRedirect(false);
				forward.setNextPath("MainForm.jsp");
				
			} else if(command.equals("LoginForm.do")) {		//로그인화면으로
				forward = new ActionForward();
				forward.setRedirect(false);
				forward.setNextPath(form + "LoginForm.jsp");
				
			} else if(command.equals("JoinForm.do")) {		//회원가입화면으로
				forward = new ActionForward();
				forward.setRedirect(false);
				forward.setNextPath(form + "JoinForm.jsp");
				
			} else if(command.equals("UserInfoForm.do")) {	//내정보 클릭 - 회원정보화면 이동
				forward = new ActionForward();
				forward.setRedirect(false);
				forward.setNextPath(form + "UserInfoForm.jsp");
				
			} else if(command.equals("ModifyForm.do")) {	//회원정보 수정화면으로
				forward = new ActionForward();
				forward.setRedirect(false);
				forward.setNextPath(form + "ModifyForm.jsp");
				
			} else if(command.equals("DeleteForm.do")) {	//회원삭제화면으로
				forward = new ActionForward();
				forward.setRedirect(false);
				forward.setNextPath(form + "DeleteForm.jsp");
			} else if(command.equals("MemberListForm.do")) {	//관리자 - 전체회원보기 화면으로
				forward = new ActionForward();
				forward.setRedirect(false);
				forward.setNextPath(form + "MemberListForm.jsp");
			
			} else if(command.equals("Result.do")) {		//각종 처리결과 화면으로
				forward = new ActionForward();
				forward.setRedirect(false);
				forward.setNextPath(form + "ResultForm.jsp");
				
				//각종 처리 액션
			} else if(command.equals("MemberLoginAction.do")) {		//로그인 처리
				action  = new MemberLoginAction();
				forward = action.execute(request, response);
				
			} else if(command.equals("MemberLogoutAction.do")) {	//로그아웃 처리
				action = new MemberLogoutAction();
				forward = action.execute(request, response);
				
			} else if(command.equals("MemberJoinAction.do")) {		//회원가입처리
				action = new MemberJoinAction();
				forward = action.execute(request, response);
				
			} else if(command.equals("MemberInfoAction.do")) {		//회원정보화면에 보여줄 정보처리
				action = new MemberInfoAction();
				forward = action.execute(request, response);
				
			} else if(command.equals("MemberModifyAction.do")) {	//회원수정 처리
				action = new MemberModifyAction();
				forward = action.execute(request, response);
				
			} else if(command.equals("MemberModifyFormAction.do")) {//회원수정화면에서 정보처리
				action = new MemberModifyFormAction();
				forward = action.execute(request, response);
				
			} else if(command.equals("MemberDeleteAction.do")) {	//회원삭제 처리
				action = new MemberDeleteAction();
				forward = action.execute(request, response);
				
			} else if(command.equals("MemberListAction.do")) {		//관리자일때 회원리스트 처리
				action = new MemberListAction();
				forward = action.execute(request, response);
			}
				
			
			if(forward != null) {
				if(forward.isRedirect()) {
					response.sendRedirect(forward.getNextPath());
				} else {
					RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getNextPath());
					dispatcher.forward(request, response);
				}
			}
			
		}	catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
