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
      
/*       login */
      
    	   
      
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
        <a class="nav-link" aria-current="page" href="#" onclick='newButton()'>새글쓰기</a>
        <a class="nav-link" href="#" onclick="document.getElementById('id01').style.display='block'">Login</a>
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
							 	 	<c:if test="${pm.currentPage != 0 }"> 
									<a class="page-link" href="http://localhost:8080/ccc/home?pageSize=${totalSize.pageSize }&currentPage=${pm.currentPage - 1}&search=${search}" aria-label="Previous">
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
								<li class="page-item <c:out value="${idx == pm.currentPage ? 'active' : '' }"/>">
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
								<c:if test="${pm.currentPage != showPage}">
									<a class="page-link" href="http://localhost:8080/ccc/home?pageSize=${totalSize.pageSize }&currentPage=${pm.currentPage + 1}&search=${search}" aria-label="Next">
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
			      
			      
			       <form action="postFormAction" class="form-inline" method="post">
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
					      <button  type="submit" class="btn btn-secondary">✔️</button>
					      <button  type="button" onclick="reset()" class="btn btn-secondary">⬅️</button>
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
						 	 
						 	 <form action="" class="form-inline" method="post">
						 	 	<i class="fas fa-search" aria-hidden="true"></i>
						 	 	<textarea  name="description" style="resize: none;" rows="5" cols="40" id="updateDescriptionInput"></textarea>
						 	 	<div class="mb-3">
					  				<label for="formFileSm" class="form-label"></label>
					 				<input class="form-control form-control-sm" name="picture" type="file" id="updateFile">
								</div>
	  					    	<input class="form-control form-control-sm" type="text" name="instaId" aria-label="instaId" id="updateInstaIdInput">	
								<input type="hidden" name="postId" id="updatePostId">
								<button type="button" onclick="goPostUpdate()" class="btn btn-secondary" id="goUpButton">V</button>
						 	 </form>
							
							
							
			      </div>
			      
			       <div class="modal-footer">
			        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
			      </div>
			      
			      
			      
			     
			    </div>
			  </div>
			</div>	





<div id="loadingSpinner" style="display:none" class="spinner-border"></div>

</main>
</div>

<!-- 	 sing up -->
			
			
<!-- 			<button onclick="document.getElementById('id01').style.display='block'" style="width:auto;">Sign Up</button> -->
			
			<div id="id01" class="modal">
			<div class="modal-dialog">
			<div class="modal-content">
			  <span onclick="document.getElementById('id01').style.display='none'" class="close" title="Close Modal">&times;</span>
			  <form class="modal-content" action="/ccc/memberAction" method="post" id="signupForm">
			    <div class="container">
			    <div class="modal-header">
			      <h1>Sign Up</h1>
			      <p>Please fill in this form to create an account.</p>
			      </div>
			      <hr>
			      <input class="form-control" type="text" aria-label="readonly input example" readonly id="signInMessage">
			      <div>
			      <label for="email"><b>Email</b></label>
			      <input type="text" placeholder="Enter Email" name="id" required id="idInput">
			       <button type="button" onclick="idCheck()" class="cancelbtn">중복체크</button><br>
			       </div>
			       
			      <div>
				  <label for="email"><b>Name</b></label>
			      <input type="text" placeholder="Enter Name" name="nickName" required id="nickInput"><br>
			      <button type="button" onclick="nickCheck()" class="cancelbtn">중복체크</button><br>
			      </div>
			      
			      <div>
			      <label for="psw"><b>Password</b></label>
			      <input type="password" placeholder="숫자 및 문자하나가 들어가게 입력해주세요." name="password" required id="passwordInput"><br>
			      </div>
			
			      <label for="psw-repeat"><b>Repeat Password</b></label>
			      <input type="password" placeholder="Repeat Password" name="passwordConfirm" required id="passwordConfirm"><br>
			      
			      <input type="hidden" name="role" value="nomal" required>
