package jsp.board.action;

import java.util.*;

import javax.servlet.http.*;

import jsp.board.model.*;
import jsp.common.action.*;
// 게시글 글 목록 처리 action
// 페이징 처리도 구현
public class BoardListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		
		//현재 페이지 번호 만들기
		int spage = 1;
		String page = request.getParameter("page");
		
		if(page != null) {
			spage = Integer.parseInt(page);
		}
		
		//검색조건, 검색내용 가져옴
		String opt = request.getParameter("opt");
		String condition = request.getParameter("condition");
		
		//검색조건 내용을 map에 담음
		
		HashMap<String, Object> listOpt = new HashMap<String, Object>();
		listOpt.put("opt", opt);
		listOpt.put("condition", condition);
		listOpt.put("start", spage*10-9);
		
		//DAO 
		BoardDAO dao = BoardDAO.getInstance();
		int listCount = dao.getBoardListCount(listOpt);
		ArrayList<BoardBean> list = dao.getBoardList(listOpt);
		
		//한 화면에 10개의 게시글 보여지게 함
		//페이지 번호는 총 5개, 이후는 다음으로 표시
		
		//전체 페이지수
		int maxPage = (int)(listCount/10.0 + 0.9);
		//시작 페이지 번호
		int startPage = (int)(spage/5.0 + 0.8) * 5 -4;
		// 마지막 페이지 번호
		int endPage = startPage + 4;
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		
		//4개 페이지번호 저장
		request.setAttribute("spage", spage);
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		
		//글의 총 개수와 글목록 저장
		request.setAttribute("list", list);
		
		forward.setRedirect(false);
		forward.setNextPath("BoardListForm.bo");
		return forward;
	}

}
