package jsp.guestbook.action;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import jsp.common.action.*;

/**
 * Servlet implementation class MemberController
 */
@WebServlet("*.ge")
public class GuestbookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GuestbookController() {
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
		
		String form = "MainForm.jsp?contentPage=guestbook/";
		
		
		try {
			if(command.equals("GuestbookForm.ge")) {		// 방명록 입력 화면으로 이동
				forward = new ActionForward();
				forward.setRedirect(false);
				forward.setNextPath(form + "GuestbookForm.jsp");
				
			} else if(command.equals("BoardWriteForm.bo")) {		//게시판 글쓰기 화면으로 
				forward = new ActionForward();
				forward.setRedirect(false);
				forward.setNextPath(form + "BoardWriteForm.jsp");
			
			} else if(command.equals("BoardDetailForm.bo")) {		//게시판 상세글 보기 화면으로
				forward = new ActionForward();
				forward.setRedirect(false);
				forward.setNextPath(form + "BoardDetailForm.jsp");
			
			} else if(command.equals("BoardReplyForm.bo")) {		//게시글 답글 작성 화면으로
				forward = new ActionForward();
				forward.setRedirect(false);
				forward.setNextPath(form + "BoardReplyForm.jsp");
				
			} else if(command.equals("BoardUpdateForm.bo")) {		//글 수정 화면으로
				forward = new ActionForward();
				forward.setRedirect(false);
				forward.setNextPath(form + "BoardUpdateForm.jsp");
				
				
				
			} else if(command.equals("GuestbookWriteAction.ge")) {		//방명록 글 등록 처리
				action = new GuestbookWriteAction();
				action.execute(request, response);
				
			} else if(command.equals("GuestbookListAction.ge")) {		//방명록 목록 보기 처리
				action = new GuestbookListAction();
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
				
			
			System.out.println("경로 " + forward.getNextPath());
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
