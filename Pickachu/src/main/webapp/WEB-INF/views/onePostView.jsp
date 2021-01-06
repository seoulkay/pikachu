<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h3>
	one post? serious?  
</h3>
	
<div class="row">   
	<div class="col-3 text-center">
		
		<small>${post.create }</small>
		
	</div>
	<div class="col-4 text-center">
		
		<small>${post.postId }</small>
		
	</div>
	<div class="col-3 text-center">
		
		${post.instaId }
		
	</div>
	<div class="col-2 text-center">
		
		${post.description }
		
	</div>
</div>

<!-- <hr color="black" size="0.8px"> -->

<table>
<tr>
<td>
<form action="updatePostForm" id="myForm" method="GET">
	<input type="hidden" name="postId" value="${post.postId }">
	<input type="submit" value="수정">
</form>	
</td>
<td>
<form action="postDeleteAction" id="myForm"  method="GET">
	<input type="hidden" name="postId" value="${post.postId }">
	<input type="submit" value="삭제">
</form>
</td>
</tr>
</table>

<hr color="black" size="1px">
<!-- <hr color="#C4C4C4" size="0.7px"> -->

<h4>
	anything to say?  
</h4>

<div class="row">
<form action="replyAction" id="replyForm" method="POST">

			<div class="col-3 text-center">
			<input type="text" name="replyId" placeholder="ID">
			</div>
			<div class="col-7 text-center">
			<input type="text" name="description" placeholder="내용">
			</div>
			<div class="col-2 text-center">
			<input type="submit" value="작성">
			</div>
			<input type="hidden" name="postId" value="${post.postId }">	
			
</form>
</div>
<hr color="#C4C4C4" size="1px">

    <c:forEach items="${replyList}" var="repl" begin="0">
    	<div class="row">   
			<div class="col-3 text-center">
				<small>${repl.created }</small>
			</div>
<!-- 			<div class="col-4 text-center"> -->
<%-- 				<small>${repl.Id }</small> --%>
<!-- 			</div> -->
			<div class="col-3 text-center">
				${repl.replyId }
			</div>
			<div class="col-2 text-center">
				${repl.description }
			</div>
		</div>
    
		<table>
			<tr>
<%-- 				<td><strong>${repl.userId}</strong></td> --%>
<!-- 				<td></td> -->
<%-- 				<td>${repl.description}</td> --%>
<!-- 				<td></td> -->
<%-- 				<td>${repl.created}</td> --%>
			
				<td>
				<form action="modReply" method="get">	
					<input type="hidden" name="id" value="${repl.id }">
					<input type="submit" value="수정">
				</form>
				</td>
				<td>
					<form action="deleteReply" method="get">	
					<input type="hidden" name="id" value="${repl.id }">
					<input type="submit" value="삭제">
				</form>
				</td>
			</tr>
		</table>
		<hr color="#C4C4C4" size="1px">
		
	</c:forEach>
	
</body>
</html>
