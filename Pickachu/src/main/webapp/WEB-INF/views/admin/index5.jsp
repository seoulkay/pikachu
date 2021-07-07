<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta name="description" content="한국의 실시간 주요 뉴스를 단어로 한눈에 확인">
    <meta name="keywords" content="뉴스위키">
    <meta name="author" content="hw_kim">
    <meta name="version" content="2.1">

	<script src="${pageContext.request.contextPath}/resources/admin/plugins/jquery/jquery.min.js"></script>
	<script src="https://d3js.org/d3.v3.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/admin/dist/js/pages/d3.layout.cloud.js"></script>
	<!-- Bootstrap 4 -->
	<script src="${pageContext.request.contextPath}/resources/admin/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/locale/ko.js"></script>

	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-wEmeIV1mKuiNpC+IOBjI7aAzPcEZeedi5yW5f2yOq55WWLwNGmvvx4Um1vskeMj0" crossorigin="anonymous"> 

	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@500&display=swap" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@700&display=swap" rel="stylesheet">
	
	<title>뉴스위키 - 한국 언론사에서 가장 많이 언급되고 있는 실시간 뉴스</title>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-p34f1UUtsS3wqzfto5wAAmdvj+osOnFyQFpp4Ua3gs/ZVWx6oOypYoCJhGGScy+8" crossorigin="anonymous"></script>
	
	<script data-ad-client="ca-pub-1262784398485676" async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>

</head>



