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
	<h1>${postOne.description }   </h1><br>
    <h4>${postOne.instaId}   </h4><br>	
    <small>${postOne.create}   </small><br>	
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
</body>
</html>
