<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 - 답변글</title>
 <style type="text/css">
        #title{
            height : 16;
            font-family :'돋움';
            font-size : 12;
            text-align :center;
        }
        
        table {
        	width: 700px;
        	border: 3px;
        	border-color: lightgray;
        	text-align: center;
        }
    </style>
</head>
<body>
	<br>
	<b><font size="6" color="gray">답글 작성</font></b>
	<br>
	
	<form action="BoardReplyAction.bo?page=${page }" name="boardForm" method="post">
		<input type="hidden" name="board_id" value="${sessionScope.sessionID }">
		<input type="hidden" name="board_num" value="${board.board_num }" />
		<input type="hidden" name="board_re_ref" value="${board.board_re_ref }">
<%-- 		<input type="hidden" name="board_re_lev" value="${board.board_re_lev }">
		<input type="hidden" name="board_re_seq" value="${board.board_re_seq }"> --%>
		
		
		<table>
			<tr>
				<td id="title">작성자</td>
				<td>${sessionScope.sessionID }</td>
			</tr>
			<tr>
				<td id="title">
					제 목
				</td>
				<td>
					<input name="board_subject" type="text" size="70" maxlength="100" value="" />
				</td>
			</tr>
			<tr>
				<td id="title">
					내 용
				</td>
				<td>
					<textarea rows="20" cols="72" name="board_content">
					</textarea>
				</td>
			</tr>
			<tr align="center" valign="middle">
				<td colspan="5">
					<input type="reset" value="작성취소">
					<input type="submit" value="등록">
					<input type="button" value="목록" onclick="javascript:history.go(-1)">
				</td> 
			</tr>
		</table>
	
	</form>
</body>
</html>