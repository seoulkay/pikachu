<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h3>
	write me!  
</h3>

<div class="row">
<form action="writeFormInput" id="writeFormInput" method="POST">
<!-- 			<i class="fas fa-search" aria-hidden="true"></i>	 -->
			<div class="col-3 text-center">
			<input type="text" name="instaId" placeholder="ID">
			</div>
			<div class="col-7 text-center">
			<input type="text" name="description" placeholder="내용">
			</div>
			<div class="col-2 text-center">
			<input type="submit" value="작성">
			</div>
</form>
</div>
			
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
				<hr color="black" size="1px">
</c:forEach>




</body>
</html>
