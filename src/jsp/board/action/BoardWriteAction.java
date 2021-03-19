package jsp.board.action;

import java.util.*;

import javax.servlet.http.*;

import com.oreilly.servlet.*;
import com.oreilly.servlet.multipart.*;

import jsp.board.model.*;
import jsp.common.action.*;
// 게시판 글쓰기 처리 action
public class BoardWriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ActionForward forward = new ActionForward();
		
		//업로드 파일 사이즈
		int fileSize = 5* 1024* 1024;
		
		//업로드 될 경로
		String uploadPath = request.getServletContext().getRealPath("/UploadFolder");
		
		try {
			//파일업로드
			MultipartRequest multi = new MultipartRequest(request, uploadPath,fileSize,"utf-8",new DefaultFileRenamePolicy());
				
			//파일이름 가져오기
			String fileName = "";
			@SuppressWarnings("unchecked")
			Enumeration<String> names = multi.getFileNames();
			if(names.hasMoreElements()) {
				String name = names.nextElement();
				fileName = multi.getFilesystemName(name);
				System.out.println("파일이름 " + fileName);
			}
			
			BoardDAO dao = BoardDAO.getInstance();
			BoardBean boardData = new BoardBean();
			String board_file = multi.getFilesystemName("board_file");		//글쓰기 파일첨부 부분의 name의 값을 파일이름으로 불러옴
			boardData.setBoard_num(dao.getSeq());					//시퀀스값 가져와 세팅
			boardData.setBoard_id(multi.getParameter("board_id"));	//히든값
			boardData.setBoard_subject(multi.getParameter("board_subject"));
			boardData.setBoard_content(multi.getParameter("board_content"));
			boardData.setBoard_file(board_file);
			
			boolean result = dao.boardInsert(boardData);
			
			if(result) {
				System.out.println("글 등록 완료");
				forward.setRedirect(true);
				forward.setNextPath("BoardListAction.bo");
			}
				
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("글 작성 오류 : " + e.getMessage());
		}

		return forward;
	}

}