<!-- 			      <label> -->
<!-- 			        <input type="checkbox" checked="checked" name="remember" style="margin-bottom:15px"> Remember me -->
<!-- 			      </label> -->
			
			      <p>By creating an account you agree to our <a href="#" style="color:dodgerblue">Terms & Privacy</a>.</p>
			
			      <div class="clearfix">
			        <button type="button" onclick="document.getElementById('id01').style.display='none'" class="cancelbtn">Cancel</button>
			        <button type="button" onclick="signupSubmitChecker()" class="signupbtn" id="signUpSub">Sign Up</button>
			      </div>
			    </div>
			  </form>
			  </div>
			  </div>
			</div>
			
			<script>
			// Get the modal
			var modal = document.getElementById('id01');
			
			// When the user clicks anywhere outside of the modal, close it
			window.onclick = function(event) {
			  if (event.target == modal) {
			    modal.style.display = "none";
			  }
			}
			</script>

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
				
				var postId = $('#updatePostId').val();
				var description = $('#updateDescriptionInput').val();
				var instaId = $('#updateInstaIdInput').val();
				var picture = $('#updateFile').val();
				
				 $.ajax({
			         url: 'postOneUpdate',
			    	 method: "POST",
			 	     data: {'postId': postId, 'description': description , 'picture' : picture, 'instaId' : instaId}
					 
			 	})
				.done(function(data) {
					$('#loadingSpinner').show();
					$('#postOneModal').modal('hide'); 
					updated=true;
				});

			}
			
			$('#postOneModal').on('hidden.bs.modal', function () {
				if(updated){
					
					location.reload();
					console.log("리프레시 페이지");
					$('#loadingSpinner').hide();
				}else{
					console.log("업데이트가 안됨, 고로 리프레시 안함");
				}  
				
				})
		</script>
		
		
		<script>
		
		
		function signupSubmitChecker(){
			var passwordConfirm = $('#passwordConfirm').val();
			var str = $('#passwordInput').val();
			var re = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;
			var found = re.test(str);
			console.log(str, passwordConfirm);
			
			if(found==true){
				console.log("1번");
				if(passwordConfirm==str){
					console.log("2번");
					if(idCheckOk==true){
						console.log("3번");
						if(nickNameCheckOk==true){
							console.log("4번");
							
							$('#signInMessage').attr("placeholder", "가입을 진행합니다");
							$('#signupForm').submit();
							
							}else{
								$('#signInMessage').attr("placeholder", "이름 중복확인 해주세요");
							}
						}else{
							$('#signInMessage').attr("placeholder", "Email 중복확인 해주세요");
					}
					
				}else{
					$('#signInMessage').attr("placeholder", "비밀번호 재확인이 틀렸습니다.");
				
				}
				
			}else{
				console.log("6번");
				$('#signInMessage').attr("placeholder", "비밀번호를 형식에 맞게 입력해주세요.");
			}
			
			console.log(found);
		}


		var idCheckOk = 0; //현재는 체크안한 상태
		var nickNameCheckOk = 0; //현재는 체크안한 상태 

		function idCheck(){
			
			var id = $('#idInput').val();
			
			 $.ajax({
		         url: 'idCheck',
		    	 method: "POST",
		 	     data: {'id': id}
		 	     })
		 	
			.done(function(data) {
				if(data==1){
					
					$('#idInput').val('');
					$('#signInMessage').attr("placeholder", "해당 email은 사용 할 수 습니다.");
					//사용할수 없습니다를 띄어
					}else{
					$('#signInMessage').attr("placeholder", "해당 email은 사용 할 수 있습니다.");
					idCheckOk = true;
					//사용할수 있습니다를 띄어 
					//idCheckOk = true;
					}
				})
			}
			
		
			function nickCheck(){
				
				var nick = $('#nickInput').val();
				 $.ajax({
			         url: 'nickCheck',
			    	 method: "POST",
			 	     data: {'nickName': nick}
				 	})
				 	
				.done(function(data) {
					if(data==1){
						
							$('#nickInput').val('');
							$('#nickInput').attr("placeholder", "해당 이름은 사용 할 수 없습니다.");
							//사용할수 없습니다를 띄어
						}else{
							$('#signInMessage').attr("placeholder", "해당 이름은 사용 할 수 있습니다.");
							nickNameCheckOk = true;
							//사용할수 있습니다를 띄어 
						}
					})
				}
		

			
		</script>
	
</html>