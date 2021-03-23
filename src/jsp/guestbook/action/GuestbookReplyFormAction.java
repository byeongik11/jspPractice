package jsp.guestbook.action;

import javax.servlet.http.*;

import jsp.common.action.*;
import jsp.guestbook.model.*;

public class GuestbookReplyFormAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		
		//부모글의 글번호 가져옴
		int guestbook_no = Integer.parseInt(request.getParameter("num"));
		// 답글 작성 후 원래 페이지로 돌아가기 위해 페이지번호가 필요
		
		String pageNum = request.getParameter("page");
		
		//부모 방명록의 정보를 db에서 가져옴
		
		GuestbookDAO dao = GuestbookDAO.getInstance();
		GuestbookBean guestbook = dao.getGuestbook(guestbook_no);
		
		
		//방명록 정보와 페이지 번호를 request 세팅
		
		request.setAttribute("guestbook", guestbook);
		request.setAttribute("pageNum", pageNum);
		
		forward.setRedirect(false);
		forward.setNextPath("guestbook/GuestbookReplyForm.jsp");
		
		return forward;
	}

}
