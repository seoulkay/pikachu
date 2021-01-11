<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h3>
	<a href="/ccc/home" style="text-decoration:none; color:black">write me!  </a>
</h3>

<div class="row">
<form action="writeFormInput" id="writeFormInput" method="POST">
<!-- 			<i class="fas fa-search" aria-hidden="true"></i>	 -->
			<div class="col-3 text-center">
			<input type="text" name="instaId" placeholder="ID" autofocus>
			</div>
			<div class="col-7 text-center">
			<input type="text" name="description" placeholder="description">
			</div>
			<div class="col-2 text-center">
<!-- 			<input type="submit" value="작성"> -->
			<button>
			<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-check" viewBox="0 0 16 16">
 			 <path d="M10.97 4.97a.75.75 0 0 1 1.07 1.05l-3.99 4.99a.75.75 0 0 1-1.08.02L4.324 8.384a.75.75 0 1 1 1.06-1.06l2.094 2.093 3.473-4.425a.267.267 0 0 1 .02-.022z"/>
			</svg>
			</button>
			
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
