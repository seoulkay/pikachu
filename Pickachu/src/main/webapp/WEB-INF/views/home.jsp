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
      <h3 class="float-md-start mb-0">기쁜우리젊은날</h3>
      <nav class="nav nav-masthead justify-content-center float-md-end">
        <a class="nav-link active" aria-current="page" href="http://localhost:8080/ccc/home">Home</a>
        <a class="nav-link" href="http://localhost:8080/ccc/postForm">새글쓰기</a>
        <a class="nav-link" href="#">Login</a>
      </nav>
    </div>
  </header>
  <main class="px-3">
  <br>
  <br>
			<form action="" id="myForm" class="form-inline" method="GET">
				<i class="fas fa-search" aria-hidden="true"></i>
				<input class="form-control form-control-sm" type="text" name="search" placeholder="search is .. ${search}" aria-label=".form-control-sm example">
			</form>
			
			<c:forEach items="${postList }" var="post" begin="0" >
		 	  	<div class="row">   
			 	  	 <div class="col-3 text-center">
			 	  	 	<small>${post.create }</small>
			 	  	 </div>
			 	  	 <div class="col-6 text-center">
			 	  	 	<a href="http://localhost:8080/ccc/post?postId=${post.postId}">${post.description }</a>
			 	  	 </div>
			 	  	 <div class="col-3 text-center">
			 	  	 	${post.instaId }
			 	  	 </div>
			 	  	 
				</div>
				<hr>
		 	 </c:forEach>
		 	 
		 	 
<!-- 		 	 페이징버튼부 -->
		 	 <div class="row align-items-center">
			
				
				<div class="text-center">
				<table style="margin-left: auto; margin-right:auto;">
				<tr>
				<td>
				<nav aria-label="Page navigation example">
						<div class="row">
							<ul class="pagination">
						 	 	<li class="page-item">
							 	 	<c:if test="${totalSize.currentPage != 0 }"> 
									<a class="page-link" href="http://localhost:8080/ccc/home?pageSize=${totalSize.pageSize }&currentPage=${totalSize.currentPage - 1}&search=${search}" aria-label="Previous">
									<span aria-hidden="true">&laquo;</span>
									</a>
									</c:if>
							 	</li>	
							</ul>
						</div>
					</nav>
				</td>
				
				<c:forEach  begin="${pm.startPage }" end="${pm.endPage - 1 }" var="idx" >
					<td>
						<nav aria-label="Page navigation example">
							<ul class="pagination">
								<li class="page-item <c:out value="${idx == totalSize.currentPage ? 'active' : '' }"/>">
									<a class="page-link" href="http://localhost:8080/ccc/home?pageSize=${totalSize.pageSize }&currentPage=${idx}&search=${search}">${idx + 1}
									</a>
								</li>
							</ul>
						</nav> 
					</td>    
			 	</c:forEach>
			 	<td>
			 	<nav aria-label="Page navigation example">
						<ul class="pagination">
							<li class="page-item">
								<c:if test="${totalSize.currentPage != showPage}">
									<a class="page-link" href="http://localhost:8080/ccc/home?pageSize=${totalSize.pageSize }&currentPage=${totalSize.currentPage + 1}&search=${search}" aria-label="Next">
										<span aria-hidden="true">&raquo;</span>
									</a>
								</c:if>
							</li>
						</ul>
					</nav>
				</td>
			 	</tr>
			 	</table>
				</div>	
				
		 	 
			</div>
<!-- 			페이징버튼 끝  -->
</main>
</div>
</body>
</html>