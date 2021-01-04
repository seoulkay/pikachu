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

<table>
	
	<c:forEach items="${postAllList }" var="po" begin="0">
		<tr>
			<td>				
				${po.postId }
			</td>
			<td>
				${po.instaId }
			</td>
			<td>
				${po.description }
			</td>
			<td>
				${po.create }
			</td>

			
		</tr>
				
	</c:forEach>
					
</table>



</body>
</html>
