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
<link href="resources/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">

    

    <!-- Bootstrap core CSS -->
<link href="resources/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">

    <!-- Favicons -->
<link rel="apple-touch-icon" href="/docs/5.0/assets/img/favicons/apple-touch-icon.png" sizes="180x180">
<link rel="icon" href="/docs/5.0/assets/img/favicons/favicon-32x32.png" sizes="32x32" type="image/png">
<link rel="icon" href="/docs/5.0/assets/img/favicons/favicon-16x16.png" sizes="16x16" type="image/png">
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
<script src="resources/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>

<div class="cover-container w-150 h-100 p-3 mx-auto flex-column">

<header class="mb-auto">
    <div>
      <h3 class="float-md-start mb-0">기쁜우리젊은날</h3>
      <nav class="nav nav-masthead justify-content-center float-md-end">
        <a class="nav-link active" aria-current="page" href="http://localhost:8080/ccc/home">Home</a>
        <a class="nav-link" aria-current="page" href="#" onclick='newButton()'>새글쓰기</a>
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
			 	  	 	<a href="#" onclick="postOne(${post.postId})">${post.description }</a>
		
			 	  	 	
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

  <!-- modal 시작  -->
		<!-- Button trigger modal -->

			<!-- Modal -->
			<div class="modal fade" id="newPostModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
			  <div class="modal-dialog">
			    <div class="modal-content">
			      <div class="modal-header">
			        <h5 class="modal-title" id="staticBackdropLabel">새글 입력할거에요</h5>
			        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			      </div>
			      
			      
			       <form action="postFormAction" id="myForm" class="form-inline" method="post">
			     	 <div class="modal-body">
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
					
							 	 	</td>
						 	 	</tr>
				 	 	</table>
					      <div class="modal-footer">
					      <button  type="submit" class="btn btn-secondary">V</button>
					      <button  onclick="reset()" class="btn btn-secondary">X</button>
					      </div>
				      
				     	 </div>
			      
			       </form>
			       
			       
			    </div>
			  </div>
			</div>

		
<!-- 	모달로 읽어올꺼에 -->
	
			<!-- Modal -->
			<div class="modal fade" id="postOneModal">
			  <div class="modal-dialog">
			    <div class="modal-content">
			      <div class="modal-header">
			        <h5 class="modal-title" id="staticBackdropLabel">하나의 포스트를 보여줄거에요</h5>
			        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			      </div>
			      <form action="postOneUpdate" id="myForm" class="form-inline" method="post">
			      <input type="hidden" name="postId" id="updatePostId">
			      <div class="modal-body">
			     	   <input class="form-control" type="text" aria-label="readonly input example" readonly id="postDescription">
  					  <small id="instaId">   </small><br><br>
	  					  	
							<button type="button" onclick="updatePostInput()" class="btn btn-outline-secondary" id="inputUpButton">update</button>
							
							
							
							
							
							
<!-- 							<form action="postUpdateAction" id="myForm" class="form-inline" method="post"> -->
<!-- 						 	 	<i class="fas fa-search" aria-hidden="true"></i> -->
<!-- 						 	 	<textarea  name="description" style="resize: none;" rows="5" cols="40" id="updateDescriptionInput"></textarea> -->
<!-- 						 	 	<div class="mb-3" id="updateFile"> -->
<!-- 					  				<label for="formFileSm" class="form-label"></label> -->
<!-- 					 				<input class="form-control form-control-sm" name="picture" type="file"> -->
<!-- 								</div> -->
<!-- 	  					    	<input class="form-control form-control-sm" type="text" name="instaId" aria-label="instaId" id="updateInstaIdInput">	 -->
<!-- 								<input type="hidden" name="postId" id="updatePostId"> -->
<!-- 								<button type="submit" class="btn btn-secondary" id="goUpButton">V</button> -->
<!-- 						 	 </form> -->
						 	 
						 	 
						 	 	<i class="fas fa-search" aria-hidden="true"></i>
						 	 	<textarea  name="description" style="resize: none;" rows="5" cols="40" id="updateDescriptionInput"></textarea>
						 	 	<div class="mb-3">
					  				<label for="formFileSm" class="form-label"></label>
					 				<input class="form-control form-control-sm" name="picture" type="file" id="updateFile">
								</div>
	  					    	<input class="form-control form-control-sm" type="text" name="instaId" aria-label="instaId" id="updateInstaIdInput">	
								
								<button type="button" onclick="goPostUpdate(description, instaId)" class="btn btn-secondary" id="goUpButton">V</button>
						 	 
							
							
							
			      </div>
			      
			       <div class="modal-footer">
			        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
			      </div>
			      
			      </form>
			      
			     
			    </div>
			  </div>
			</div>	





