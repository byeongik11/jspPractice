<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>탈퇴 화면</title>
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
		//비밀번호 미입력시 경고창
	function checkValue() {
			
		if(!document.deleteform.password.value) {
			alert("비밀번호를 입력하지 않았습니다.");
			return false;
		}
	}
	</script>

</head>
<body>
	
	<br><br>
		<b><font size="6" color="gray">내 정보</font></b>
		<br><br><br>
	
	<form action="MemberDeleteAction.do" onsubmit="return checkValue()" name="deleteform" method="post">
		
		<table>
			<tr>
				<td bgcolor="skyblue">비밀번호</td>
				<td><input type="password" name="password" maxlength="50"></td>
			</tr>
		</table>
		
		<br>
		<input type="button" value="취소" onclick="javascript:window.location='MainForm.do'">
		<input type="submit" value="탈퇴" />
	
	</form>
	
</body>
</html>