/*
 * Author: Abdullah A Almsaeed
 * Date: 4 Jan 2014
 * Description:
 *      This is a demo file used only for the main dashboard (index.html)
 **/

var loginData = [0,0,0,0,0,0,0];
var countryData= [0,0];
var visitorsData= {};
	


function loginChartDataAjax(){
	
	var data1 = 1;
	
	$.ajax({
         url: 'loginCountAjax',
    	 method: "GET",
 	     data: {'data': data1}
 	     })
	.done(function(data) {
		
		loginData = data;
		console.log(JSON.stringify(loginData));
		
		$.ajax({
	         url: 'countCountryCodeAjax',
	    	 method: "GET",
	 	     data: {'data': data1}
	 	     })
		.done(function(data) {
			countryData = data

			for(var i=0; i<Object.keys(countryData).length; i++){
				var keyname = '';
				visitorsData[keyname + data[i].countryCode] = data[i].countryCodeTotal;			
			}
			
			console.log(JSON.stringify(countryData));
			console.log(JSON.stringify(visitorsData));
$(function () {

  'use strict'
	
  // Make the dashboard widgets sortable Using jquery UI
  $('.connectedSortable').sortable({
    placeholder         : 'sort-highlight',
    connectWith         : '.connectedSortable',
    handle              : '.card-header, .nav-tabs',
    forcePlaceholderSize: true,
    zIndex              : 999999
  })
  $('.connectedSortable .card-header, .connectedSortable .nav-tabs-custom').css('cursor', 'move')

  // jQuery UI sortable for the todo list
  $('.todo-list').sortable({
    placeholder         : 'sort-highlight',
    handle              : '.handle',
    forcePlaceholderSize: true,
    zIndex              : 999999
  })

  // bootstrap WYSIHTML5 - text editor
  $('.textarea').summernote()

  $('.daterange').daterangepicker({
    ranges   : {
      'Today'       : [moment(), moment()],
      'Yesterday'   : [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
      'Last 7 Days' : [moment().subtract(6, 'days'), moment()],
      'Last 30 Days': [moment().subtract(29, 'days'), moment()],
      'This Month'  : [moment().startOf('month'), moment().endOf('month')],
      'Last Month'  : [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
    },
    startDate: moment().subtract(29, 'days'),
    endDate  : moment()
  }, function (start, end) {
    window.alert('You chose: ' + start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'))
  })

  /* jQueryKnob */
  $('.knob').knob()

  // jvectormap data
  var tt= {
    'us': 5000, //USA
    'sa': 200, //Saudi Arabia
    'ca': 300.30, //Canada
    'de': 300, //Germany
    'fr': 1000, //France
    'cn': 1000, //China
    'au': 1, //Australia
    'br': 1, //Brazil
    'in': 1, //India
    'gb': 1, //Great Britain
    'ru': 1 //Russia
  }
  // World map by jvectormap
  $('#world-map').vectorMap({
	
	map: 'world_en',
          backgroundColor: '#333333',
          color: '#ffffff',
          hoverOpacity: 0.7,
          selectedColor: '#666666',
          enableZoom: true,
          showTooltip: true,
          scaleColors: ['#C8EEFF', '#006491'],
          values: visitorsData,
          normalizeFunction: 'polynomial'
});
//    map              : 'world_en',
//    backgroundColor  : 'transparent',
//    regionStyle      : {
//      initial: {
//        fill            : 'rgba(255, 255, 255, 0.7)',
//        'fill-opacity'  : 1,
//        stroke          : 'rgba(0,0,0,.2)',
//        'stroke-width'  : 1,
//        'stroke-opacity': 1
//      }
//    },
//    series           : {
//      regions: [{
//        values           : visitorsData,
//        scale            : ['#C8EEFF', '#0071A4'],
//        normalizeFunction: 'polynomial'
//      }]
//    },
//    onRegionLabelShow: function (e, el, code) {
//      if (typeof visitorsData[code] != 'undefined')
//        el.html(el.html() + ': ' + visitorsData[code] + ' new visitors')
//    }
//  })

  // Sparkline charts
  var sparkline1 = new Sparkline($("#sparkline-1")[0], {width: 80, height: 50, lineColor: '#92c1dc', endColor: '#ebf4f9'});
  var sparkline2 = new Sparkline($("#sparkline-2")[0], {width: 80, height: 50, lineColor: '#92c1dc', endColor: '#ebf4f9'});
  var sparkline3 = new Sparkline($("#sparkline-3")[0], {width: 80, height: 50, lineColor: '#92c1dc', endColor: '#ebf4f9'});

  sparkline1.draw([1000, 1200, 920, 927, 931, 1027, 819, 930, 1021]);
  sparkline2.draw([515, 519, 520, 522, 652, 810, 370, 627, 319, 630, 921]);
  sparkline3.draw([15, 19, 20, 22, 33, 27, 31, 27, 19, 30, 21]);

  // The Calender
  $('#calendar').datetimepicker({
    format: 'L',
    inline: true
  })
	
  // SLIMSCROLL FOR CHAT WIDGET
  $('#chat-box').overlayScrollbars({
    height: '250px'
  })

  /* Chart.js Charts */
  // Sales chart
  var salesChartCanvas = document.getElementById('revenue-chart-canvas').getContext('2d');
  //$('#revenue-chart').get(0).getContext('2d');

  

  var salesChartData = {
    labels  : [ loginData[6].month+'-'+loginData[6].day,
				loginData[5].month+'-'+loginData[5].day,
				loginData[4].month+'-'+loginData[4].day,
				loginData[3].month+'-'+loginData[3].day,
				loginData[2].month+'-'+loginData[2].day,
				loginData[1].month+'-'+loginData[1].day,
			    loginData[0].month+'-'+loginData[0].day],
    datasets: [
      {
        label               : '로그인성공횟수',
        backgroundColor     : 'rgba(60,141,188,0.9)',
        borderColor         : 'rgba(60,141,188,0.8)',
        pointRadius          : false,
        pointColor          : '#3b8bba',
        pointStrokeColor    : 'rgba(60,141,188,1)',
        pointHighlightFill  : '#fff',
        pointHighlightStroke: 'rgba(60,141,188,1)',
        data                : [loginData[6].successTotal, 
							   loginData[5].successTotal,	
							   loginData[4].successTotal, 
							   loginData[3].successTotal, 
				               loginData[2].successTotal, 
							   loginData[1].successTotal, 
							   loginData[0].successTotal]
      },
      {
        label               : '로그인전체횟수',
        backgroundColor     : 'rgba(210, 214, 222, 1)',
        borderColor         : 'rgba(210, 214, 222, 1)',
        pointRadius         : false,
        pointColor          : 'rgba(210, 214, 222, 1)',
        pointStrokeColor    : '#c1c7d1',
        pointHighlightFill  : '#fff',
        pointHighlightStroke: 'rgba(220,220,220,1)',
        data                : [loginData[6].total, 
							   loginData[5].total,	
							   loginData[4].total, 
							   loginData[3].total, 
				               loginData[2].total, 
							   loginData[1].total, 
							   loginData[0].total]
      },
    ]
  }
  console.log(JSON.stringify(salesChartData));
  var salesChartOptions = {
    maintainAspectRatio : false,
    responsive : true,
    legend: {
      display: false
    },
    scales: {
      xAxes: [{
        gridLines : {
          display : false,
        }
      }],
      yAxes: [{
        gridLines : {
          display : false,
        }
      }]
    }
  }

  // This will get the first returned node in the jQuery collection.
  var salesChart = new Chart(salesChartCanvas, { 
      type: 'line', 
      data: salesChartData, 
      options: salesChartOptions
    }
  )

  // Donut Chart
  var pieChartCanvas = $('#sales-chart-canvas').get(0).getContext('2d')
  var pieData        = {
    labels: [
        '성공하지못한', 
        '성공한자', 
    ],
    datasets: [
      {
        data: [30,20],
        backgroundColor : ['#f56954', '#00a65a'],
      }
    ]
  }
  var pieOptions = {
    legend: {
      display: false
    },
    maintainAspectRatio : false,
    responsive : true,
  }
  //Create pie or douhnut chart
  // You can switch between pie and douhnut using the method below.
  var pieChart = new Chart(pieChartCanvas, {
    type: 'doughnut',
    data: pieData,
    options: pieOptions      
  });

  // Sales graph chart
  var salesGraphChartCanvas = $('#line-chart').get(0).getContext('2d');
  //$('#revenue-chart').get(0).getContext('2d');

  var salesGraphChartData = {
    labels  : ['2011 Q1', '2011 Q2', '2011 Q3', '2011 Q4', '2012 Q1', '2012 Q2', '2012 Q3', '2012 Q4', '2013 Q1', '2013 Q2'],
    datasets: [
      {
        label               : 'Digital Goods',
        fill                : false,
        borderWidth         : 2,
        lineTension         : 0,
        spanGaps : true,
        borderColor         : '#efefef',
        pointRadius         : 3,
        pointHoverRadius    : 7,
        pointColor          : '#efefef',
        pointBackgroundColor: '#efefef',
        data                : [2666, 2778, 4912, 3767, 6810, 5670, 4820, 15073, 10687, 8432]
      }
    ]
  }

  var salesGraphChartOptions = {
    maintainAspectRatio : false,
    responsive : true,
    legend: {
      display: false,
    },
    scales: {
      xAxes: [{
        ticks : {
          fontColor: '#efefef',
        },
        gridLines : {
          display : false,
          color: '#efefef',
          drawBorder: false,
        }
      }],
      yAxes: [{
        ticks : {
          stepSize: 5000,
          fontColor: '#efefef',
        },
        gridLines : {
          display : true,
          color: '#efefef',
          drawBorder: false,
        }
      }]
    }
  }

  // This will get the first returned node in the jQuery collection.
  var salesGraphChart = new Chart(salesGraphChartCanvas, { 
      type: 'line', 
      data: salesGraphChartData, 
      options: salesGraphChartOptions
    }
  )


})
})
})
}

loginChartDataAjax();