<style>
	.title {
        font-family:'Noto Sans KR', sans-serif;
        font-weight: 700;
		
    }
    body {
        font-family:'Noto Sans KR', sans-serif;
        font-weight: 500;
		
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
    a{
    	text-decoration: none;
    	text-align: center;	
    }
    p{
  	 	font-size: 0.8em;

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



/* 	.carousel { */
/*   		height: 200px; */
/* 	} */

/*  	.carousel-item { */
/* 		width: auto; */
/*      	height: 120px; */
/*      	max-height: 120px; */
/*  	} */
	
	.carousel-control-prev-icon,
	.carousel-control-next-icon {
	  height: 1em;
	  width: 1em;
	  outline: none;
	  background-size: 100%, 100%;
	  border-radius: 50%;
	  background-image: none;
	}

	.carousel-control-next-icon:after {
	  content: '>';
	  font-size: 1em;
	  color: red;
	}

	.carousel-control-prev-icon:after {
	  content: '<';
	  font-size: 1em;
	  color: red;
	}
	
	.carousel-indicators {
		color: red;
	}
    
</style>



<body>

	<div class="container-fluid" align="center">
	
		<div class="row justify-content-md-center mb-3" ></div>
	
		<div class="row justify-content-md-center " >
<!-- 		  <div class="col-lg-6"> -->
		  	<a href="http://www.coldestseason.me">
		  	<h1 id="title" >
		  		<span style="color:#E85C90; font-size:1.2em;">뉴</span>
		  		<span style="color:#B491B1; font-size:1.2em;"><strong><i>스</i></strong></span>
		  		<span style="color:#8CBACB; font-size:1.2em;"><strong>위</strong></span>
		  		<span style="color:#58EFEC;">키</span>		  		

<!-- 		  		<span style="color:#E81CFF;"><strong><i>뉴</i></strong></span> -->
<!-- 		  		<span style="color:#AB5BFF;"><strong><i>뉴</i></strong></span> -->
<!-- 		  		<span style="color:#7D8AFF;"><strong><i>뉴</i></strong></span> -->
<!-- 		  		<span style="color:#40C9FF;"><strong><i>뉴</i></strong></span>		  		 -->
		  	</h1>
		  	</a>
<!-- 		  	<h4>시간대별 가장 많이 노출된 뉴스단어 50개를 한눈에</h4> -->
		  </div>
		  
		<div class="row justify-content-md-center mt-1 mb-2" >
<!-- 		  <div class="col-lg-6"> -->
		  <p>미디어에서 가장 많이 언급되고 있는 실시간 뉴스</p>
		</div>
<!-- 		  <div class="col-lg-1" align="right">ver1.8</div> -->
		
	
		<div class="row" id="my_dataviz" align="center" >
		</div>

		
		<div class="row justify-content-md-center mb-3" >

				
			<div class="col-md-auto">
				<select onchange="if(this.value) location.href=(this.value);" class="form-select form-select-sm mb-3" >
<!-- 					<option selected>뉴스 선택</option> -->
					<option value="index5?source=naver" id="naver_select">네이버 뉴스</option>		
					<option value="index5?source=daum" id="daum_select">다음 뉴스</option>					
					<option value="index5?source=naversports" id="naversports_select">네이버 스포츠</option>
					<option value="index5?source=daumsports" id="daumsports_select">다음 스포츠</option>
					<option value="index5?source=naverland" id="naverland_select">네이버 부동산뉴스</option>
				
				</select>
			</div>
			
			<div class="col-md-auto">		
				<select class="form-select form-select-sm mb-3" onchange="drawWordCloud(this.value)" id="timeSelector">
				</select>
			</div>	
			
		</div>
		
<!-- 		<div class="row justify-content-md-center mb-3" > -->
		<div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
			  
			  <ul class="carousel-indicators">
			    <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
			    <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
			    <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
			  </ul>
			  
			  <div class="carousel-inner">
			    <div class="carousel-item active">
			      	<a href="http://kimsouvenir.cafe24.com/surl/O/11" target="_blank">
			      		<img class="d-block w-100" src="https://images.pexels.com/photos/213399/pexels-photo-213399.jpeg?auto=compress&cs=tinysrgb&h=650&w=940" alt="광고이미지 1" height="110">
			    	</a>
			    </div>
			    <div class="carousel-item">
			    	<a href="http://kimsouvenir.cafe24.com/surl/O/11" target="_blank">
			      		<img class="d-block w-100" src="https://images.pexels.com/photos/2355519/pexels-photo-2355519.jpeg?auto=compress&cs=tinysrgb&dpr=3&h=750&w=1260" alt="광고이미지 2" height="110">
			    	</a>
			    </div>
			    <div class="carousel-item">
			    	<a href="http://kimsouvenir.cafe24.com/surl/O/11" target="_blank">
			      		<img class="d-block w-100" src="https://images.pexels.com/photos/2544554/pexels-photo-2544554.jpeg?auto=compress&cs=tinysrgb&h=650&w=940" alt="광고이미지 3" height="110">
			    	</a>
			    </div>
			  </div>
			  
			  <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
			    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
<!-- 			    <span class="sr-only">Previous</span> -->
			  </a>
			  <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
			    <span class="carousel-control-next-icon" aria-hidden="true"></span>
<!-- 			    <span class="sr-only">Next</span> -->
			  </a>
		</div>
<!-- 		</div> -->
		
<!-- 		<div class="row justify-content-md-center" > -->
<!-- 			<div class="col-md-auto"  style="font-size:0.5em">		 -->
<!-- 				<p><small>뉴스위키 사용법</small></p> -->
<!-- 			</div>	 -->
				
<!-- 			<div class="col-md-auto" style="font-size:0.5em"> -->
<!-- 				<p><small>Copyright 2021. 뉴스위키 All Rights Reserved.</small></p> -->
<!-- 			</div> -->
			
<!-- 		</div> -->
		
		

		




		

		
	</div>




</body>

<script>
//var source = "${source}";

var urlString = window.location.href;
var url = new URL(urlString);
var c = url.searchParams.get("source");
console.log(c);

var source = c;

var frequency_list = {};

$('.carousel').carousel({
	  interval: 5000
	})

function toggleName(){
	if(source=="naver"){
		$("#naver_select").attr("selected", "selected");
	}else if(source=="daum"){
		$("#daum_select").attr("selected", "selected");
		
	}else if(source=="naversports"){
		$("#naversports_select").attr("selected", "selected");
		
	}else if(source=="daumsports"){
		$("#daumsports_select").attr("selected", "selected");
		
	}else if(source=="naverland"){
		$("#naverland_select").attr("selected", "selected");
	}
	
}

toggleName();




function toggleUrl(){
	var daum1 = "daum";
	var naver1 = "naver";
	
	if(source=="naver"){
		return daum1;
	}else{
		return naver1;
	}
}


function timeStampToHuman(p1){
	const dateObject = new Date(p1);
	const humanDateFormat = moment(dateObject).format('MMMM Do  a h:mm')

	return humanDateFormat;
}

var weight = 2.5,   // change me
width = window.innerWidth - 21,
height = window.innerHeight - 350;


// draw 함수 (워드 클라우드 관련 함수)
//한 줄
var color = d3.scale.linear()
//워드클라우드 크기랑 색깔 지정 
	.domain([100,20,15,10,6,5,4,3,2,1,0])
//	.domain([150,30,17.5,15,8,7,6,4,3,1,0])

	//.range(["#40C9FF","#4FB9FF","#5FAAFF","#6E9AFF","#7D8AFF","#8C7AFF","#9C6BFF","#AB5BFF","#BA4BFF","#C93BFF","#D92CFF","#E81CFF"]);
	.range(["#58EFEC","#65E2E4","#72D4DB","#7FC7D3","#8CBACB","#99ACC2","#A79FBA","#B491B1","#C184A9","#CE77A1","#DB6998","#E85C90"]);
	//.range(["#C3B196","#B2A289","#A1937D","#918370","#807463","#6F6556","#5E564A","#4D473D","#3C3830","#2C2823","#1B1917","#0A0A0A"]);
	//.range(["#FFD324","#E9C122","#D2AE1F","#BC9C1D","#A68A1B","#907818","#796516","#FFC800","#C29903","#856905","#473A08","#0A0A0A"]);
	//.range(["#FFCE85","#FFC579","#FFBB6D","#FFB261","#FFA855","#FF9F49","#FF953C","#FF8C30","#FF8224","#FF7918","#FF6F0C","#FF6600"]);
	//.range(["#96F2FA", "#8DDAFB", "#86C6FB", "#7DB0FB", "#759AFC", "#6D84FC", "#656EFD", "#5D58FD", "#5542FE", "#4C2CFE", "#4416FF", "#3C00FF"]);
//	.range(["#ddd", "#ccc", "#bbb", "#aaa", "#999", "#888", "#777", "#666", "#555", "#444", "#333", "#222"]);
//한 줄 끝

function draw(words) {
    d3.select("#my_dataviz").append("svg")
            .attr("width", width)
            .attr("height", height)
            //.attr("viewbox", "0 0 100 100")

            .append("g")
            // without the transform, words words would get cutoff to the left and top, they would
            // appear outside of the SVG area
            .attr("transform", "translate(" + width/2 + "," + height/2 + ")")
            
            //.attr("transform", "scale(2, 2)")
            
            .selectAll("text")
            .data(words)
            .enter().append("text")
            .style("font-size", function(d) { return d.size  + "px"; })
            .style("fill", function(d, i) { return color(i); })
            //.style("padding", 3 )
            .style("font-family", "Noto Sans KR")
            .style("font-weight", "500")
            .attr("text-anchor", "middle")
            .attr("transform", function(d) {
                return "translate(" + [d.x, d.y] + ")rotate(" + d.rotate + ")";
            })
            .append("a")
            //a태그를 붙임
            
            .attr("href", function(d) {
                return "${url}"+d.text;
            })
            //href 속성 집어넣고 주소 붙인다. 링크 생성 
            .attr("text-decoration", "none")
            .attr("target", function(d) {
                return "_blank";
            })
            //타겟 _blank -> 새 탭에서 열기 
            
            .text(function(d) { return  d.text ; });	                       
}


//ajax 함수 
function news20Ajax(p1){

	var source = p1;
	// 	소스는 다음/네이버 
	
	$.ajax({
       url: '/ccc/get/news20AjaxX10',
  	 method: "GET",
	     data: {'data': source }
		// 소스를 데이터에 넣은 상태로 보낸다. 파라미터 설정 
	     })
	     
	 //받아오면...
	 //아래 데이터는 받아오는 자료를 의미.
	.done(function(data) {
		
		//받아온 데이터를 프리퀀시 리스트에 집어넣는다. 
		frequency_list = data;
		//받아온 데이터를 스트링으로 찍어본다. 
		console.log(JSON.stringify(frequency_list));
		
// 		for(int i=0; i<frequency_list.length; i++){
			
// 			for(int j=0;j<frequency_list[j].top20.length;j++){
				
// 			}
// 		}
		
		drawWordCloud(9);

		//타임 셀렉터에 옵션을 자동으로 추가. 
// 		$("#timeSelector").append(" <option selected>시간을 선택</option>");
		
		//
		//
		console.log(frequency_list.length);
		//frequency_list 만큼 반복해서 append
		for(var i=frequency_list.length-1; i>-1; i--){
			$("#timeSelector").append(' <option value='+i+'>'+timeStampToHuman(frequency_list[i].created)+'</option>');
		}
		
	})
	}


news20Ajax('${source}');
	


function drawWordCloud(p1){

	$( "#my_dataviz" ).empty();
	
	console.log(frequency_list[p1].top20[0].size);
	
	d3.layout.cloud()
 	.size([width, height])
	.words(frequency_list[p1].top20)
	.rotate(0)
	.padding(1)
	.fontSize(function(d) { return d.size * weight ;	})         
	.on("end" , draw)
	.start();

// 	$("#collectedTime").text(timeStampToHuman(frequency_list[p1].created));
}





//	.rotate(d => ~~(Math.random() * 2) * 90)









</script>

<!-- Global site tag (gtag.js) - Google Analytics -->
<script async src="https://www.googletagmanager.com/gtag/js?id=G-T7WCS87HSP"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'G-T7WCS87HSP');
</script>


</html>