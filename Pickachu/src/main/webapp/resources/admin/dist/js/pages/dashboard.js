/*
 * Author: Abdullah A Almsaeed
 * Date: 4 Jan 2014
 * Description:
 *      This is a demo file used only for the main dashboard (index.html)
 **/

/* global moment:false, Chart:false, Sparkline:false */

var loginData = [0,0,0,0,0,0,0];
var countryData= [0,0];
var visitorsData= {};

function loginChartDataAjax(){
  
  var data1 = 1;
	console.log("여기까지오나");  

  $.ajax({	
       url: '/ccc/admin/loginCountAjax',
       method: "GET",
       data: {'data': data1 }
       })
  .done(function(data) {
    console.log("결과값"+JSON.stringify(data[0]));
    loginDataTotal = data;
    
    //salesChartData.data = [0,0,0,0,0,0,1];
    console.log(JSON.stringify(loginDataTotal));
    console.log(loginDataTotal[0].total);


		$.ajax({
	         url: '/ccc/admin/countCountryCodeAjax',
	    	 method: "GET",
	 	     data: {'data': data1}
	 	     })
		.done(function(data) {
			countryData = data

			//맵. 키와 벨류로 되어있다. 몇개들어있는지 알수는 없다. 
			for(var i=0; i<Object.keys(countryData).length; i++){
				var keyname = ''; 
//				키 네임은 아무 것도 없다. 
//				비지터 데이터[키네임] 
				visitorsData[keyname + data[i].countryCode] 
				= data[i].countryCodeTotal;		
				//'' 넣어준 건 강제로 스트링화시키기 위함.
				////= 이후는 값을 세팅하는 거.
				//결과물은  JSON이 됨.
				//visitorsData[숫자]  --> 배열
				//visitorsData[스트링]  --> 맵   
				
			}
			// visitorData [ㄹㄹ,ㄹ,ㄹ,ㄹ,ㄹ, ]
			
			
			
			console.log(JSON.stringify(countryData));
			console.log(JSON.stringify(visitorsData));


$(function () {
  'use strict'

  // Make the dashboard widgets sortable Using jquery UI
  $('.connectedSortable').sortable({
    placeholder: 'sort-highlight',
    connectWith: '.connectedSortable',
    handle: '.card-header, .nav-tabs',
    forcePlaceholderSize: true,
    zIndex: 999999
  })
  $('.connectedSortable .card-header').css('cursor', 'move')

  // jQuery UI sortable for the todo list
  $('.todo-list').sortable({
    placeholder: 'sort-highlight',
    handle: '.handle',
    forcePlaceholderSize: true,
    zIndex: 999999
  })

  // bootstrap WYSIHTML5 - text editor
  $('.textarea').summernote()

  $('.daterange').daterangepicker({
    ranges: {
      Today: [moment(), moment()],
      Yesterday: [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
      'Last 7 Days': [moment().subtract(6, 'days'), moment()],
      'Last 30 Days': [moment().subtract(29, 'days'), moment()],
      'This Month': [moment().startOf('month'), moment().endOf('month')],
      'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
    },
    startDate: moment().subtract(29, 'days'),
    endDate: moment()
  }, function (start, end) {
    // eslint-disable-next-line no-alert
    alert('You chose: ' + start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'))
  })

  /* jQueryKnob */
  $('.knob').knob()

  // jvectormap data
  var tt = {
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
  })

  // Sparkline charts
  var sparkline1 = new Sparkline($('#sparkline-1')[0], { width: 80, height: 50, lineColor: '#92c1dc', endColor: '#ebf4f9' })
  var sparkline2 = new Sparkline($('#sparkline-2')[0], { width: 80, height: 50, lineColor: '#92c1dc', endColor: '#ebf4f9' })
  var sparkline3 = new Sparkline($('#sparkline-3')[0], { width: 80, height: 50, lineColor: '#92c1dc', endColor: '#ebf4f9' })

  sparkline1.draw([1000, 1200, 920, 927, 931, 1027, 819, 930, 1021])
  sparkline2.draw([515, 519, 520, 522, 652, 810, 370, 627, 319, 630, 921])
  sparkline3.draw([15, 19, 20, 22, 33, 27, 31, 27, 19, 30, 21])

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
  var salesChartCanvas = document.getElementById('revenue-chart-canvas').getContext('2d')
  // $('#revenue-chart').get(0).getContext('2d');

  var salesChartData = {
    labels: [loginDataTotal[6].month+"."+loginDataTotal[6].date, loginDataTotal[5].month+"."+loginDataTotal[5].date, loginDataTotal[4].month+"."+loginDataTotal[4].date, loginDataTotal[3].month+"."+loginDataTotal[3].date, loginDataTotal[2].month+"."+loginDataTotal[2].date, loginDataTotal[1].month+"."+loginDataTotal[1].date, loginDataTotal[0].month+"."+loginDataTotal[0].date],
    datasets: [
      {
        label: 'Digital Goods',
        backgroundColor: 'rgba(60,141,188,0.9)',
        borderColor: 'rgba(60,141,188,0.8)',
        pointRadius: false,
        pointColor: '#3b8bba',
        pointStrokeColor: 'rgba(60,141,188,1)',
        pointHighlightFill: '#fff',
        pointHighlightStroke: 'rgba(60,141,188,1)',
        data: [loginDataTotal[6].successTotal, loginDataTotal[5].successTotal, loginDataTotal[4].successTotal, loginDataTotal[3].successTotal, loginDataTotal[2].successTotal, loginDataTotal[1].successTotal, loginDataTotal[0].successTotal]
      },
      {
        label: 'Electronics',
        backgroundColor: 'rgba(210, 214, 222, 1)',
        borderColor: 'rgba(210, 214, 222, 1)',
        pointRadius: false,
        pointColor: 'rgba(210, 214, 222, 1)',
        pointStrokeColor: '#c1c7d1',
        pointHighlightFill: '#fff',
        pointHighlightStroke: 'rgba(220,220,220,1)',
        data: [loginDataTotal[6].total, loginDataTotal[5].total, loginDataTotal[4].total, loginDataTotal[3].total, loginDataTotal[2].total, loginDataTotal[1].total, loginDataTotal[0].total]
      }
    ]
  }

  var salesChartOptions = {
    maintainAspectRatio: false,
    responsive: true,
    legend: {
      display: false
    },
    scales: {
      xAxes: [{
        gridLines: {
          display: false
        }
      }],
      yAxes: [{
        gridLines: {
          display: false
        }
      }]
    }
  }

  // This will get the first returned node in the jQuery collection.
  // eslint-disable-next-line no-unused-vars
  var salesChart = new Chart(salesChartCanvas, {
    type: 'line',
    data: salesChartData,
    options: salesChartOptions
  })

  // Donut Chart
  var pieChartCanvas = $('#sales-chart-canvas').get(0).getContext('2d')
  var pieData = {
    labels: [
      'Instore Sales',
      'Download Sales',
      'Mail-Order Sales'
    ],
    datasets: [
      {
        data: [30, 12, 20],
        backgroundColor: ['#f56954', '#00a65a', '#f39c12']
      }
    ]
  }
  var pieOptions = {
    legend: {
      display: false
    },
    maintainAspectRatio: false,
    responsive: true
  }
  // Create pie or douhnut chart
  // You can switch between pie and douhnut using the method below.
  // eslint-disable-next-line no-unused-vars
  var pieChart = new Chart(pieChartCanvas, {
    type: 'doughnut',
    data: pieData,
    options: pieOptions
  })

  // Sales graph chart
  var salesGraphChartCanvas = $('#line-chart').get(0).getContext('2d')
  // $('#revenue-chart').get(0).getContext('2d');

  var salesGraphChartData = {
    labels: ['2011 Q1', '2011 Q2', '2011 Q3', '2011 Q4', '2012 Q1', '2012 Q2', '2012 Q3', '2012 Q4', '2013 Q1', '2013 Q2'],
    datasets: [
      {
        label: 'Digital Goods',
        fill: false,
        borderWidth: 2,
        lineTension: 0,
        spanGaps: true,
        borderColor: '#efefef',
        pointRadius: 3,
        pointHoverRadius: 7,
        pointColor: '#efefef',
        pointBackgroundColor: '#efefef',
        data: [2666, 2778, 4912, 3767, 6810, 5670, 4820, 15073, 10687, 8432]
      }
    ]
  }

  var salesGraphChartOptions = {
    maintainAspectRatio: false,
    responsive: true,
    legend: {
      display: false
    },
    scales: {
      xAxes: [{
        ticks: {
          fontColor: '#efefef'
        },
        gridLines: {
          display: false,
          color: '#efefef',
          drawBorder: false
        }
      }],
      yAxes: [{
        ticks: {
          stepSize: 5000,
          fontColor: '#efefef'
        },
        gridLines: {
          display: true,
          color: '#efefef',
          drawBorder: false
        }
      }]
    }
  }

  // This will get the first returned node in the jQuery collection.
  // eslint-disable-next-line no-unused-vars
  var salesGraphChart = new Chart(salesGraphChartCanvas, {
    type: 'line',
    data: salesGraphChartData,
    options: salesGraphChartOptions
  })
})

	})
	})
	}
	
	loginChartDataAjax();
	