package jsp.board.action;

import javax.servlet.http.*;

import jsp.board.model.*;
import jsp.common.action.*;

public class BoardUpdateFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = new ActionForward();
		
		//페이지번호와 글 번호를 가져옴
		
		String pageNum = request.getParameter("page");
		String num = request.getParameter("num");
		int boardNum = Integer.parseInt(num);
		
		BoardDAO dao = BoardDAO.getInstance();
		BoardBean board = dao.getDetail(boardNum);
		
		request.setAttribute("board", board);
		request.setAttribute("pageNum", pageNum);
		
		forward.setRedirect(false);
		forward.setNextPath("BoardUpdateForm.bo");
		return forward;
	}

}
