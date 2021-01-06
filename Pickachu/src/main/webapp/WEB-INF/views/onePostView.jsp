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

<hr color="black" size="1px">

<form action="updatePostForm" id="myForm" class="form-inline" method="GET">
	<i class="fas fa-search" aria-hidden="true"></i>
	<input type="hidden" name="postId" value="${post.postId }">
	<input type="submit" value="수정">
</form>	
<form action="postDeleteAction" id="myForm" class="form-inline" method="GET">
	<i class="fas fa-search" aria-hidden="true"></i>
	<input type="hidden" name="postId" value="${post.postId }">
	<input type="submit" value="삭제">
</form>	


</body>
</html>
