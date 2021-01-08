<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Post</title>
</head>
<body>
<h1>
	포스트 하나만 보여주는 페이지입니다..  
</h1>
			<form action="postUpdateForm" id="myForm" class="form-inline" method="GET">
				<i class="fas fa-search" aria-hidden="true"></i>
				<input type="hidden" name="postId" value="${postOne.postId }">
				<input type="submit" value="수정">
			</form>	
			<form action="postDeleteAction" id="myForm" class="form-inline" method="GET">
				<i class="fas fa-search" aria-hidden="true"></i>
				<input type="hidden" name="postId" value="${postOne.postId }">
				<input type="submit" value="삭제">
			</form>	
			
	<h1>${postOne.description }   </h1><br>
    <h4>${postOne.instaId}   </h4><br>	
    <small>${postOne.create}   </small><br>	
    							<form action="replyFormAction" class="form-inline" method="POST">
					 	 		댓글 아이디 : <input type="text" name="instaId">
					 	 		내용 : <input type="text" name="description">
					 	 		<input type="submit" value="댓글쓰기">
					 	 		<input type="hidden" name="postId" value="${postOne.postId }">
				 	 			</form>
			
			
			<c:forEach items="${replyList }" var="re" begin="0">
		 	  	<div class="row">    
				<table>
					<tr>
					    <td width=100>${re.create }</td>
						
						<td width=150>👤${re.instaId} </td>
						<td>:</td><td width=450 style="word-break:break-all">${re.description }</td>
						<td>
							<form action="replyUpdateForm" class="form-inline" method="GET">
			 	 			<input type="submit" value="수정">
			 	 			<input type="hidden" name="id" value="${re.id }">
		 	 				</form>
		 	 			</td>
		 	 			<td>
		 	 				<form action="replyDeleteAction" class="form-inline" method="GET">
			 	 			<input type="submit" value="삭제">
			 	 			<input type="hidden" name="id" value="${re.id }">
			 	 			<input type="hidden" name="postId" value="${re.postId }">
		 	 				</form>
		 	 			</td>	
					</tr>
					
					<!-- 대댓글 -->
					<c:forEach items="${re.reReplyList }" var="reRe" begin="0">
						<tr>
							<td width=45></td>
						    <td colspan=4><small>${reRe.create }
								😃${reRe.instaId} :
								${reRe.description }</small>
							<td>
								<form action="replyUpdateForm" class="form-inline" method="GET">
				 	 			<input type="submit" value="수정">
				 	 			<input type="hidden" name="id" value="${reRe.id }">
			 	 				</form>
			 	 			</td>
			 	 			<td>
			 	 				<form action="replyDeleteAction" class="form-inline" method="GET">
				 	 			<input type="submit" value="삭제">
				 	 			<input type="hidden" name="id" value="${reRe.id }">
				 	 			<input type="hidden" name="postId" value="${reRe.postId }">
			 	 				</form>
			 	 			</td>	
						</tr>
						</c:forEach>
						
						<tr>
							<td colspan = "7">
								<form action="reReplyFormAction" class="form-inline" method="POST">
					 	 		아이디 : <input type="text" name="instaId">
					 	 		내용 : <input type="text" name="description">
					 	 		<input type="submit" value="댓글쓰기">
					 	 		<input type="hidden" name="id" value="${re.id }">
					 	 		<input type="hidden" name="postId" value="${re.postId }">
				 	 			</form>
							</td>
						</tr>
					
				</table>
				</div>
		 	 </c:forEach>
</body>
</html>
