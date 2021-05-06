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
 <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-wEmeIV1mKuiNpC+IOBjI7aAzPcEZeedi5yW5f2yOq55WWLwNGmvvx4Um1vskeMj0" crossorigin="anonymous">
 <title>NEWS_CLOUD</title>
</head>

<style>
    body {
        font-family:"Lucida Grande","Droid Sans",Arial,Helvetica,sans-serif;
    }
    a{text-decoration:none}
    
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
</style>

<body>

<div class="container">

  <div class="row">
    <div class="col">
    	<a class="btn btn-primary btn-sm" href="index5?source=naver" role="button" >NAVER</a>
    </div>
    <div class="col">
    	<a class="btn btn-primary btn-sm" href="index5?source=daum" role="button">DAUM</a>
    </div>
  </div>


  <div class="row">
   	<div>
		<p id="collectedTime"></p>
	</div>
	<label for="customRange3" class="form-label"></label>
	<input type="range" class="form-range" value="9" onChange="drawWordCloud(this.value)" min="0" max="9" step="1" id="customRange3">
  </div>
  

  
</div>




</body>

<script>

function timeStampToHuman(p1){
	const dateObject = new Date(p1);
	const humanDateFormat = dateObject.toLocaleString();
	return humanDateFormat;
}

// draw 함수 (워드 클라우드 관련 함수)
//한 줄
var color = d3.scale.linear()
//워드클라우드 크기랑 색깔 지정 
	.domain([100,20,15,10,6,5,4,3,2,1,0])
	.range(["#ddd", "#ccc", "#bbb", "#aaa", "#999", "#888", "#777", "#666", "#555", "#444", "#333", "#222"]);
//한 줄 끝

function draw(words) {
    d3.select("body").append("svg")
            .attr("width", "100%")
            .attr("height", 700)
            .attr("class", "wordcloud")
            .append("g")
            // without the transform, words words would get cutoff to the left and top, they would
            // appear outside of the SVG area
            .attr("transform", "translate(320,200)")
            .selectAll("text")
            .data(words)
            .enter().append("text")
            .style("font-size", function(d) { return d.size + "px"; })
            .style("fill", function(d, i) { return color(i); })
            .attr("transform", function(d) {
                return "translate(" + [d.x, d.y] + ")rotate(" + d.rotate + ")";
            })
            .append("a")
            //a태그를 붙임
            
            .attr("href", function(d) {
                return "${url}"+d.text;
            })
            //href 속성 집어넣고 주소 붙인다. 링크 생성 
            
            .attr("target", function(d) {
                return "_blank";
            })
            //타겟 _blank -> 새 탭에서 열기 
            
            .text(function(d) { return  d.text ; });	
            
            
}
function drawWordCloud(p1){
	$("#collectedTime").text(timeStampToHuman(frequency_list[p1].created));
	
	$( ".wordcloud" ).remove();
	
	//한 줄 
	d3.layout.cloud().size([800, 300])
	
	.words(frequency_list[p1].top20)
	//원하는 자료를 p1에 넣은 상태라면 그릴 거다.  
	
	.rotate(0)
	.fontSize(function(d) { return d.size; })         
	.on("end" , draw)
	// 다 끝났을 때 -> 78행 드라우 함수 실행  
	        
	.start();
	//한 줄 끝..
}

//원하는 결과를 미리 선언. 중간 함수 안에서 선언하면 헷갈린다.   
var frequency_list = {};

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
		
		drawWordCloud(9);

	})
	}


news20Ajax('${source}');


	
</script>


</html>