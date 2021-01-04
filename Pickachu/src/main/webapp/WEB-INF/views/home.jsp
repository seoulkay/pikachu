<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	인스타그램 게시판이랍니다.  
</h1>

<%-- <P>  ${post.postId } </P> --%>
<%-- <P>  ${post.instaId } </P> --%>
<%-- <P>  ${post.description } </P> --%>
<%-- <P>  ${post.create } </P> --%>

	
<form action="" id="myForm" class="form-inline" method="GET">
			<i class="fas fa-search" aria-hidden="true"></i>
				<input type="text" name="search" value="${search }">
				<input type="submit" value="검색하기">
</form>
			
<c:forEach items="${postList }" var="post" begin="0" >
		 	  	<div class="row">   
			 	  	 <div class="col-3 text-center">
			 	  	 	<small>${post.create }</small>
			 	  	 </div>
			 	  	 <div class="col-4 text-center">
			 	  	 	<small><a>${post.postId }</a></small>
			 	  	 </div>
			 	  	 <div class="col-3 text-center">
			 	  	 	${post.instaId }
			 	  	 </div>
			 	  	 <div class="col-2 text-center">
			 	  	 	${post.description }
			 	  	 </div>
				</div>
				<hr>
		 	 </c:forEach>




</body>
</html>
