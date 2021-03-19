package jsp.board.action;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import jsp.common.action.*;
import jsp.member.action.*;

/**
 * Servlet implementation class MemberController
 */
@WebServlet("*.bo")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardController() {
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
		
		String form = "MainForm.jsp?contentPage=board/";
		
		
		try {
			if(command.equals("BoardListForm.bo")) {		//상단 게시판 메뉴 클릭시 글목록 화면으로 이동
				forward = new ActionForward();
				forward.setRedirect(false);
				forward.setNextPath(form + "BoardListForm.jsp");
				
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
				
			} else if(command.equals("BoardWriteAction.bo")) {		//게시판 글쓰기 처리
				action  = new BoardWriteAction();
				forward = action.execute(request, response);				
			
			} else if(command.equals("BoardListAction.bo")) {		// 게시판 글목록 리스트 보여주기 처리
				action = new BoardListAction();
				forward = action.execute(request, response);
				
			} else if(command.equals("BoardDetailAction.bo")) {		//게시판 상세글 보기 처리
				action = new BoardDetailAction();
				forward = action.execute(request, response);
			
			} else if(command.equals("FileDownloadAction.bo")) {	//상세보기 및 파일다운로드 처리
				action = new FileDownloadAction();
				forward = action.execute(request, response);
				
			} else if(command.equals("BoardReplyFormAction.bo")) {	//답글작성화면 처리
				action = new BoardReplyFormAction();
				forward = action.execute(request, response);
				
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
			} else if(command.equals("BoardReplyAction.bo")) {	//답글 작성 처리
				action = new BoardReplyAction();
				forward = action.execute(request, response);
				
			} else if(command.equals("BoardDeleteAction.bo")) {	// 글 삭제 처리
				action = new BoardDeleteAction();
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
