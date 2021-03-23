package jsp.guestbook.action;

import javax.servlet.http.*;

import jsp.common.action.*;
import jsp.guestbook.model.*;

public class GuestbookWriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		ActionForward forward = new ActionForward();
		
		String guestbook_id = request.getParameter("guestbook_id");
		String guestbook_password = request.getParameter("guestbook_password");
		String guestbook_content = request.getParameter("guestbook_content");
		
		GuestbookDAO dao = GuestbookDAO.getInstance();
		
		GuestbookBean guestbook = new GuestbookBean();
		guestbook.setGuestbook_no(dao.getSeq());  //시퀀스번호
		guestbook.setGuestbook_id(guestbook_id);
		guestbook.setGuestbook_password(guestbook_password);
		guestbook.setGuestbook_content(guestbook_content);
		
		boolean result = dao.guestbookInsert(guestbook);
		
		if(result) {
			System.out.println("등록완료");
			forward.setRedirect(true);
			System.out.println("리스트로 이동");
			forward.setNextPath("GuestbookListAction.ge");
		}
		return forward;
	}

}
