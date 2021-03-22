package jsp.board.action;

import java.io.*;

import javax.servlet.http.*;

import jsp.board.model.*;
import jsp.common.action.*;

public class BoardDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = new ActionForward();
		
		//글 번호를 가져온다
		
		String num = request.getParameter("num");
		int boardNum = Integer.parseInt(num);
		
		BoardDAO dao = BoardDAO.getInstance();
		// 삭제할 글에 있는 파일 정보를 가져옴
		String fileName = dao.getFileName(boardNum);
		// 글 삭제 - 답글이 있을 경우 답글도 모두 삭제.
		boolean result = dao.deleteBoard(boardNum);
		
		if(fileName != null) {
			//파일이 있는 폴더의 절대경롤르 가져온다.
			
			String folder = request.getServletContext().getRealPath("UploadFolder");
			String filePath = folder + "/" + fileName;
			
			File file = new File(filePath);
			
			if(file.exists()) {	//파일이 존재하면 
				file.delete();	//삭제
			}
			
		}
		
		if(result) {
			forward.setRedirect(true);
			forward.setNextPath("BoardListAction.bo");
		} else {
			return null;
		}
		return forward;
	}

}
