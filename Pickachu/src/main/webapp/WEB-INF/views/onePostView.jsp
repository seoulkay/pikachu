<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h3>
	<a href="/ccc/home" style="text-decoration:none; color:black">one post? serious?  </a>
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

<!-- <hr color="black" size="0.8px"> -->

<table>
<tr>
<td>
<form action="updatePostForm" id="myForm" method="GET">
	<input type="hidden" name="postId" value="${post.postId }">
<!-- 	<input type="submit" value="수정"> -->
	<button>
	<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-scissors" viewBox="0 0 16 16">
  	<path d="M3.5 3.5c-.614-.884-.074-1.962.858-2.5L8 7.226 11.642 1c.932.538 1.472 1.616.858 2.5L8.81 8.61l1.556 2.661a2.5 2.5 0 1 1-.794.637L8 9.73l-1.572 2.177a2.5 2.5 0 1 1-.794-.637L7.19 8.61 3.5 3.5zm2.5 10a1.5 1.5 0 1 0-3 0 1.5 1.5 0 0 0 3 0zm7 0a1.5 1.5 0 1 0-3 0 1.5 1.5 0 0 0 3 0z"/>
	</svg>
	</button>
</form>	
</td>

<td>
<form action="postDeleteAction" id="myForm"  method="GET">
	<input type="hidden" name="postId" value="${post.postId }">
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
	anything to say?  


<div class="row">
<form action="replyForm" id="myForm" method="GET">
	<input type="hidden" name="postId" value="${post.postId }">
<!-- 	<input type="submit" value="댓글쓰기"> -->
	<button>
	<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil" viewBox="0 0 16 16">
  	<path d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168l10-10zM11.207 2.5L13.5 4.793 14.793 3.5 12.5 1.207 11.207 2.5zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293l6.5-6.5zm-9.761 5.175l-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325z"/>
	</svg>
	</button>	
	
</form>
</div>
</h4>

<hr color="#C4C4C4" size="1px">

    <c:forEach items="${replyList}" var="repl" begin="0">
    	<div class="row">   
			<div class="col-3 text-center">
			<a href="oneReplyView?postId=${post.postId }&replyId=${repl.replyId }" style="text-decoration:none; color:black">
				<small>${repl.created }</small>
				</a>
			</div>
			<div class="col-4 text-center">
			<a href="oneReplyView?postId=${post.postId }&replyId=${repl.replyId }" style="text-decoration:none; color:black">
				<small>${repl.replyId }</small>
			</a>
			</div>
			<div class="col-3 text-center">
			<a href="oneReplyView?postId=${post.postId }&replyId=${repl.replyId }" style="text-decoration:none; color:black">
				${repl.instaId }
			</a>
			</div>
			<div class="col-2 text-center">
			<a href="oneReplyView?postId=${post.postId }&replyId=${repl.replyId }" style="text-decoration:none; color:black">
				${repl.description }
			</a>
			</div>
		</div>
    
    		<c:forEach items="${repl.reReplyList}" var="reRepl" begin="0">
    			<div class="row" >   
					<div class="col-12 text-center">
					<a href="oneReplyView?postId=${post.postId }&replyId=${reRepl.replyId }" style="text-decoration:none; color:red">
						<small>${reRepl.created }</small>
						<small>${reRepl.replyId }</small>		
						${reRepl.instaId }
						${reRepl.description }
					</a>
					</div>
				</div>
    		</c:forEach>	
    

		<hr color="#C4C4C4" size="1px">
		
	</c:forEach>
	
</body>
</html>
