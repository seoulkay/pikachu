<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.79.0">
    <title>기쁜우리젊은날</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/5.0/examples/cover/">
    <!-- CSS only -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">

    

    <!-- Bootstrap core CSS -->
<link href="/docs/5.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">

    <!-- Favicons -->
<link rel="apple-touch-icon" href="/docs/5.0/assets/img/favicons/apple-touch-icon.png" sizes="180x180">
<link rel="icon" href="/docs/5.0/assets/img/favicons/favicon-32x32.png" sizes="32x32" type="image/png">
<link rel="icon" href="/docs/5.0/assets/img/favicons/favicon-16x16.png" sizes="16x16" type="image/png">
<link rel="manifest" href="/docs/5.0/assets/img/favicons/manifest.json">
<link rel="mask-icon" href="/docs/5.0/assets/img/favicons/safari-pinned-tab.svg" color="#7952b3">
<link rel="icon" href="/docs/5.0/assets/img/favicons/favicon.ico">
<meta name="theme-color" content="#7952b3">


    <style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 2rem;
        }
      }
    </style>

    
    <!-- Custom styles for this template -->
    <link href="resources/cover.css" rel="stylesheet">
</head>
<body class="d-flex h-100 text-center text-white bg-dark">
<div class="cover-container w-150 h-100 p-3 mx-auto flex-column">

<header class="mb-auto">
    <div>
      <nav class="nav nav-masthead justify-content-center float-md-center">
        <a class="nav-link" aria-current="page" href="http://localhost:8080/ccc/home">Home</a>
        <a class="nav-link" href="http://localhost:8080/ccc/postForm">새글쓰기</a>
        <a class="nav-link" href="#">Login</a>
      </nav>
    </div>
  </header>
  <br>
  <br>
  
    <main class="px-3">
    
    <input class="form-control" type="text" placeholder="${postOne.description }" aria-label="readonly input example" readonly>
    <small>${postOne.instaId} :	${postOne.create}   </small><br><br>
    <table style="margin-left: auto; margin-right:auto;">
    <tr>
     <td>
    			<form action="postUpdateForm" id="myForm" class="form-inline" method="GET">
				<i class="fas fa-search" aria-hidden="true"></i>
				<button type="submit" class="btn btn-outline-secondary">update</button>
				<input type="hidden" name="postId" value="${postOne.postId }">
				</form>	
    </td>
    <td>
   			 	<form action="postDeleteAction" id="myForm" class="form-inline" method="GET">
				<i class="fas fa-search" aria-hidden="true"></i>
				<input type="hidden" name="postId" value="${postOne.postId }">
				<button type="submit" class="btn btn-outline-secondary">delete</button>
				</form>	
    </td>
 
    </tr>
    </table>
    <br><br>
    							<form action="replyFormAction" class="form-inline" method="POST">
								<input class="form-control" type="text" name="description" placeholder="message to ${postOne.instaId}" aria-label="description">
								<input class="form-control form-control-sm" type="text"  name="instaId" placeholder="your instaId" aria-label="instaId">
					 	 		<button type="submit" class="btn btn-secondary">reply</button>
					 	 		<input type="hidden" name="postId" value="${postOne.postId }">
				 	 			</form>
			
			
			<c:forEach items="${replyList }" var="re" begin="0">
		 	  	<div class="row">    
				<table style="margin-left: auto; margin-right:auto;">
					<tr>
					  	<td> 
					  		<input class="form-control" type="text" placeholder="${re.description }" aria-label="readonly input example" readonly>
    					</td>
    					<td>	
    						<small>👤${re.instaId}:	${re.create }   </small>
    					</td>
		 	 			<td>
		 	 				<form action="replyUpdateForm" id="myForm" class="form-inline" method="GET">
							<i class="fas fa-search" aria-hidden="true"></i>
							<button type="submit" class="btn btn-outline-secondary">update</button>
							<input type="hidden" name="id" value="${re.id }">
							</form>
		 	 			</td>
		 	 			<td>
		 	 				<form action="replyDeleteAction" id="myForm" class="form-inline" method="GET">
							<i class="fas fa-search" aria-hidden="true"></i>
							<input type="hidden" name="id" value="${re.id }">
			 	 			<input type="hidden" name="postId" value="${re.postId }">
							<button type="submit" class="btn btn-outline-secondary">delete</button>
							</form>
		 	 			</td>	
					</tr>
					
					<!-- 대댓글 -->
					<c:forEach items="${re.reReplyList }" var="reRe" begin="0">
						<tr>
							<td width=45></td>
						    <td colspan=4><small>${reRe.create }
								😃${reRe.instaId} :
								${reRe.description }</small>
							<td>
			 	 				<form action="replyUpdateForm" id="myForm" class="form-inline" method="GET">
								<i class="fas fa-search" aria-hidden="true"></i>
								<button type="submit" class="btn btn-outline-secondary">update</button>
								<input type="hidden" name="id" value="${reRe.id }">
				</form>	
			 	 			</td>
			 	 			<td>
			 	 				<form action="replyDeleteAction" id="myForm" class="form-inline" method="GET">
								<i class="fas fa-search" aria-hidden="true"></i>
								<input type="hidden" name="id" value="${reRe.id }">
				 	 			<input type="hidden" name="postId" value="${reRe.postId }">
								<button type="submit" class="btn btn-outline-secondary">delete</button>
								</form>	
			 	 			</td>	
						</tr>
						</c:forEach>
						
						<tr>
							<td colspan = "7">
							<br><br>
								<form action="reReplyFormAction" class="form-inline" method="POST">
								<input class="form-control form-control-sm" type="text" name="description" placeholder="message to ${re.instaId}" aria-label="description">
								<input class="form-control form-control-sm" type="text"  name="instaId" placeholder="your instaId" aria-label="instaId">
					 	 		<button type="submit" class="btn btn-secondary">reply</button>
					 	 		<input type="hidden" name="id" value="${re.id }">
					 	 		<input type="hidden" name="postId" value="${re.postId }">
				 	 			</form>
							</td>
						</tr>
					
				</table>
				</div>
		 	 </c:forEach>
		 	 </main>
		 	 </div>
</body>
</html>
