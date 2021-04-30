<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<script src="${pageContext.request.contextPath}/resources/admin/plugins/jquery/jquery.min.js"></script>
<script src="https://d3js.org/d3.v3.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/admin/dist/js/pages/d3.layout.cloud.js"></script>
<head>
    <title>Word Cloud Example</title>
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
</style>
<body>

</body>

<script>

function news20Ajax(){

	var frequency_list = {};
	var source = '${source}';
	
	$.ajax({
         url: '/ccc/get/news20',
    	 method: "GET",
 	     data: {'data': source }
 	     })
	.done(function(data) {
		
		frequency_list = data;
		console.log(JSON.stringify(frequency_list));
		
		
		 var color = d3.scale.linear()
         .domain([100,20,15,10,6,5,4,3,2,1,0])
         .range(["#ddd", "#ccc", "#bbb", "#aaa", "#999", "#888", "#777", "#666", "#555", "#444", "#333", "#222"]);

 d3.layout.cloud().size([800, 300])
         .words(frequency_list)
         .rotate(0)
         .fontSize(function(d) { return d.size; })
         .on("end" , draw)
         .on("word", end)
         .start();
 function end(words) { console.log(words.text); }
 function draw(words) {
     d3.select("body").append("svg")
             .attr("width", 850)
             .attr("height", 350)
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
             .attr("href", function(d) {
                 return "${url}"+d.text;
             })
             .attr("target", function(d) {
                 return "_blank";
             })
             .text(function(d) { return  d.text ; });	
 			}
	
	})
	}
	
	news20Ajax('${source}');
	</script>


</html>