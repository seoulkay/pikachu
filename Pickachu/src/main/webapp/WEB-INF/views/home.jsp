<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page session="false" %>
<html>
<head>


	<title>Home</title>
<!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">



<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.1/css/all.css"> 


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
          font-size: 3.5rem;
        }
      }
      
      
	   	const popover = new bootstrap.Popover(document.querySelector('.callPopover'), {
	    container: 'body',
	    title: 'Search',
	    html: true,
	    placement: 'bottom',
	    sanitize: false,
	    content() {
	        return document.querySelector('#PopoverContent').innerHTML;
	    }
		})
      

/* 		input[type=text], input[type=password] { */
/* 		  width: 100%; */
/* 		  padding: 15px; */
/* 		  margin: 5px 0 22px 0; */
/* 		  display: inline-block; */
/* 		  border: none; */
/* 		  background: #f1f1f1; */
/* 		} */
      
    </style>
    
    
    
    <!-- Custom styles for this template -->
    <link href="https://fonts.googleapis.com/css?family=Playfair+Display:700,900" rel="stylesheet">
    <!-- Custom styles for this template -->
<!--     <link href="resources/blog.css" rel="stylesheet"> -->


</head>
<body >


<main class="container py-5" >

	<nav class="navbar fixed-top navbar-light bg-light">
	  <div class="container-fluid">
	    <h2 style="color:black">
		hello stranger. <a href="#" onclick='clickWriteButton(); return false;' style="text-decoration:none; color:red">write down</a> your wisdom. thanks to you we all might overcome depression and anxiety. you can <a href="#" onclick='clickSearchButton(); return false;'   style="text-decoration:none; color:violet">search</a> other's thoughts.
		just become our <a href="#" onclick='clickMemberButton(); return false;' style="text-decoration:none; color:green">member</a> for more of use.
		</h2>
	
	  </div>
	
	</nav>



	<hr class="my-5" >


	<div class="col-12 text-center">
		<c:if test="${not empty search}"  >
			<h2 style="color:blue">your search: <a style="color:violet">${search }</a></h2>
		</c:if>
	</div>

	<hr class="my-5" >


<!-- 포스트리스트 -->
	<div class="row" data-masonry='{"percentPosition": true }' >
		<c:forEach items="${postList }" var="post" begin="0" >
			<div class="col-sm-6 col-lg-4 mb-4">
<!--  				<a href="#" data-toggle="modal" data-target="#onePostModal" style="text-decoration:none; color:blue"> -->
 				<a href="#" onclick="onePost(${post.postId})" style="text-decoration:none; color:blue"> 
					<div class="card text-center p-3">
						<figure class="p-3 mb-0">
							<blockquote class="blockquote">
								<p>
								
					 	  	 	<h3>${post.description }</h3>
					 	  	 	
								</p>
							</blockquote>
							<figcaption class="blockquote-footer mb-1 text-muted">
								
					 	  	 	${post.instaId }
					 	  	 	
							</figcaption>
						</figure>
						
<%-- 						<button id="onePostButton" type="button" onclick="onePost(${post.postId})" class="btn btn-light" data-mdb-ripple-color="dark" style="display:none" > --%>
			  				
<!-- 						</button> -->
						
					</div>
				</a>
			
				
				
			</div>
			
			
			
			
			
		</c:forEach>
	</div>
	
		

	<div class="row flex-nowrap justify-content-between align-items-center">

		<div class="col-3 pt-1">		
			<form action="writeForm" method="GET">
		<!-- 				<button class="btn btn-primary"> -->
		<!-- 				<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil" viewBox="0 0 16 16"> -->
		<!--   				<path d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168l10-10zM11.207 2.5L13.5 4.793 14.793 3.5 12.5 1.207 11.207 2.5zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293l6.5-6.5zm-9.761 5.175l-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325z"/> -->
		<!-- 				</svg> -->
		<!-- 				</button> -->
						
			</form>	
		</div>
	
	
	
	
		<div class="col-7 text-center">
			<nav aria-label="Page navigation example">
				  <ul class="pagination">
				  
				    <li class="page-item">
				 	  <c:if test="${pm.currentPage !=0}">
				      <a class="page-link" href="home?pageSize=${pm.pageSize }&currentPage=${pm.currentPage - 1}" aria-label="Previous" >
				        <span aria-hidden="true">&laquo;</span>
				        <span class="sr-only">Previous</span>
				      </a>
				  	  </c:if>
				    </li>
				    
				    
