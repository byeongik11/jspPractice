<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 
<%
	String contentPage=request.getParameter("contentPage");
	if(contentPage==null)
		contentPage="FirstView.jsp";
%>
 --%>




<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인 화면</title>
<style type="text/css">
	#wrap {
		width: 800px;
		margin: 0 auto 0 auto;
	}
	
	#header {
		text-align: center;
		width: 1000px;
		height: 150px;
		background-color: #92FFFF;
		padding: 60px 0px;
	}
	
	#main {
		float: left;
		width: 1000px;
		height: 700px;
		background-color: #FFCA6C;
		text-align: center;
		vertical-align: middle;
	}
	
	#footer {
			clear: left;
			width: 1000px;
			height: 100px;
			background-color: #7DFE74;
		}

</style>
<script type="text/javascript">
	

</script>
</head>
<body>

	<div id="wrap">
		<div id="header">
			<jsp:include page="Header.jsp" />
		</div>
		<div id="main">
			<c:set var="contentPage" value="${param.contentPage}"></c:set>
			<c:if test="${contentPage==null }">
				<jsp:include page="FirstView.jsp" />
			</c:if>
			<jsp:include page="${contentPage}"></jsp:include>
			
		</div>
		<div id="footer">
			<jsp:include page="Footer.jsp" />
		</div>
	</div>
	
	
</body>
</html>