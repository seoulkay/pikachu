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
<!-- 			<i class="fas fa-search" aria-hidden="true"></i> -->
				<input type="text" name="search" value="${search }">
<!-- 				<input type="submit" value="검색하기"> -->
				<button>
				<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
  				<path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
				</svg>
				</button>
				
				
</form>
<form action="writeForm" method="GET">
<!-- 				<input type="submit" value="글쓰기"> -->
				<button>
				<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil" viewBox="0 0 16 16">
  				<path d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168l10-10zM11.207 2.5L13.5 4.793 14.793 3.5 12.5 1.207 11.207 2.5zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293l6.5-6.5zm-9.761 5.175l-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325z"/>
				</svg>
				</button>
				
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


		<nav aria-label="Page navigation example">
			  <ul class="pagination">
			  
			    <li class="page-item">
			 	  <c:if test="${pm.currentPage !=0}">
			      <a class="page-link" href="home?pageSize=${page.pageSize }&currentPage=${pm.currentPage - 1}" aria-label="Previous">
			        <span aria-hidden="true">&laquo;</span>
			        <span class="sr-only">Previous</span>
			      </a>
			  	  </c:if>
			    </li>
			    
			    <c:forEach var="pi" begin="${pm.startPage }" end="${pm.endPage -1}">
			    	<c:choose>
			    		<c:when test="${pi == pm.currentPage }">	
			    			<li class="page-item active">
					    	<a class="page-link" href="home?pageSize=${page.pageSize }&currentPage=${pi}">${pi +1} 
<!-- 					    	<span class="sr-only">(current)</span> -->
					    	</a></li>
			    		</c:when>
			    		
			    		<c:otherwise>
					    	<li class="page-item">
					    	<a class="page-link" href="home?pageSize=${page.pageSize }&currentPage=${pi}">${pi +1} 
					    	</a></li>
						</c:otherwise>    
			    	</c:choose>
			    
			    </c:forEach>
			  
			    
			    <li class="page-item">
			      <a class="page-link" href="home?pageSize=${page.pageSize }&currentPage=${pm.currentPage + 1}" aria-label="Next">
			        <span aria-hidden="true">&raquo;</span>
			        <span class="sr-only">Next</span>
			      </a>
			    </li>
			    
			  </ul>
			  
		</nav>
	




</body>
</html>
