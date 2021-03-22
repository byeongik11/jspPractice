package jsp.board.action;

import java.util.*;

import javax.servlet.http.*;

import com.oreilly.servlet.*;
import com.oreilly.servlet.multipart.*;

import jsp.board.model.*;
import jsp.common.action.*;

public class BoardUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = new ActionForward();
		
		// 답글 작성 후 원래 페이지로 돌아가기 위해 페이지번호가 필요
		String pageNum = request.getParameter("page");
		
		int fileSize = 5*1024*1024;
		
		String uploadPath = request.getServletContext().getRealPath("/UploadFolder");
		
		try {
			MultipartRequest multi = new MultipartRequest(request, uploadPath,fileSize, "utf-8", new DefaultFileRenamePolicy());
			
			int num = Integer.parseInt(multi.getParameter("board_num")); //글번호
			String subject = multi.getParameter("board_subject");//글 제목
			String content = multi.getParameter("board_content");//글 내용
			String existFile = multi.getParameter("existing_file");//기존 첨부파일
			
			BoardBean board = new BoardBean();
			board.setBoard_num(num);
			board.setBoard_subject(subject);
			board.setBoard_content(content);
			
			Enumeration<String> fileNames = multi.getFileNames();
			
			if(fileNames.hasMoreElements()) {
				String fileName = fileNames.nextElement();
				String updateFile = multi.getFilesystemName(fileName);
				
				if(updateFile == null) {		//수정시 새로운 파일을 첨부안했다면 기존파일명을 세팅
					board.setBoard_file(existFile);
				} else {		//새로운 파일을 첨부시
					board.setBoard_file(updateFile);
				}
			}
			
			BoardDAO dao = BoardDAO.getInstance();
			boolean result = dao.updateBoard(board);
			
			if(result) {
				forward.setRedirect(true);
				forward.setNextPath("BoardListAction.bo?page="+pageNum);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return forward;
	}

}
