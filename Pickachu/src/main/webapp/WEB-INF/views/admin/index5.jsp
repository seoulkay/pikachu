<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<script src="${pageContext.request.contextPath}/resources/admin/plugins/jquery/jquery.min.js"></script>
<script src="https://d3js.org/d3.v3.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/admin/dist/js/pages/d3.layout.cloud.js"></script>
<!-- Bootstrap 4 -->
<script src="${pageContext.request.contextPath}/resources/admin/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>


<head>
<meta name="keywords" content="뉴스, 검색어, 클라우드, 순위, 뉴스클라우드, 실시간뉴스, 방탄소년단, BTS">
<meta name="description" content="네이버와 다음에 게시된 뉴스의 헤드라인을 분석해서 가장 많이 언급된 단어들의 클라우드를 보여줍니다.">
<meta name="author" content="애국청년">
<meta name="application-name" content="핫눈에 보는 핫뉴스">

 <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-wEmeIV1mKuiNpC+IOBjI7aAzPcEZeedi5yW5f2yOq55WWLwNGmvvx4Um1vskeMj0" crossorigin="anonymous">
 <link rel="canonical" href="https://getbootstrap.com/docs/4.4/examples/cover/">
 
 <title>뉴스, 한눈에 보자</title>

<script data-ad-client="ca-pub-1262784398485676" async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>

</head>
<!-- <meta name="theme-color" content="#7952b3"> -->
<meta name="theme-color" content="#563d7c">


<style>
/*     body {
/*         font-family:"Lucida Grande","Droid Sans",Arial,Helvetica,sans-serif; */
/*     } */
    
    a{text-decoration:none;}
	
	h4{text-align:center;}
    
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
    
    .svg{ 
   		position:relative; 
    	width:100%; 
      	height:2000px;  	
	} 
    .outer {
  	text-align: center;
  	}
  	
  	.inner {
  	 display: inline-block;
  	}
	
	
	      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }
    
    .slidecontainer {
  width: 100%; /* Width of the outside container */
}

/* The slider itself */
.slider {
  -webkit-appearance: none;  /* Override default CSS styles */
  appearance: none;
  width: 100%; /* Full-width */
  height: 100px; /* Specified height */
  background: #ededed; /* Grey background */
  outline: none; /* Remove outline */
  opacity: 0.7; /* Set transparency (for mouse-over effects on hover) */
  -webkit-transition: .2s; /* 0.2 seconds transition on hover */
  transition: opacity .2s;
}

/* Mouse-over effects */
.slider:hover {
  opacity: 1; /* Fully shown on mouse-over */
}

/* The slider handle (use -webkit- (Chrome, Opera, Safari, Edge) and -moz- (Firefox) to override default look) */
.slider::-webkit-slider-thumb {
  -webkit-appearance: none; /* Override default look */
  appearance: none;
  width: 100px; /* Set a specific slider handle width */
  height: 100px; /* Slider handle height */
  background: #3C00FF; /* Green background */
  cursor: pointer; /* Cursor on hover */
}

.slider::-moz-range-thumb {
  width: 70px; /* Set a specific slider handle width */
  height: 70px; /* Slider handle height */
  background: #3C00FF; /* Green background */
  cursor: pointer; /* Cursor on hover */
}
    
</style>
 <!-- Custom styles for this template -->
 <link href="cover.css" rel="stylesheet">
    
<link href="https://fonts.googleapis.com/css?family=Playfair+Display:700,900" rel="stylesheet">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@700&display=swap" rel="stylesheet">

<body class="text-center">
    <div class="cover-container d-flex w-100 h-70 p-3 mx-auto flex-column">
 		 <header class="masthead mb-auto">
   		 <div class="inner">
   		   <h1 class="masthead-brand">NEWS NOW</h1>
     	 <nav class="nav nav-masthead justify-content-center">
      		<a class="nav-link active" href="index5?source=naver">네이버</a>
     	   	<a class="nav-link" href="index5?source=daum">다음</a>
<!--         <a class="nav-link" href="#">Contact</a> -->
      </nav>
    </div>
  </header>

  <main role="main" class="inner cover">
<!--     <h1 class="cover-heading">Cover your page.</h1> -->
<!--     <p class="lead">Cover is a one-page template for building simple and beautiful home pages. Download, edit the text, and add your own fullscreen background photo to make it your own.</p> -->
<!--     <p class="lead"> -->
<!--       <a href="#" class="btn btn-lg btn-secondary">Learn more</a> -->
<!--     </p> -->
  </main>


</div>






<!-- 	<nav class="navbar fixed-top navbar-light bg-light"> -->
<!-- 	  <div class="container-fluid"> -->
<!-- 	    <h2 style="color:black"> -->
<%-- 		<a href="#" onclick='clickPortalLabel(); return false;' style="text-decoration:none; color:#3C00FF">${label}</a> 뉴스 헤드라인 중 가장 많이 언급된 50개의 단어입니다. --%>
<!-- 		</h2> -->
	
