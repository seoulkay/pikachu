<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>


<head>
 <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta name="description" content="현재 네이버와 다음의 뉴스중 핫한 단어모음 50개를 보여주는 사이트">
    <meta name="keywords" content="오늘의 뉴스">
    <meta name="author" content="tellmebaby">
    <meta name="application-name" content="뉴스단어장">
	<script src="${pageContext.request.contextPath}/resources/admin/plugins/jquery/jquery.min.js"></script>
	
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/locale/ko.js"></script>
	<script src="https://d3js.org/d3.v3.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/admin/dist/js/pages/d3.layout.cloud.js"></script>
	
	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300&display=swap" rel="stylesheet">
	<link rel="preconnect" href="https://fonts.gstatic.com">
	<link href="https://fonts.googleapis.com/css2?family=Noto+Serif+KR:wght@500&display=swap" rel="stylesheet">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/admin/plugins/icheck-bootstrap/icheck-bootstrap.min.css">

	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
 	 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
 	 <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

 <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-wEmeIV1mKuiNpC+IOBjI7aAzPcEZeedi5yW5f2yOq55WWLwNGmvvx4Um1vskeMj0" crossorigin="anonymous">
 <title>지금뉴스</title>
	<script data-ad-client="ca-pub-2029088903535235" async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
	
	<style>
    body {
        font-family:'Noto Serif KR', serif;
    }
    .legend {
        border: 1px solid #555555;
        border-radius: 5px 5px 5px 5px;
        font-size: 0.8em;
        margin: 10px;
        padding: 8px;
    }
    .bld {
     font-weight: bold;
    }
    .button {
  	 background-color: white;
 	 border: none;
 	 color: white;
	 padding: 15px 15px;
 	 text-align: center;
   	 text-decoration: none;
 	 display: inline-block;
  	 font-size: 16px;
 	 margin: 4px 2px;
  	 cursor: pointer;
	}
	.outer {
  	text-align: center;
  	} 	
  	.inner {
  	 display: inline-block;
  	}
	.centered { display: table; margin-left: auto; argin-right: auto;}
	a:link {text-decoration: none; text-align: center;	  	
    }
    a:visited {color:#646464; text-decoration: none;	  	
    }
    a:hover { color:#646464; text-decoration: none;	  	
    }
    small {color:#0C90AD;
    }
	</style>
</head>

<body>
				<div class="container-fluid">
					<div class="row" >
<!-- 						<div class="col-md-auto"> -->
<!-- 						<h3></h3> -->
<!-- 						<small> 시간대별 가장많이 노출된 뉴스단어 Top50 </small> -->
<!-- 						</div> -->
					<figure class="text-center">
  
    <h1 class="display-4"><a href="index6?source=naver">지금뉴스</a></h1>
    <h1 class="display-6"><a href="index6?source=naver">TOP50</a></h1>
  
