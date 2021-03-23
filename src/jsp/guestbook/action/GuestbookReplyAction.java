package jsp.guestbook.action;

import javax.servlet.http.*;

import jsp.common.action.*;
import jsp.guestbook.model.*;

public class GuestbookReplyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		ActionForward forward = new ActionForward();
		
		GuestbookDAO dao = GuestbookDAO.getInstance();
		GuestbookBean guestbook = new GuestbookBean();
		
		//답글 작성후 원래 페이지로 돌아가기
		String pageNum = request.getParameter("page");
		
		//파라미터를 가져옴
		
		int guestbook_no = Integer.parseInt(request.getParameter("guestbook_no"));
		String guestbook_id = request.getParameter("guestbook_id");
		String guestbook_password = request.getParameter("guestbook_password");
		String guestbook_content = request.getParameter("guestbook_content");
		int guestbook_group = Integer.parseInt(request.getParameter("guestbook_group"));
		
		guestbook.setGuestbook_no(dao.getSeq());
		guestbook.setGuestbook_id(guestbook_id);
		guestbook.setGuestbook_password(guestbook_password);
		guestbook.setGuestbook_content(guestbook_content);
		guestbook.setGuestbook_group(guestbook_group);
		guestbook.setGuestbook_parent(guestbook_no);
		
		boolean result = dao.guestbookInsert(guestbook);
		
		if(result) {
			forward.setRedirect(true);
			forward.setNextPath("GuestbookListAction.ge?page="+pageNum);
		}
		return forward;
	}

}
