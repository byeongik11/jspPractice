package jsp.board.action;

import javax.servlet.http.*;

import jsp.board.model.*;
import jsp.common.action.*;
//게시판 상세글보기 처리 action
public class BoardDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = new ActionForward();
		
		//파라미터로 넘어온 글번호와 페이지번호를 가져온다.
		
		String num = request.getParameter("num");
		int boardNum = Integer.parseInt(num);
		
		String pageNum = request.getParameter("pageNum");
		
		BoardDAO dao = BoardDAO.getInstance();
		
		BoardBean board = dao.getDetail(boardNum);
		boolean result = dao.updateCount(boardNum);
		
		request.setAttribute("board", board);
		request.setAttribute("pageNum", pageNum);
		
		if(result) {
			forward.setRedirect(false);
			forward.setNextPath("BoardDetailForm.bo");
		}
		return forward;
	}

}