</figure>
					</div>
				
					
				
					<div class="row" >
						<div class="col" id="my_dataviz"></div>
						
					</div>

				<div class="row justify-content-md-center" >
						
						<div class="col-md-auto">
							<select onchange="if(this.value) location.href=(this.value);" class="form-select form-select-lg mb-3" aria-label=".form-select-lg example">
							 
							  <option value="index6?source=naver" id="naver_select">네이버 뉴스</option>
							  <option value="index6?source=daum" id="daum_select">다음 뉴스</option>
							  <option value="index6?source=naverent" id="naverent_select">네이버연예 뉴스</option>
							  <option value="index6?source=daument" id="daument_select">다음연예 뉴스</option>
							  <option value="index6?source=navercar" id="navercar_select">네이버 자동차 뉴스</option>
							  <option value="index6?source=daumcar" id="daumcar_select">다음 자동차 뉴스</option>
							  <option value="index6?source=daumeco" id="daumeco_select">다음 경제 뉴스</option>
							  <option value="index6?source=navereco" id="navereco_select">네이버 경제 뉴스</option>
							  <option value="index6?source=daumwor" id="daumwor_select">다음 국제 뉴스</option>
							  <option value="index6?source=navernav" id="navernav_select">네이버 사회 뉴스</option>
							  <option value="index6?source=naverwor" id="naverwor_select">네이버 세계 뉴스</option>
							  <option value="index6?source=naversports" id="naversports_select">네이버 스포츠 뉴스</option>
							  <option value="index6?source=daumsports" id="daumsports_select">다음 스포츠 뉴스</option>
							  <option value="index6?source=naverland" id="naverland_select">네이버 부동산 뉴스</option>
							  <option value="index6?source=daumland" id="daumland_select">다음 부동산 뉴스</option>
							  <option value="index6?source=naverit" id="naverit_select">네이버 IT 뉴스</option>
							</select>
							<select class="form-select form-select-lg mb-3" aria-label=".form-select-lg example" onchange="drawWordcloud(this.value)" id="timeSelector">
						</select>
						 </div>
						
						</div>
						
	<!-- 			Carousel -->
			 
				  <div id="myCarousel" class="carousel slide" data-ride="carousel">
				    <!-- Indicators -->
				    <ol class="carousel-indicators">
				      <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
				      <li data-target="#myCarousel" data-slide-to="1"></li>
				      <li data-target="#myCarousel" data-slide-to="2"></li>
				    </ol>

    <!-- Wrapper for slides -->
			    <div style="height:100px" class="carousel-inner">
			      <div class="item active">
			      <a href="http://kimsouvenir.cafe24.com">
			        <img src="http://kimsouvenir.cafe24.com/web/upload/dnd_image/skin1/index/1132/2021/06/24/grid_id_1575522766878_7_5fca98.jpeg" alt="Los Angeles" style="width:100%;">
			     	</a>
			      </div>
			
			      <div style="height:100px" class="item">
			      <a href="http://tellmebabyshop.cafe24.com/">
			        <img src="http://tellmebabyshop.cafe24.com/web/product/big/202106/230c9576d26e48a5a80ec48b42e9d54a.png" alt="Chicago" style="width:100%;">
			      </a>
			      </div>
			    
			      <div style="height:100px" class="item">
			      <a href="https://www.balenciaga.com/en-kr">
			        <img src="https://balenciaga.dam.kering.com/m/656009906b92f9f1/Thumbnail-661720TAV041000_D.jpg?v=1" alt="New york" style="width:100%; ">
			      </a>
			      </div>
			    </div>
			
			    <!-- Left and right controls -->
			    <a class="left carousel-control" href="#myCarousel" data-slide="prev">
			      <span class="glyphicon glyphicon-chevron-left"></span>
			      <span class="sr-only">Previous</span>
			    </a>
			    <a class="right carousel-control" href="#myCarousel" data-slide="next">
			      <span class="glyphicon glyphicon-chevron-right"></span>
			      <span class="sr-only">Next</span>
			    </a>
			  </div>
			
						
						
				</div>
				
			<p>
			

<!-- 		     	<div class="row justify-content-md-center" > -->
						
<!-- 						<div class="col-md-auto"> -->
<!-- 						<select class="form-select form-select-lg mb-3" aria-label=".form-select-lg example" onchange="drawWordcloud(this.value)" id="timeSelector"> -->
<!-- 						</select> -->
<!-- 						 </div> -->
						
<!-- 						</div> -->
			
		 <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-p34f1UUtsS3wqzfto5wAAmdvj+osOnFyQFpp4Ua3gs/ZVWx6oOypYoCJhGGScy+8" crossorigin="anonymous"></script>
		<script>
								console.log(window.outerWidth);
								
								var frequency_list = {};
								
								var color = d3.scale.linear()
								.domain([100,70,60,50,40,30,20,10,8,5,1])
