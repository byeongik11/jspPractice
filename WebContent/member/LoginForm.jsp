<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<%
	request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 화면</title>

<link href='../../css/join_style.css' rel='stylesheet' style='text/css'/>
<style type="text/css">
		table{
			padding: 60px 0px;
			margin-left:auto; 
			margin-right:auto;
			border:3px solid skyblue;
		}
		
		td{
			border:1px solid skyblue
		}
		
		#title{
			background-color:skyblue
		}
	</style>
<script type="text/javascript">
	function checkValue() {
		
		inputForm = eval("document.loginInfo");
		
		if(!inputForm.id.value) {
			alert("아이디를 입력하세요");
			inputForm.id.focus();
			return false;
		}
		
		if(!inputForm.password.value) {
			alert("비밀번호를 입력하세요");
			inputForm.password.focus();
			return false;
		}
	}
	
	function goFirstForm() {		//메인폼으로 이동
		location.href="MainForm.jsp";
	}

</script>
</head>
<body>
	<div id="wrap">
		
		
		<br><br>
		<b><font size="6" color="gray">로그인</font></b>
		<br><br>
	
		<form action="MemberLoginAction.do" method="post" name="loginInfo" onsubmit="return checkValue()">
			<img alt="" src="../../img/welcome.jpg">
			<br><br>
			
			<table>
				<tr>
					<td bgcolor="skyblue">아이디</td>
					<td><input type="text" name="id" maxlength="50"></td>
				</tr>
				<tr>
					<td bgcolor="skyblue">비밀번호</td>
					<td><input type="password" name="password" maxlength="50"></td>
				</tr>
			</table>
			<br>
			<input type="submit" value="로그인"/>
			<input type="button" value="취소" onclick="goFirstForm()">
		</form>
		<%-- 
		<%
			String msg = request.getParameter("msg");
		
		if(msg!=null && msg.equals("0")) {
			out.println("<br>");
			out.println("<font color='red' size='5'>비밀번호를 확인해 주세요.</font>");
		} else if(msg!=null && msg.equals("-1")) {
			out.println("<br>");
			out.println("<font color='red' size='5'>아이디를 확인해 주세요.</font>");
		}
		
		%>
		 --%>
		 
		 <c:set var="failMessage" value="${requestScope.fail }" />
		 <c:if test="${failMessage !=null }">
			<c:choose>
				<c:when test="${failMessage == '0' }">
					<br><font color="red" size="5">비밀번호를 확인해 주세요.</font>
				</c:when>
				<c:otherwise>
					<br><font color="red" size="5">아이디를 확인해 주세요.</font>
				</c:otherwise>
			</c:choose>		 	
		 </c:if>
		 
	</div>
</body>
</html>