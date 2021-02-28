<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h3>
	<a href="/ccc/home" style="text-decoration:none; color:black">one reply? serious?  </a>
</h3>
	
<div class="row">   
	<div class="col-3 text-center">
		
		<small>${reply.created }</small>
		
	</div>
	<div class="col-4 text-center">
		
		<small>${reply.replyId }</small>
		
	</div>
	<div class="col-3 text-center">
		
		${reply.instaId }
		
	</div>
	<div class="col-2 text-center">
		
		${reply.description }
		
	</div>
</div>

<!-- <hr color="black" size="0.8px"> -->

<table>
<tr>
<td>
<form action="updateReplyForm" id="myForm" method="GET">
	<input type="hidden" name="replyId" value="${reply.replyId }">
<!-- 	<input type="submit" value="수정"> -->
	<button>
		<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-scissors" viewBox="0 0 16 16">
		<path d="M3.5 3.5c-.614-.884-.074-1.962.858-2.5L8 7.226 11.642 1c.932.538 1.472 1.616.858 2.5L8.81 8.61l1.556 2.661a2.5 2.5 0 1 1-.794.637L8 9.73l-1.572 2.177a2.5 2.5 0 1 1-.794-.637L7.19 8.61 3.5 3.5zm2.5 10a1.5 1.5 0 1 0-3 0 1.5 1.5 0 0 0 3 0zm7 0a1.5 1.5 0 1 0-3 0 1.5 1.5 0 0 0 3 0z"/>
		</svg>
	</button>
</form>	
</td>

<td>
<form action="replyDeleteAction" id="myForm"  method="GET">
	<input type="hidden" name="replyId" value="${reply.replyId }">
<!-- 	<input type="submit" value="삭제"> -->
	<button>
		<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">
 		 <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>
  		<path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4L4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>
		</svg>
	</button>

</form>
</td>
</tr>
</table>

<hr color="black" size="1px">
<!-- <hr color="#C4C4C4" size="0.7px"> -->

<h4>
	more to say?  


<div class="row">
<form action="reReplyAction" id="reReplyForm" method="POST">

			<div class="col-3 text-center">
			<input type="text" name="instaId" placeholder="ID">
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
			<input type="hidden" name="postId" value="${post.postId }">
			<input type="hidden" name="replyId" value="${reply.replyId }">	
			
</form>
</div>

</h4>

<hr color="#C4C4C4" size="1px">

    <c:forEach items="${reReplyList}" var="reRepl" begin="0">
    	<div class="row">   
			<div class="col-12 text-center">
			<a href="oneReplyView?Id=${reply.replyId }" style="text-decoration:none; color:red">
				<small>${reRepl.created }</small>
				<small>${reRepl.replyId }</small>
				${reRepl.instaId }
				${reRepl.description }
			</a>
			</div>
		</div>
    

		<hr color="#C4C4C4" size="1px">
		
	</c:forEach>
	
</body>
</html>
