<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>PostForm</title>
</head>
<body>
<h1>
	포스트 수정하는 페이지입니다.. 
</h1>
	<form action="postFormAction" id="myForm" class="form-inline" method="post">
 	 	<i class="fas fa-search" aria-hidden="true"></i>
	 	 	<table style="margin-left: auto; margin-right:auto;">
		 	 	<tr>
		 	 		<td>작성자:</td><td><input type="text" name="instaId"></td>
		 	 		<td>사진:</td><td><input type="text" name="picture"></td>
			 	 	</tr>
		 	 	<tr>
			 	 	<td>내용:</td>
			 	 	<td colspan=3>
					<textarea  name="description" rows="13" cols="46" ></textarea>
					</td>
		 	 	</tr>
		 	 	</table>
		 	 	<table style="margin-left: auto; margin-right:auto;">
	 	 		<tr>
		 	 		
		 	 		<td colspan=4>
			 	 	<input type="submit" >
			 	 	<input type="button" onclick="myFunction()" value="재작성 ">
			 	 	</td>
		 	 	</tr>
 	 	</table>
 	 </form>

</body>
<script>
		function myFunction() {
		 document.getElementById("myForm").reset();
		}
</script>
</html>
