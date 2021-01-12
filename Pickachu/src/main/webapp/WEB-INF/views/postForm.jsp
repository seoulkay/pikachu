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
<div class="cover-container w-150 h-20 p-3 mx-auto flex-column">
<header class="mb-auto">
    <div>
      <h3 class="float-md-start mb-0">기쁜우리젊은날</h3>
      <nav class="nav nav-masthead justify-content-center float-md-end">
        <a class="nav-link" aria-current="page" href="http://localhost:8080/ccc/home">Home</a>
        <a class="nav-link active" href="http://localhost:8080/ccc/postForm">새글쓰기</a>
        <a class="nav-link" href="#">Login</a>
      </nav>
    </div>
   
  </header>
   
  <main class="px-3">

	<form action="postFormAction" id="myForm" class="form-inline" method="post">
 	 	<i class="fas fa-search" aria-hidden="true"></i>
			 	 	<table>
		 	 	<tr>
			 	 	<td colspan=3>
					<textarea  name="description" rows="13" cols="46" ></textarea>
					</td>
		 	 	</tr>
		 	 	</table>
		 	 	<div class="mb-3">
  					<label for="formFileSm" class="form-label"></label>
 					<input class="form-control form-control-sm" id="formFileSm" name="picture" type="file">
					</div>
		 	 	<table style="margin-left: auto; margin-right:auto;">
	 	 		<tr>
		 	 		<td><input class="form-control form-control-sm" type="text" name="instaId" placeholder="your instaID" aria-label="instaId"></td>
		 	 		<td colspan=4>
		 	 		<button type="submit" class="btn btn-secondary">V</button>
		 	 		<button type="submit" onclick="myFunction()" class="btn btn-secondary">X</button>
			 	 	</td>
		 	 	</tr>
 	 	</table>
 	 </form>
 	 
 	 </main>
 	 
 	 </div>
</body>
<script>
		function myFunction() {
		 document.getElementById("myForm").reset();
		}
</script>
</html>
