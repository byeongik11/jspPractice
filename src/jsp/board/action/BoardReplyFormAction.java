package jsp.board.action;

import javax.servlet.http.*;

import jsp.board.model.*;
import jsp.common.action.*;
// 상세보기에서 답글 버튼 클릭시 답글작성화면으로 이동할 때 실행되는 action
public class BoardReplyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = new ActionForward();
		
		
		BoardDAO dao = BoardDAO.getInstance();
		// 게시글 번호
		int num = Integer.parseInt(request.getParameter("num"));
		// 답글작성후 원래 페이지로 돌아가기 위해 
		String pageNum = request.getParameter("page");
		
		BoardBean board = dao.getDetail(num);
		
		request.setAttribute("board", board);
		request.setAttribute("page", pageNum);
		
		forward.setRedirect(false);//단순조회
		forward.setNextPath("BoardReplyForm.bo");
		return forward;
	}

}