<!-- 				    <h2>go</h2> -->
				    
				    <c:forEach var="pi" begin="${pm.startPage }" end="${pm.endPage -1}">
				    	<c:choose>
				    		<c:when test="${pi == pm.currentPage }">	
				    			<li class="page-item active">
						    	<a class="page-link" href="home?pageSize=${pm.pageSize }&currentPage=${pi}">${pi +1} 
	
						    	</a></li>
				    		</c:when>
				    		
				    		<c:otherwise>
						    	<li class="page-item">
						    	<a class="page-link" href="home?pageSize=${pm.pageSize }&currentPage=${pi}">${pi +1} 
						    	</a></li>
							</c:otherwise>    
				    	</c:choose>
				    
				    </c:forEach>
				  
				    
				    <li class="page-item">
				    <c:if test="${pm.currentPage != endPage}">
				      <a class="page-link" href="home?pageSize=${pm.pageSize }&currentPage=${pm.currentPage + 1}" aria-label="Next">
				        <span aria-hidden="true">&raquo;</span>
				        <span class="sr-only">Next</span>
				      </a>
				    </c:if>
				    </li>
				    
				  </ul>
				  
			</nav>
		</div>

	</div>




<!-- Button trigger modal -->
<button id="writeButton" type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#writeFormModal" style="display: none;">
  write down
</button>

<!-- Modal -->
<div class="modal fade" id="writeFormModalU" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content" >
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">write down your quote</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      
		<form action="writeFormInput" id="writeFormInput" method="POST">
    	  <div class="modal-body">
      
        	<div class="form-group">
			<input class="form-control input-lg" type="text" name="description" placeholder="description" autofocus>
			</div>
			<div class="form-group">
			<input class="form-control input-lg" type="text" name="instaId" placeholder="name" >
			</div>
     	 </div>
      
      	<div class="modal-footer">
<!--         <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button> -->
        <button  class="btn btn-primary">publish</button>
      	</div>
    
    </form>
    </div>
  </div>
</div>





<button id="searchButton" type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#searchModal" style="display: none">
  search your interests
</button>

<!-- Modal -->
<div class="modal fade" id="searchModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
  	<form action="home" id="myForm" class="form-inline" method="GET">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">search your interests</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
      

				<input class="form-control" type="text" name="search" value="${search }" placeholder="subject, people, etc">
		
      </div>
      
      <div class="modal-footer">
<!--         <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button> -->
        <button  class="btn btn-primary">search</button>
      </div>
    </div>
    </form>
  </div>
</div>


<!-- MEMBER -->
<!-- Button trigger modal -->
<button id="memberButton" type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#signUpFormModal" style="display: none;">
  sign up
</button>

<!-- Modal -->
<div class="modal fade" id="signUpFormModalU" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content" >
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">join us now</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      
		<form action="signUpFormInput" id="signUpFormInput" method="POST">
    	  <div class="modal-body">
      
        	<div class="form-group">
        	
			<input class="form-control input-lg" type="text" name="eMailMember" placeholder="Email" id="eMailMember"autofocus>
			<button type="button" class="btn btn-secondary" onclick="eMailCheck(eMailMember)">check</button>
			
			</div>
			
			<div class="form-group">
			<input class="form-control input-lg" type="text" name="penName" placeholder="Pen Name" id="penName">
			<button type="button" class="btn btn-secondary" onclick="penNameCheck(penName)">check</button>
			</div>
			
			<div class="form-group">
			<input class="form-control input-lg" type="password" name="passwordMember" placeholder="Password" id="passwordMember" >
			</div>
			<div class="form-group">
			<input class="form-control input-lg" type="password" name="passwordMemberRepeat" placeholder="Confirm Password" id="passwordMemberRepeat" >
			</div>

     	 </div>
      

      	<div class="modal-footer">
		<h5 id="eMailErrorMsg"></h5>
		<h5 id="penNameErrorMsg"></h5>
        <button type="button" class="btn btn-primary" onclick="signUpSubmitCheck()">sign up</button>
      	</div>
    	
<%--     	<c:if test="${member.passwordMember != passwordMemberRepeat}"> --%>
<!-- 		    alert( "success" ); -->
<%-- 		</c:if> --%>
    	
    </form>
    </div>
  </div>
</div>



<!-- ONE POST -->

<!-- Modal -->
<div class="modal fade" id="onePostModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="modalInsId"></h5>
        <h5 class="modal-title" id="modalUpdateHeader">update your quotes.</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      	
      	
      	<form action="updatePostAjax" id="updateInput" method="POST"> 
      		<input id="updatePostId" type="hidden">
      	
    	  <div class="modal-body">
      		
      		
      		<div class="form-group" >
			<input class="form-control input-lg" type="text" id="updateDescription" name="description" placeholder="description" autofocus>
			</div>
			<div class="form-group" >
			<input class="form-control input-lg" type="text" id="updateInstaId" name="instaId" placeholder="name" >
			</div>
			
			
