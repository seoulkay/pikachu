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
	<script src="https://d3js.org/d3.v3.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/admin/dist/js/pages/d3.layout.cloud.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/admin/plugins/icheck-bootstrap/icheck-bootstrap.min.css">
	<link rel="stylesheet" media="all" href="${pageContext.request.contextPath}/resources/admin/plugins/s3/css_d6kOGVnCcwMwcsGUTQWatc-sqvX3bgXf4Oyc17kjjoY.css" />
	<link rel="stylesheet" media="all" href="${pageContext.request.contextPath}/resources/admin/plugins/s3/css_QflYzkzjtvPaB2ujG6aohkcyK8rb5ZqDwvxzqFBKEyo.css" />
	<link rel="stylesheet" media="all" href="${pageContext.request.contextPath}/resources/admin/plugins/s3/css_MiU_ZvzjkHKkL53HRG1eEx0iEVLOzkJYMqQpAFHfIjE.css" />

 <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-wEmeIV1mKuiNpC+IOBjI7aAzPcEZeedi5yW5f2yOq55WWLwNGmvvx4Um1vskeMj0" crossorigin="anonymous">
 <title>지금뉴스</title>
	<script data-ad-client="ca-pub-2029088903535235" async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
	
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
</head>

<body>
				<div class="container-fluid">
				<div class="row" >
				<div class="col-sm-1"></div>
				<div class="col-sm-10">
				<h3>지금뉴스</h3>
				<p> 시간대별 가장많이 노출된 뉴스단어 Top50 </p>
				</div>
				<div class="col-sm-1"></div>
				</div>
				</div>
				
				
					<div class="row" >
						<div class="col-md-auto" id="my_dataviz"></div>
						
					</div>

				<div class="row justify-content-md-center" >
				
<!-- 				<div class="col-md-auto" align="center"> -->
<!-- 				<a data-gtm-event="" data-gtm-drawer="" data-gtm-interaction="" href="index6?source=naver" title="네이버뉴스" data-button-text-desktop="네이버뉴스" data-button-text-mobile="네이버뉴스" class="tds-btn tcl-button" >네이버 뉴스</a> -->
<!-- 				<a data-gtm-event="" data-gtm-drawer="" data-gtm-interaction="" href="index6?source=daum" title="다음뉴스" data-button-text-desktop="다음뉴스" data-button-text-mobile="다음뉴스" class="tds-btn tcl-button" >다음 뉴스</a> -->
<!-- 				</div> -->
				
				
				    <div class="btn-group" role="group">
				    <button id="btnGroupDrop1" type="button" class="btn btn-primary dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
				      분야별 
				    </button>
				    <ul class="dropdown-menu" aria-labelledby="btnGroupDrop1">
				      <li><a href="index6?source=naver" >네이버 뉴스</a></li>
				      <li> <a href="index6?source=daum" >다음 뉴스</a></li>
				    </ul>
				  </div>
				  
				
				
				<div class="low">
				<div class="col" >
				
				<script>
						console.log(window.outerWidth);
						var frequency_list = {};
						
						var color = d3.scale.linear()
						.domain([100,20,15,10,6,5,4,3,2,1,0])
						.range(["#ddd", "#ccc", "#bbb", "#aaa", "#999", "#888", "#777", "#666", "#555", "#444", "#333", "#222"]);

							var fill = d3.scale.category20();
							
							var weight = 1, //글자크기정하기 
							    width = window.innerWidth,
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
							           .style("font-family", "Impact")
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
				
							function news20Ajax(p1){
							
							var source = p1;
									
								$.ajax({
						         url: '/ccc/get/news202',
						    	 method: "GET",
						 	     data: {'data': source }
						 	     })
								.done(function(data) {
								
								frequency_list = data;
								drawWordcloud(9);
								})
							}
							news20Ajax('${source}');
							
							function drawWordcloud(p1){
								$( "#my_dataviz" ).empty();
								d3.layout.cloud().size([width,height])
						        .words(frequency_list[p1].top20)
						        .rotate(0)
						        .padding(1)
						        .fontSize(function(d) { return d.size * weight; })
						        .on("end" , draw)
						        .start();
								$( "#createdTime" ).text(timestampToHumanReader(frequency_list[p1].created));	
								

							}
						
						
							function timestampToHumanReader(p1){
				
								const dateObject = new Date(p1)
				
								const humanDateFormat = dateObject.toLocaleString() //2019-12-9 10:30:15
								
								return humanDateFormat
							}

					</script>
				</div>
				</div>
			</div>	
		
<!--      	 <div class="row"> -->
<!--      		 <div class="col-sm-1"> -->
<!--      		 </div>	 -->
<!--      	      	 <div class="col-sm-10">  	      	  -->
<!-- 				<label for="customRange3" class="form-label"></label> -->
<!-- 				<input type="range" class="form-range" min="0" max="9" step="1" value="9" id="customRange3" onchange="drawWordcloud(this.value)"> -->
<!-- 				</div> -->
<!-- 					<div class="col-sm-1"> -->
<!-- 					</div> -->
<!-- 		</div> -->
		<div class="row">
		
		<p align="center" id="createdTime">
		
		</div>
     	
     	<div class="row justify-content-md-center" >
				<div class="col-lg-2"></div>
				<div class="col-md-auto">
				<select class="form-control form-control-lg" onchange="drawWordcloud(this.value)">
 				 <option selected>시간을 선택해 주세요</option>
				 <option value="9" >지금</option>
				 <option value="8" >30분전 </option>
				 <option value="7" >1시간전</option>
				 <option value="6" >1시간30분전</option>
				 <option value="5" >2시간전</option>
				 <option value="4" >2시간30분전</option>
				 <option value="3" >3시간전</option>
				 <option value="2" >3시간30분전</option>
				 <option value="1" >4시간전</option>
				 <option value="0" >4시간30분전</option>
				 </select>
				 </div>
				<div class="col-lg-2"></div>
				</div>
	
 <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-p34f1UUtsS3wqzfto5wAAmdvj+osOnFyQFpp4Ua3gs/ZVWx6oOypYoCJhGGScy+8" crossorigin="anonymous"></script>

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