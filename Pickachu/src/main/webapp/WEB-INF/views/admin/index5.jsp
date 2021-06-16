<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta name="description" content="현재 네이버와 다음의 뉴스중 핫한 단어모음 50개를 보여주는 사이트">
    <meta name="keywords" content="newsTOP50">
    <meta name="author" content="hw_kim">

	<script src="${pageContext.request.contextPath}/resources/admin/plugins/jquery/jquery.min.js"></script>
	<script src="https://d3js.org/d3.v3.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/admin/dist/js/pages/d3.layout.cloud.js"></script>
	<!-- Bootstrap 4 -->
	<script src="${pageContext.request.contextPath}/resources/admin/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>
	<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/locale/ko.js"></script>

	<link rel="stylesheet" media="all" href="/ccc/resources/admin/plugins/s3/css_d6kOGVnCcwMwcsGUTQWatc-sqvX3bgXf4Oyc17kjjoY.css" />
	<link rel="stylesheet" media="all" href="/ccc/resources/admin/plugins/s3/css_QflYzkzjtvPaB2ujG6aohkcyK8rb5ZqDwvxzqFBKEyo.css" />
	<link rel="stylesheet" media="all" href="/ccc/resources/admin/plugins/s3/css_MiU_ZvzjkHKkL53HRG1eEx0iEVLOzkJYMqQpAFHfIjE.css" />
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-wEmeIV1mKuiNpC+IOBjI7aAzPcEZeedi5yW5f2yOq55WWLwNGmvvx4Um1vskeMj0" crossorigin="anonymous"> 

	<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@700&display=swap" rel="stylesheet">
	
	<title>newsTOP50 by All Korean News Media</title>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-p34f1UUtsS3wqzfto5wAAmdvj+osOnFyQFpp4Ua3gs/ZVWx6oOypYoCJhGGScy+8" crossorigin="anonymous"></script>
	
	<script data-ad-client="ca-pub-1262784398485676" async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>

</head>



<style>
	body {
        font-family:"Lucida Grande","Droid Sans",Arial,Helvetica,sans-serif;
		
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
    a{text-decoration: none;
    	text-align: center;	
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

    
</style>



<body>

	<div class="container-fluid" align="center">
	
		<div class="row" align="center">
		  <div class="col-lg-8">
		  	<h1 class="display-1" style="color:#0275d8"><strong>NEWS HOT 50</strong></h1>
<!-- 		  	<h4>시간대별 가장 많이 노출된 뉴스단어 50개를 한눈에</h4> -->
		  </div>
		  <div class="col-lg-3">
		  <p>시간대별 가장 많이 노출되고 있는 뉴스 토픽 50개를 한눈에 보자.</p>
		  </div>
		  <div class="col-lg-1" align="right">ver1.6</div>
		</div>
	
		<div class="row" id="my_dataviz" align="center" >
		</div>

		
		<div class="row justify-content-md-center" >
			<div class="col-lg-2"></div>
				<div class="col-md-auto">
				<select class="form-control form-control-lg" onchange="drawWordCloud(this.value)" id="timeSelector">
				</select>
				</div>
			<div class="col-lg-2"></div>
			
			<div class="low">
					<div class="col" >
					</div>
			</div>
		</div>
		
		
		<div class="row justify-content-md-center" >
				
				<div class="btn-group" role="group">
				    <button id="btnGroupDrop1" type="button" class="btn btn-primary dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
				     카테고리 
				    </button>
				    <ul class="dropdown-menu" aria-labelledby="btnGroupDrop1">
				      <li><a href="index5?source=naver" >네이버 뉴스</a></li>
				      <li> <a href="index5?source=daum" >다음 뉴스</a></li>
				    </ul>
				</div>
				
		</div>	
		

		

		
	</div>




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
	const humanDateFormat = moment(dateObject).format('YYYY, MMMM Do  h:mm a')

	return humanDateFormat;
}

var weight = 1,   // change me
width = window.innerWidth,
height = window.innerHeight - 300;


// draw 함수 (워드 클라우드 관련 함수)
//한 줄
var color = d3.scale.linear()
//워드클라우드 크기랑 색깔 지정 
	.domain([100,20,15,10,6,5,4,3,2,1,0])
//	.domain([150,30,17.5,15,8,7,6,4,3,1,0])


	.range(["#FFCE85","#FFC579","#FFBB6D","#FFB261","#FFA855","#FF9F49","#FF953C","#FF8C30","#FF8224","#FF7918","#FF6F0C","#FF6600"]);
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