<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h3>
	hello stranger!  
</h3>

<%-- <P>  ${post.postId } </P> --%>
<%-- <P>  ${post.instaId } </P> --%>
<%-- <P>  ${post.description } </P> --%>
<%-- <P>  ${post.create } </P> --%>

	
<form action="home" id="myForm" class="form-inline" method="GET">
			<i class="fas fa-search" aria-hidden="true"></i>
				<input type="text" name="search" value="${search }">
				<input type="submit" value="검색하기">
</form>
<form action="writeForm" method="GET">
				<input type="submit" value="글쓰기">
</form>		
			
<c:forEach items="${postList }" var="post" begin="0" >
		 	  	<div class="row">   
			 	  	 <div class="col-3 text-center">
			 	  	 	<a href="onePostView?postId=${post.postId }" style="text-decoration:none; color:black">
			 	  	 	<small>${post.create }</small>
			 	  	 	</a>
			 	  	 </div>
			 	  	 <div class="col-4 text-center">
			 	  	 	<a href="onePostView?postId=${post.postId }" style="text-decoration:none; color:black">
			 	  	 	<small>${post.postId }</small>
			 	  	 	</a>
			 	  	 </div>
			 	  	 <div class="col-3 text-center">
			 	  	 	<a href="onePostView?postId=${post.postId }" style="text-decoration:none; color:black">
			 	  	 	${post.instaId }
			 	  	 	</a>
			 	  	 </div>
			 	  	 <div class="col-2 text-center">
			 	  	 	<a href="onePostView?postId=${post.postId }" style="text-decoration:none; color:black">
			 	  	 	${post.description }
			 	  	 	</a>
			 	  	 </div>
				</div>
				<hr color="black" size="1px">
		 	 </c:forEach>




</body>
</html>