<!--       		<div class="spinner-border text-primary" id="spinner" ></div> -->
      
        	<h4 class="form-group" id="modalDesc" style="color:blue">
			
			</h4>
			<div class="form-group" >
			
			</div>
     	 </div>
      
      	<div class="modal-footer">
<!--         <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button> -->
<!--         <button  class="btn btn-primary">publish</button> -->
			<button
			  type="button"
			  class="btn btn-outline-success btn-floating"
			  data-mdb-ripple-color="dark"
			>
			  <i class="fas fa-star"></i>
			</button>
			
			<button type="button" class="btn btn-danger btn-floating" id="updateButton">
			  <i class="fas fa-magic"></i>
			</button>
			
			<button type="button" onclick="updatePost(description, instaId)" class="btn btn-dark btn-floating" >
			  <i class="fab fa-apple"></i>
			</button>
		
      	</div>
   	 </form>
<!--     </form> -->
    </div>
  </div>
</div>



</main>




<script type='text/javascript'>

//업데이트 여부 확인
var updated = false;

function openModal(){
	var myModal = document.getElementById('writeFormModal')
	var myInput = document.getElementById('myInput')
}

function signUpSubmitCheck(){

	var str = $('#passwordMember');
	var re = "/^(?=.*[a-zA-Z])((?=.*\d)|(?=.*\W)).{6,20}$/";
	var found = str.match(re);
	
	if(found==true){
		//패스워드 비교 조건문
			if($('#passwordMember')==$('#passwordMemberRepeat')){
				
				if(eMailCheckOk){
					if(penNameCheckOk){
						//버튼 클릭시 서브밋을 해라.										
					}else{
						$('#penNameErrorMsg').html("펜네임 중복확인을 해주세요.");	
					}
				}else{
					$('#eMailErrorMsg').html("아이디 중복확인을 해주세요.");	
				}
			}else{
				$('#penNameErrorMsg').html("비밀번호가 일치하지 않습니다.");	
				//경고를 띄우든지, 뭘 해라.
				//폼 안에 글을 하나 쓰게 html<h3 id="fdaf"></h3>만들어놓고, 여기서 $(#아이디).html(""); 넣어
			}
		
	}else{
		//경고를 띄우든지, 뭘 해라.
		//폼 안에 글을 하나 쓰게 html<h3 id="fdaf"></h3>만들어놓고, 여기서 $(#아이디).html(""); 넣어
		$('#penNameErrorMsg').html("비밀번호는 6~20자 영문 대소문자 및 숫자를 혼합하여 지정해주세요.");
	}
}

//console.log(found);



</script>




<script>
// On mouse-over, execute myFunction
function clickWriteButton() {
	$('#writeFormModalU').modal('show'); 
}
</script>

<script>
// On mouse-over, execute myFunction
function clickSearchButton() {
  document.getElementById("searchButton").click(); // Click on the checkbox
}
</script>

<script>
function clickMemberButton() {
	$('#signUpFormModalU').modal('show'); 
}
</script>


<script>
// On mouse-over, execute myFunction

//파라미터를 받아서 펑션 안에서 모달을 직접 띄워라.
function clickOnePostButton() {
  document.getElementById("onePostButton").click(); // Click on the checkbox
}
</script>




<!-- <script src="http://code.jquery.com/jquery.min.js"></script> -->


