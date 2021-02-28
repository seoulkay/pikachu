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

<form action="updatePostAction" id="myForm" class="form-inline" method="post">
 	 	<i class="fas fa-search" aria-hidden="true"></i>
	 	 	<table>
		 	 	<tr>
		 	 		<td>ID</td><td><input type="text" name="instaId" value="${post.instaId }"></td>
		 	 	</tr>
		 	 	<tr>
			 	 	<td>내용</td>
			 	 	<td colspan=3>
					<textarea  name="description" rows="13" cols="46">${post.description}</textarea>
					</td>
		 	 	</tr>
		 	 	</table>
		 	 	<table >
	 	 		<tr>
		 	 		<td colspan=4>
		 	 		<input type="hidden" name="postId" value="${post.postId }">
			 	 	<input type="submit" >
			 	 	<input type="button" onclick="myFunction()" value="재작성 ">
			 	 	</td>
		 	 	</tr>
 	 	</table>
 	 </form>

<script>
		function myFunction() {
		 document.getElementById("myForm").reset();
		}
</script>


</body>
</html>
