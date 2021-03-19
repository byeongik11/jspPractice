<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="jsp.member.model.MemberBean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href='../../css/join_style.css' rel='stylesheet' style='text/css'/>
   <style type="text/css">
        table{
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
	function changeForm(val) {
		if(val == "-1") {				//메인폼으로
			location.href="MainForm.do";
		} else if( val == "0") {		//수정폼으로
			location.href="MemberModifyFormAction.do";
		} else if(val == "1") {			//삭제폼으로
			location.href="DeleteForm.do";
		}        
	}
	
</script>
</head>
<body>
	<%-- 
	<%	
		MemberBean memberBean = (MemberBean)request.getAttribute("memberInfo");
	%>
	 --%>
	 	
	 	<c:set var="member" value="${requestScope.memberInfo }" />
	 
	 
		<br><br>
		<b><font size="6" color="gray">내 정보</font></b>
		<br><br><br>
		
		<table>
			<tr>
				<td id="title">아이디</td>
				<td>${member.id }</td>
			</tr>
			
			<tr>
				<td id="title">비밀번호</td>
				<td>${member.password }</td>
			</tr>
			
			<tr>
				<td id="title">이름</td>
				<td>${member.name }</td>
			</tr>
			
			<tr>
				<td id="title">성별</td>
				<td>${member.gender }</td>
			</tr>
			
			<tr>
				<td id="title">생일</td>
				<td>
					${member.birthyy }년
					${member.birthmm }월
					${member.birthdd }일
				</td>
			</tr>
			
			<tr>
				<td id="title">이메일</td>
				<td>
					${memberBean.mail1 }@
					${memberBean.mail2 }
				</td>
			</tr>
			
			<tr>
				<td id="title">휴대전화</td>
				<td>
					${member.phone }
				</td>
			</tr>
			
				<tr>
				<td id="title">주소</td>
				<td>${member.address }</td>
			</tr>
			
		</table>
		<br>
		<input type="button" value="뒤로" onclick="changeForm(-1)">
		<input type="button" value="회원정보 변경" onclick="changeForm(0)">
		<input type="button" value="회원탈퇴" onclick="changeForm(1)">
</body>
</html>