// 								.range(["#ddd", "#ccc", "#bbb", "#aaa", "#999", "#888", "#777", "#666", "#555", "#444", "#333", "#222"]);
								.range(["#F8F1E9", "#F8F1E9", "#DAEFF5", "#F3E4E7", "#91E0F4", "#91E0F4", "#F3E4E7", "#F3E4E7", "#B1AFCD", "#B1AFCD", "#0C90AD", "#0C90AD"]);
								
									var fill = d3.scale.category20();
									
									var weight = 1, //글자크기정하기 
									    width = window.innerWidth - 21,
									    height = window.innerHeight - 300;
									
									function draw(words) {
									         d3.select("#my_dataviz").append("svg")
									           .attr("width", width)
									           .attr("height", height)
									           .append("g")
									           .attr("transform", "translate(" + width/2 + "," + height/2 + ")")   
									           .selectAll("text")
									           .data(words)
									           .enter().append("text")
									           .style("font-size", function(d) { return d.size + "px"; })
									           .style("font-family", "Noto Sans KR")
									           .style("fill", function(d, i) { return color(i); })   
									           .attr("text-anchor", "middle")
									           .attr("transform", function(d) {
									           	return "translate(" + [d.x, d.y] + ")rotate(" + d.rotate + ")";
									            })
									           .append("a")
										       .attr("href", function(d) {
										        return "${url}"+d.text;
										        })
										       .attr("target", function(d) {
										        return "_blank";
										        })
										       .text(function(d) { return  d.text ; });									
									        }
		
									var selectId = 1;
									var alpha = 1;
									
									function news20Ajax(p1){
									
									var source = p1;
											
										$.ajax({
								         url: '/ccc/get/news202',
								    	 method: "GET",
								 	     data: {'data': source }
								 	     })
										.done(function(data) {
										
										frequency_list = data;
										console.log(frequency_list[0].top20[0].size);
										alpha = 65/frequency_list[0].top20[0].size;
										console.log(alpha);
										drawWordcloud(9);
										//타임 셀렉터에 옵션을 자동으로 추가. 
										// 반복으로 frequency_list만큼 append 
										for(var i=frequency_list.length - 1 ; i > -1  ; i-- ) {
											$("#timeSelector").append('<option value='+i+'>'+timestampToHumanReader(frequency_list[i].created)+'</option>');
										}
										
										})
									}
									news20Ajax('${source}');
									
									function drawWordcloud(p1){
										$( "#my_dataviz" ).empty();
										d3.layout.cloud().size([width,height])
								        .words(frequency_list[p1].top20)
								        .rotate(0)
								        .padding(1)
								        .fontSize(function(d) { return d.size * weight * alpha; })
								        .on("end" , draw)
								        .start();
							
									}
								
									
									function timestampToHumanReader(p1){
						
										const dateObject = new Date(p1)
						
										const humanDateFormat = moment(dateObject).format('MMMM Do, h:mm a')

										//2019-12-9 10:30:15
										

										return humanDateFormat
									}
									
									var url_string = window.location.href; //window.location.href
									var url = new URL(url_string);
									
									
									var source = url.searchParams.get("source");;
									
									function toggleName(){

										if(source=="naver"){
											$("#naver_select").attr("selected", "selected");
										}else if(source=="daum"){
											$("#daum_select").attr("selected", "selected");
										}else if(source=="naverent"){
											$("#naverent_select").attr("selected", "selected");
										}else if(source=="daument"){
											$("#daument_select").attr("selected", "selected");
										}else if(source=="navercar"){
											$("#navercar_select").attr("selected", "selected");
										}else if(source=="daumcar"){
											$("#daumcar_select").attr("selected", "selected");
										}else if(source=="daumeco"){
											$("#daumeco_select").attr("selected", "selected");
										}else if(source=="daumwor"){
											$("#daumwor_select").attr("selected", "selected");
										}else if(source=="navereco"){
											$("#navereco_select").attr("selected", "selected");
										}else if(source=="naverwor"){
											$("#naverwor_select").attr("selected", "selected");
										}else if(source=="navernav"){
											$("#navernav_select").attr("selected", "selected");
										}else if(source=="naverland"){
											$("#naverland_select").attr("selected", "selected");
										}else if(source=="naversports"){
											$("#naversports_select").attr("selected", "selected");
										}else if(source=="naverit"){
											$("#naverit_select").attr("selected", "selected");
										}else if(source=="daumland"){
											$("#daumland_select").attr("selected", "selected");
										}else if(source=="daumsports"){
											$("#daumsports_select").attr("selected", "selected");
										}
									}

									toggleName();
									
									function carousel(){
										$('.carousel').carousel({
											  interval: 2000
											})
									}
									carosusel();
							</script>
</body>
<footer>

</footer>

<!--  Global site tag (gtag.js) - Google Analytics  -->
<script async src="https://www.googletagmanager.com/gtag/js?id=G-28YM45DKL9"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'G-28YM45DKL9');
</script>

</html>