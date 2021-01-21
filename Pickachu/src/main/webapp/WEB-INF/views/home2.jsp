<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
</head>
<body >

<header >
    <div>
      <h3 >기쁜우리젊은날</h3>
        <a class="nav-link active" aria-current="page" href="http://localhost:8080/ccc/home">Home</a>
        <a class="nav-link" aria-current="page" href="#" onclick='newButton()'>새글쓰기</a>
        <a class="nav-link" href="#">Login</a>
    </div>
</header>


<main >
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
			 	  	 	<button  onclick="postOne(${post.postId})">${post.postId} </button>
			 	  	 	
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

		<button  style="display: none;" id="newButton" type="button" class="nav-link" data-bs-toggle="modal" data-bs-target="#newPostModal">새글쓰기 </button>

			<!-- Modal -->
			<div class="modal fade" id="newPostModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
			  <div class="modal-dialog">
			    <div class="modal-content">
			      <div class="modal-header">
			        <h5 class="modal-title" id="staticBackdropLabel">새글 입력할거에요</h5>
			        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			      </div>
			      <div class="modal-body">
			      
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
					 	 		 <button type="submit" onclick="reset()" class="btn btn-secondary">X</button>
					 	 		
						 	 	</td>
					 	 	</tr>
			 	 	</table>
			 	 </form>
			
			      </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
			      </div>
			    </div>
			  </div>
			</div>

		
<!-- 	모달로 읽어올꺼에 -->
	
	<button  style="display: none;" id="rPost" type="button" class="nav-link" data-bs-toggle="modal" data-bs-target="#rPost">포스트읽어오 </button>

			<!-- Modal -->
			<div class="modal fade" id="postOneModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
			  <div class="modal-dialog">
			    <div class="modal-content">
			      <div class="modal-header">
			        <h5 class="modal-title" id="staticBackdropLabel">하나의 포스트를 보여줄거에요</h5>
			        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
			      </div>
			      <div class="modal-body">
			      
			     	   <input class="form-control" type="text" placeholder="${postOne.description }" aria-label="readonly input example" readonly>
  					  <small>${postOne.instaId} :	${postOne.create}   </small><br><br>
			
			      </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
			      </div>
			    </div>
			  </div>
			</div>	


</main>

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
		
		function newButton() {
			  document.getElementById("newButton").click(); 
			}
	
</script>

<script type="text/javascript">
			function postOne(param){
				 $.ajax({
			         url: 'postOneView',
			 	     contentType: 'application/json',
			 	     dataType: 'json',
			    	 method: "GET",
			 	     data: {'postId': param}
			 	})
				.done(function(data) {
					    <!--alert( "success" );-->
					    console.log(data.instaId);
					    console.log(data.description);
				});
				
			}
		</script>

	
	
</html>