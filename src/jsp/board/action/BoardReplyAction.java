package jsp.board.action;

import javax.servlet.http.*;

import jsp.board.model.*;
import jsp.common.action.*;

//답글 달기 action클래스
public class BoardReplyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		
		ActionForward forward = new ActionForward();
		
		BoardDAO dao = BoardDAO.getInstance();
		BoardBean boardData = new BoardBean();
		
		//답글 작성후 원래 페이지로 돌아가기
		String pageNum = request.getParameter("page");
		System.out.println("페이지 번호 : " + pageNum);
		//파라미터 값을 가져옴
		String id = request.getParameter("board_id");
		String subject = request.getParameter("board_subject");
		String content = request.getParameter("board_content");
		int ref = Integer.parseInt(request.getParameter("board_re_ref"));
		int lev = Integer.parseInt(request.getParameter("board_re_lev"));
		int seq = Integer.parseInt(request.getParameter("board_re_seq"));
		
		//답글 중 가장 최근 답글이 위로 올라가게 처리
		// 그러기 위해 답글 순서 seq를 1증가 시킴
		
		boardData.setBoard_re_ref(ref);
		boardData.setBoard_re_seq(seq);
		dao.updateReSeq(boardData);
		
		
		//답글 저장
		
		boardData.setBoard_num(dao.getSeq());// 시퀀스값 세팅
		boardData.setBoard_id(id);
		boardData.setBoard_subject(subject);
		boardData.setBoard_content(content);
		boardData.setBoard_re_ref(ref);
		boardData.setBoard_re_lev(lev+1);
		boardData.setBoard_re_seq(seq+1);
		
		boolean result = dao.boardInsert(boardData);
		
		if(result) {
			forward.setRedirect(false);
			//원래 있던 페이지로 돌아가기 위해 페이지번호 전달
			forward.setNextPath("BoardListAction.bo?page="+pageNum);
		}
		
		return forward;
	}

}