<div id="loadingSpinner" style="display:none" class="spinner-border"></div>

</main>
</div>

</body>
		
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js" integrity="sha384-q2kxQ16AaE6UbzuKqyBE9/u/KzioAlnx2maXQHiDX9d4/zp8Ok3f+M7DPm+Ib6IU" crossorigin="anonymous"></script>
    <script src="resources/bootstrap.min.js" integrity="sha384-pQQkAEnwaBkjpqZ8RU1fF1AKtTcHJwFl3pblpTlHXybJjHpMYo79HY3hIi4NKxyj" crossorigin="anonymous"></script>		
		
<script src="http://code.jquery.com/jquery-3.5.1.js"
  integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
  crossorigin="anonymous">
		var myModal = document.getElementById('staticBackdrop')
		var myInput = document.getElementById('staticBackdrop')
		
		myModal.addEventListener('shown.bs.modal', function () {
		  myInput.focus()
		})
		function openModal()
		{
			 myInput.focus()
		}
		
		function reset() {
			 document.getElementById("myForm").reset();
			}
		</script>	
		
		<script type="text/javascript">	
		function newButton() {
			$('#loadingSpinner').show();
			$('#loadingSpinner').hide();
			$('#newPostModal').modal('show');  
			}
	
		</script>

		<script type="text/javascript">
			function postOne(param){
				 $.ajax({
			         url: 'postOneView',
			 	     contentType: 'application/json',
			    	 method: "GET",
			 	     data: {'postId': param},
			 	     beforeSend: function () {
			 	    	 console.log("아닌거 같은데");
			 	    	 //스피너를 돌리면 
			 	    	 $('#loadingSpinner').show();
			 	    	$('#inputUpButton').show();
			 	     },
			 	})
				.done(function(data) {
						$('#updateDescriptionInput').hide(); 
						
						$('#postDescription').show(); 
						$('#instaId').show(); 
						$('#updateFile').hide(); 
						$('#updateInstaIdInput').hide(); 
						$('#goUpButton').hide(); 
						$('#updatePostId').val(data.postId);
						
						$('#updateDescriptionInput').html(data.description);
						$('#updateInstaIdInput').val(data.instaId);
						$('#postDescription').attr("placeholder",data.description);
						$('#instaId').html(data.instaId);
						$('#loadingSpinner').hide();
						$('#postOneModal').modal('show');   
						$('#updatePostId').html(data.postId);
						
				});

			}
		</script>

		<script type="text/javascript">
			function updatePostInput(){
				
				$('#postDescription').hide(); 
				$('#instaId').hide(); 
				$('#updateDescriptionInput').show(); 
				$('#updateFile').show(); 
				$('#updateInstaIdInput').show(); 
				$('#inputUpButton').hide(); 
				$('#goUpButton').show(); 
				
			}	
		</script>


		<script type="text/javascript">
			function goPostUpdate(){
				
					var postId = $('#updatePostId');
					var picture = $('#updateFile').val();
					var description = $('#updateDescriptionInput').val();
					var instaId = $('#updateInstaIdInput').val();
					
				 $.ajax({
			         url: 'postOneUpdate',
			    	 method: "POST",
			 	     data: {'postId': postId, 'description': description , 'picture' : picture, 'instaId' : instaId}

			 	})
				.done(function(data) {
					$('#postOneModal').modal('hide'); 
					
				});

			}
		</script>
	
	
</html>