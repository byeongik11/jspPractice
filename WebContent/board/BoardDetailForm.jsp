<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
 
<html>
<head>
    <title>글 상세보기</title>
    
    <style type="text/css">
        #wrap {
            width: 800px;
            margin: 0 auto 0 auto;
        }
    
        #detailBoard{
            text-align :center;
        }
        
        #title{
            height : 16;
            font-family :'돋움';
            font-size : 12;
            text-align :center;
        }
    </style>
    
    <script type="text/javascript">
    	function changeView(value) {
    		if(value == 0) {
    			location.href='BoardListAction.bo?page=${pageNum}';
    		} else if(value == 1) {
    			location.href='BoardReplyFormAction.bo?num=${board.board_num}&page=${pageNum}';
    		}
    	}
    </script>
</head>
<body>
 
<div id="wrap">
    <br><br>
    <div id="board">
        <table id="detailBoard" width="800" border="3" bordercolor="lightgray">
        
            <tr>
                <td id="title">작성일</td>
                <td>${board.board_date}</td>
            </tr>
            <tr>
                <td id="title">작성자</td>
                <td>${board.board_id}</td>
            </tr>
            <tr>
                <td id="title">
                    제 목
                </td>
                <td>
                    ${board.board_subject}
                </td>        
            </tr>
            <tr>
                <td id="title">
                    내 용
                </td>
                <td>
                    ${board.board_content}
                </td>        
            </tr>
            <tr>
                <td id="title">
                    첨부파일
                </td>
                <td>
                    <a href='FileDownloadAction.bo?file_name=${board.board_file}'>${board.board_file}</a>
                </td>    
            </tr>
    
            <tr align="center" valign="middle">
                <td colspan="5">
                <!-- 로그인을 해야만 답글을 달수 있도록 함, 수정, 삭제 버튼일 경우 글 작성자일 경우만 보이도록 처리 -->
                <c:if test="${sessionScope.sessionID !=null}">
                	<c:if test="${sessionScope.sessionID == board.board_id }">
    	                <input type="button" value="수정" >
        	            <input type="button" value="삭제" >
 	               </c:if>
                    <input type="button" value=답글 onclick="changeView(1) ">    
                </c:if>
                    <input type="button" value="목록" onclick="changeView(0)">            
                </td>
            </tr>
        </table>
    </div>
</div>    
 
</body>
</html>