<script 
  src="http://code.jquery.com/jquery-3.5.1.js"
  integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc="
  crossorigin="anonymous"></script>



  <script type="text/javascript">

	  	$("#updateButton").click(function(){

	  		$('#modalUpdateHeader').show();
	  		
	  		$('#modalInsId').hide();
	  		$('#modalDesc').hide();
	  		
	  		$('#updateDescription').attr("placeholder", $('#modalDesc').text());
	  		$('#updateInstaId').attr("placeholder", $('#modalInsId').text());
	  			
	  		$('#updateDescription').show();
	  		$('#updateInstaId').show();
	  			  		
	  	
  		})

  </script>



		<script type="text/javascript">
		
		
			var eMailCheckOk = false;
			var penNameCheckOk = false;
			
			function eMailCheck(param){
				
				 $.ajax({
			         url: 'eMailCheckAjax',
			 	        
			         //팬네임 체크 
			         //포스트로 보내는 걸 추천
			         
			    	 method: "POST",
			 	     data: {'eMailMember': param}
				 	
			 	})
				.done(function(data) {
					
					//펜네임 체크
					//데이터가 오면 여기서 이프를 걸어라.
					if(data=1){
					//	아이디 중복 .html("아아디 중복입니다.");
						$('#eMailErrorMsg').html("Email is already used.");
					}else{
						$('#eMailErrorMsg').html("Email is ok.");
						//	아이디 ok .html("아아디 사용이 가능합니다.");
						eMailCheckOk = true;
					}

				});
				
			}
			
			
			function penNameCheck(param){
				
				 $.ajax({
			         url: 'penNameCheckAjax',
			 	        
			         //팬네임 체크 
			         //포스트로 보내는 걸 추천
			         
			    	 method: "POST",
			 	     data: {'penName': param}
				 	
			 	})
				.done(function(data) {
					
					//펜네임 체크
					//데이터가 오면 여기서 이프를 걸어라.
					if(data=1){
					//	아이디 중복 .html("아아디 중복입니다.");
						$('#penNameErrorMsg').html("Pen name is already used.");
					}else{
						$('#penNameErrorMsg').html("Pen name is ok.");
						//	아이디 ok .html("아아디 사용이 가능합니다.");
						penNameCheckOk = true;
					}

				});
				
			}
			
			
			
			function onePost(param){
				
				
				 $.ajax({
			         url: 'onePostViewAjax',
			 	        
			         //팬네임 체크 
			         //포스트로 보내는 걸 추천
			         
			    	 method: "GET",
			 	     data: {'postId': param}
				 
				 	
				 	
			 	})
				.done(function(data) {
					
					//펜네임 체크
					//데이터가 오면 여기서 이프를 걸어라.
					//if(data=1){
					//	아이디 중복 .html("아아디 중복입니다.");
					//	아이디 ok .html("아아디 사용이 가능합니다.");
					//  idCheckOk = true;
					
					
						$('#modalUpdateHeader').hide();
						$('#updateDescription').hide();
						$('#updateInstaId').hide();
						
						$('#modalInsId').show();
				  		$('#modalDesc').show();
					    <!--alert( "success" );-->
					    console.log(data.instaId);
					    console.log(data.description);
					    console.log(data.postId);
					    
					    
					    // Add data in Modal body
					    $('#updatePostId').val(data.postId);
					    $('#modalDesc').html(data.description);
					    $('#modalInsId').html(data.instaId);
					    
					    
					    // Display Modal
					    $('#onePostModal').modal('show'); 
					    
					    
					    //console.log(('#updatePostId').val());
				});
				
			}
				
				
				
				
				
				
		</script>
		
		<script type="text/javascript">
			
			
			
			function updatePost(){
								
				var postId = $('#updatePostId').val();
				var description = $('#updateDescription').val();
				var instaId = $('#updateInstaId').val();
				
				 $.ajax({
			         url: 'updatePostAjax',
			 	        
			    	 method: "POST",
			 	     data: {'postId': postId, 'instaId' : instaId, 'description' : description}				 	
				 			//'컨트롤러에서 받는 아이디' : 여기에서의 변수 
			 	})
				.done(function(data) {
					    
					    console.log(data.instaId);
					    console.log(data.description);
					    
					    
					    
					    // Add data in Modal body
					    $('#modalDesc').html(data.description);
					    $('#modalInsId').html(data.instaId);
					    
					    
				  		$('#updateDescription').hide();
				  		$('#updateInstaId').hide();

					    
					    $('#modalInsId').show();
				  		$('#modalDesc').show();
				  		
				  		updated=true;
					    // Display Modal
					    //$('#onePostModal').modal('show'); 
				});
				
			}
			
			$('#onePostModal').on('hidden.bs.modal', function () {
				if(updated){
					location.reload();
					console.log("리프레시 페이지");
				}else{
					console.log("업데이트가 안됨, 고로 리프레시 안함");
				}  
				
				})
		</script>
		







<!-- Optional JavaScript; choose one of the two! -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
<!--     <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js" integrity="sha384-q2kxQ16AaE6UbzuKqyBE9/u/KzioAlnx2maXQHiDX9d4/zp8Ok3f+M7DPm+Ib6IU" crossorigin="anonymous"></script> -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.min.js" integrity="sha384-pQQkAEnwaBkjpqZ8RU1fF1AKtTcHJwFl3pblpTlHXybJjHpMYo79HY3hIi4NKxyj" crossorigin="anonymous"></script>
<script defer src="https://use.fontawesome.com/releases/v5.8.1/js/all.js"></script>

<!-- 메이슨리 -->
<script src="https://cdn.jsdelivr.net/npm/masonry-layout@4.2.2/dist/masonry.pkgd.min.js" integrity="sha384-GNFwBvfVxBkLMJpYMOABq3c+d3KnQxudP/mGPkzpZSTYykLBNsZEnG2D9G/X/+7D" crossorigin="anonymous" async></script>



<script async src="https://cdn.jsdelivr.net/npm/masonry-layout@4.2.2/dist/masonry.pkgd.min.js" integrity="sha384-GNFwBvfVxBkLMJpYMOABq3c+d3KnQxudP/mGPkzpZSTYykLBNsZEnG2D9G/X/+7D" crossorigin="anonymous"></script>




</body>
</html>
