<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<script src="${pageContext.request.contextPath}/resources/admin/plugins/jquery/jquery.min.js"></script>
<script src="https://d3js.org/d3.v3.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/admin/dist/js/pages/d3.layout.cloud.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/admin/plugins/icheck-bootstrap/icheck-bootstrap.min.css">

<link rel="stylesheet" media="all" href="${pageContext.request.contextPath}/resources/admin/plugins/s3/css_d6kOGVnCcwMwcsGUTQWatc-sqvX3bgXf4Oyc17kjjoY.css" />
<link rel="stylesheet" media="all" href="${pageContext.request.contextPath}/resources/admin/plugins/s3/css_QflYzkzjtvPaB2ujG6aohkcyK8rb5ZqDwvxzqFBKEyo.css" />
<link rel="stylesheet" media="all" href="${pageContext.request.contextPath}/resources/admin/plugins/s3/css_MiU_ZvzjkHKkL53HRG1eEx0iEVLOzkJYMqQpAFHfIjE.css" />

<head>

 <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-wEmeIV1mKuiNpC+IOBjI7aAzPcEZeedi5yW5f2yOq55WWLwNGmvvx4Um1vskeMj0" crossorigin="anonymous">
    <title>Today HeadLine Top 20</title>

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

  	background-color: blue;

 	 border: none;

 	 color: white;

	 padding: 15px 30px;

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
  	
</style>
<style>
.centered { display: table; margin-left: auto; argin-right: auto;}
</style>
<body>

<div id="my_dataviz"></div>

<div class="outer">
	<div class="inner">
	<a data-gtm-event="" data-gtm-drawer="" data-gtm-interaction="" href="index6?source=naver" title="네이버뉴스" data-button-text-desktop="네이버뉴스" data-button-text-mobile="네이버뉴스" class="tds-btn tcl-button">네이버</a>
	<a data-gtm-event="" data-gtm-drawer="" data-gtm-interaction="" href="index6?source=daum" title="다음뉴스" data-button-text-desktop="다음뉴스" data-button-text-mobile="다음뉴스" class="tds-btn tcl-button">다음</a>
	</div>
	
	<div class="inner">
		<script>
				console.log(window.outerWidth);
				var frequency_list = {};
				
				var color = d3.scale.linear()
				.domain([100,20,15,10,6,5,4,3,2,1,0])
				.range(["#ddd", "#ccc", "#bbb", "#aaa", "#999", "#888", "#777", "#666", "#555", "#444", "#333", "#222"]);
// 				.range(["#ddd", "#ccc", "#540b0e", "#9e2a2b", "#e09f3e", "#fff3b0", "#335c67", "#eaac8b", "#e56b6f", "#b56576", "#6d597a", "#355070"]);
		
// 				function draw(words) {
// 			    d3.select("#my_dataviz").append("svg")
// 			            .attr("width", "100%")
// 			            .attr("height", 640)
// 			            .attr("class", "wordcloud")
// 			            .append("g")
// 			            // without the transform, words words would get cutoff to the left and top, they would
// 			            // appear outside of the SVG area
// 			            .attr("transform", "translate(320,200)")
// 			            .selectAll("text")
// 			            .data(words)
// 			            .enter().append("text")
// 			            .style("font-size", function(d) { return d.size +"px" ; })
// 			            .style("fill", function(d, i) { return color(i); })
// 			            .attr("transform", function(d) {
// 			                return "translate(" + [d.x, d.y] + ")rotate(" + d.rotate + ")";
// 			            })
// 			            .append("a")
// 			            .attr("href", function(d) {
// 			                return "${url}"+d.text;
// 			            })
// 			            .attr("target", function(d) {
// 			                return "_blank";
// 			            })
// 			            .text(function(d) { return  d.text ; });	
// 						}

					var fill = d3.scale.category20();
					
					var weight = 5,   // change me
					    width = window.innerWidth,
					    height = window.innerHeight - 150;
					
					function draw(words) {
					          d3.select("#my_dataviz").append("svg")
					              .attr("width", width)
					              .attr("height", height)
					            .append("g")
					              .attr("transform", "translate(" + width/2 + "," + height/3 + ")")
					              
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
				        .fontSize(function(d) { return d.size*weight; })
				        .on("end" , draw)
				//         .on("word", end)
				        .start();
				// function end(words) { console.log(words.text); }
						$( "#createdTime" ).text(timestampToHumanReader(frequency_list[p1].created));	
				}
				
				
				function timestampToHumanReader(p1){
		
					const dateObject = new Date(p1)
		
					const humanDateFormat = dateObject.toLocaleString() //2019-12-9 10:30:15
					
					return humanDateFormat
				}
				
				
				// 	news20Ajax('${source}');
			</script>
	</div>
	
</div>

<div class="container">

	<div class="row">
	<label for="customRange3" class="form-label"></label>
	<input type="range" class="form-range" min="0" max="9" step="1" value="9" id="customRange3" onchange="drawWordcloud(this.value)">
	</div>

	<div class="row">
	<p id="createdTime">
	</div>

      
     
</div>
 <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-p34f1UUtsS3wqzfto5wAAmdvj+osOnFyQFpp4Ua3gs/ZVWx6oOypYoCJhGGScy+8" crossorigin="anonymous"></script>
<%-- <script src="${pageContext.request.contextPath}/resources/admin/plugins/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.js"></script> --%>


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