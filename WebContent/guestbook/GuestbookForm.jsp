<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	table{
			margin-left:auto; 
			margin-right:auto;
			border:0px solid skyblue;
		}
		
		td{
			border:0px solid skyblue;
		}
		
		#title{
			background-color:skyblue
		}
		
		.left {
			text-align: left;
		}
		
		#listGuestForm {
			text-align: center;
			overflow: scroll;
			overflow-x:hidden;
			height: 220px;
		}
		
		#comment {
			height: 150px;
		}
</style>

<script type="text/javascript">
	function checkValue() {
		if(!document.guestbookInfo.guestbook_id.value) {
			alert('이름을 입력하세요');
			return false;
		}
		
		if(!document.guestbookInfo.guestbook_password.value) {
			alert('비밀번호를 입력하세요.');
			return false;
		}
		
		if(!document.guestbookInfo.guestbook_content.value) {
			alert('내용을 입력하세요.');
			return false;
		}
	}

</script>
</head>
<body>
	<div id="wrap">
	
		<br><br>
		<b><font size="6" color="gray">방명록</font></b>
		<br><br><br>
	
	<div id="writeGuestForm">
		<form action="GuestbookWriteAction.ge" name="guestbookInfo" method="post" onsubmit="return checkValue()">
			<div>
				<input type="hidden" name="pro" value="ins">
			</div>
			<table>
				<tr>
					<td id="title">이름</td>
					<td><input type="text" name="guestbook_id" id="guestbook_id"></td>
					<td id="title">비밀번호</td>
					<td><input type="password" name="guestbook_password" id="guestbook_password"></td>
				</tr>
				<tr>
					<td colspan="4">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="4">
						<textarea rows="7" cols="80" name="guestbook_content" id="guestbook_content"></textarea>
					</td>
				</tr>
			</table>
			<br>
			<input type="submit" value="등록">
		</form>
	</div>
	<!-- 글 등록 부분 끝 -->
	
	
		<br><br>
	<!-- 글목록 부분 시작 -->
	<div id="listGuestForm">
		<form action="" method="post" name="listForm">
			<input type="hidden" name="pro">
			<div id="comment">
				<c:forEach var="guestbook" items="${requestScope.list }">
					<hr size="1" width="700">
					<label>${guestbook.guestbook_id }</label>&nbsp;&nbsp;&nbsp;&nbsp;
					<label>${guestbook.guestbook_date }</label>&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="#">[답변]</a>&nbsp;
					<a href="#">[수정]</a>&nbsp;
					<a href="#">[삭제]</a><br>
					${guestbook.guestbook_content } <br>
				</c:forEach>
				<hr size="1" width="700">
			</div>
			
			<!-- 페이지 부분 -->
             <div id="pageForm">
                <c:if test="${startPage != 1}">
                    <a href='GuestbookListAction.ge?page=${startPage-1}'>[ 이전 ]</a>
                </c:if>
                
                <c:forEach var="pageNum" begin="${startPage}" end="${endPage}">
                    <c:if test="${pageNum == spage}">
                        ${pageNum} 
                    </c:if>
                    <c:if test="${pageNum != spage}">
                        <a href='GuestbookListAction.ge?page=${pageNum}'>${pageNum} </a>
                    </c:if>
                </c:forEach>
                
                <c:if test="${endPage != maxPage }">
                    <a href='GuestbookListAction.ge?page=${endPage+1 }'>[다음]</a>
                </c:if>
            </div> 

		</form>
	</div>
	</div>
	

</body>
</html>