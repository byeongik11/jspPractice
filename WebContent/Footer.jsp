<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>하단 영역</title>
</head>
<body>
	<br>
	전체 : <%-- <%=session.getAttribute("totalCount") %> --%>
	${sessionScope.totalCount}
	<br>
	오늘 : <%-- <%=session.getAttribute("todayCount") %> --%>
	${sessionScope.todayCount}
</body>
</html>