<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>첫화면</title>
</head>
<body>

		<!-- 로그인 안되었을때 -->
	<c:if test="${sessionScope.sessionID==null }">
		
		<br><br><br>
		<b><font size="6" color="gray">첫화면</font></b>
		<br><br><br>
		<img alt="" src="img/welcome.jpg">
		<br><br>
	</c:if>
	
	<!-- 로그인 했을때-->
	<c:if test="${sessionScope.sessionID != null }">
		<br><br><br>
			<font color="skyblue" size="6"><%=session.getAttribute("sessionID") %></font>
			<font size="6">님 환영합니다.</font>
	</c:if>
		
</body>
</html>