<!-- 	  </div> -->
	
<!-- 	</nav> -->


<!--   <div class="row"> -->
<!--     <div class="col" align="right"> -->
<!--     	<a class="btn btn-primary btn-sm" href="index5?source=toggleUrl()" role="button" style="text-decoration:none" >toggleName()</a> -->
<!--     	 온클릭으로 바꿔야함. -->
<!--     	 함수안에서  온클릭 해주는 내용, href 해주는 내용 다 집어넣야함. 
<!--     			버튼 이름 바꾸는 건 버튼에게 아이디 줘서 그 친구 text를 바꿔라.  --> 
    	
<!-- <!--    <a class="btn btn-primary btn-sm" href="index5?source=daum" role="button">다음</a> --> 
<!--     </div> -->
<!--   </div> -->

<!-- 	<div class="container"> -->

	<div class="container-fluid" id="my_dataviz" align="center">
	</div>

<!-- 	</div> -->



   
<nav class="navbar fixed-bottom navbar-light bg-none">	
<!--   <div class="row" > 	style="width:900px" -->
<!--     <div class="cloud"  align="center"> -->
<!--     <div align="center"> -->
<!-- 	<div> -->
<!-- 	<label for="customRange3" class="form-label" ></label> -->
<!-- 	<input type="range" class="form-range" value="9" onChange="drawWordCloud(this.value)" min="0" max="9" step="1" id="customRange3"> -->

<!-- 	<div class="slidecontainer"> -->
	<label for="customRange3" class="slidecontainer" ></label>
 		<input type="range" min="1" max="9" step="1" value="9" class="slider" id="customRange3" onChange="drawWordCloud(this.value)">
<!-- 	</div> -->
		<p id="collectedTime"></p>


<!-- 	</div> -->
<!-- 		  </div> -->

<!-- 		 <div class="row" > -->
<!-- 	<div align="center"> -->


<!-- <div class="cover-container d-flex w-100 h-70 p-3 mx-auto flex-column"> -->
<!--   <footer class="mastfoot mt-auto"> -->
<!--     <div class="inner"> -->
<!--       <p id="collectedTime"></p> -->
<!--     </div> -->
<!--   </footer> -->
<!-- </div> -->



<!-- 		<h3 id="collectedTime" style="color:#3C00FF; text-align:right"></h3> -->
<!-- 	</div> -->

</nav>	


  



</body>

<script>
var source = "${source}";
var frequency_list = {};

function toggleName(){
	var daum = "다음";
	var naver = "네이버";
	
	if(source=="naver"){
		return daum;
	}else{
		return naver;
	}
}

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
	const humanDateFormat = dateObject.toLocaleString();
	return humanDateFormat;
}

var weight = 3,   // change me
width = window.innerWidth,
height = window.innerHeight - 150;


// draw 함수 (워드 클라우드 관련 함수)
//한 줄
var color = d3.scale.linear()
//워드클라우드 크기랑 색깔 지정 
	.domain([100,20,15,10,6,5,4,3,2,1,0])
//	.domain([150,30,17.5,15,8,7,6,4,3,1,0])
	.range(["#96F2FA", "#8DDAFB", "#86C6FB", "#7DB0FB", "#759AFC", "#6D84FC", "#656EFD", "#5D58FD", "#5542FE", "#4C2CFE", "#4416FF", "#3C00FF"]);
//	.range(["#ddd", "#ccc", "#bbb", "#aaa", "#999", "#888", "#777", "#666", "#555", "#444", "#333", "#222"]);
//한 줄 끝

function draw(words) {
    d3.select("#my_dataviz").append("svg")
            .attr("width", width)
            .attr("height", height)
            .attr("viewbox", "0 0 100 100")
            //.attr("width", drawWordCloud().size[0])
     		//.attr("height", drawWordCloud().size[1])
     		
            //.attr("class", "wordcloud")
            .append("g")
            // without the transform, words words would get cutoff to the left and top, they would
            // appear outside of the SVG area
            .attr("transform", "translate(" + width/2 + "," + height/2.5 + ")")
            
            //.attr("transform", "scale(2, 2)")
            
            .selectAll("text")
            .data(words)
            .enter().append("text")
            .style("font-size", function(d) { return d.size + "px"; })
            .style("fill", function(d, i) { return color(i); })
            //.style("padding", 3 )
            .style("font-family", "Noto Sans KR")
            .style("font-weight", "700")
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
		//console.log(JSON.stringify(frequency_list));
		
// 		for(int i=0; i<frequency_list.length; i++){
			
// 			for(int j=0;j<frequency_list[j].top20.length;j++){
				
// 			}
// 		}
		
		drawWordCloud(9);

		
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
	.fontSize(function(d) { return d.size ;	})         
	.on("end" , draw)
	.start();

	$("#collectedTime").text(timeStampToHuman(frequency_list[p1].created));
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