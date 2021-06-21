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
						<div class="col-md-auto">
						<h3><a style="font-family:'Noto Serif KR'" href="index6?source=naver">지금뉴스</a></h3>
						<small> 시간대별 가장많이 노출된 뉴스단어 Top50 </small>
						</div>
					
					</div>
				
					<p>
				
					<div class="row" >
						<div class="col" id="my_dataviz"></div>
						
					</div>

				<div class="row justify-content-md-center" >
						
						<div class="col-md-auto">
							<select onchange="if(this.value) location.href=(this.value);" class="form-select form-select-lg mb-3" aria-label=".form-select-lg example">
							 <option selected>뉴스선택</option>
							  <option value="index6?source=naver">네이버 뉴스</option>
							  <option value="index6?source=daum">다음 뉴스</option>
							</select>
							<select class="form-select form-select-lg mb-3" aria-label=".form-select-lg example" onchange="drawWordcloud(this.value)" id="timeSelector">
						</select>
						 </div>
						
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
								        .fontSize(function(d) { return d.size * weight; })
								        .on("end" , draw)
								        .start();
							
									}
								
								
									function timestampToHumanReader(p1){
						
										const dateObject = new Date(p1)
						
										const humanDateFormat = moment(dateObject).format('MMMM Do, h:mm a')

										//2019-12-9 10:30:15
										

										return humanDateFormat
									}
		
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