

var http_normal_line_chart = echarts.init(document.getElementById('http-normal-line-chart'));
var http_error_line_chart = echarts.init(document.getElementById('http-error-line-chart'));
http_normal_line_chart.showLoading();
http_error_line_chart.showLoading();


function http_statistic_line_chart_render() {
    $.ajax({
        url: host + '/weblog/dashboard/httpStatistic',// 跳转到 action
        data: {
            timeId: timeWindow
        },
        type: 'GET',
        cache: false,
        dataType: 'json',
        success: function (data) {
            http_normal_line_chart.hideLoading();
            http_error_line_chart.hideLoading();
            http_normal_line_chart.setOption({
                title:{
                    text:'HTTP请求走势图'
                },
                legend: {
                    data:['200请求']
                },
                xAxis:{
                    type:"category",
                    data:data.data.xaxis
                },
                yAxis:{
                    type:'value'
                },
                series:[
                    {name:"200请求",type:"line",data:data.data.normalData}
                ]
            });
            http_error_line_chart.setOption({
                title:{
                    text:'HTTP请求走势图'
                },
                legend: {
                    data:['非200请求']
                },
                xAxis:{
                    type:"category",
                    data:data.data.xaxis
                },
                yAxis:{
                    type:'value'
                },
                series:[
                    { name:"非200请求",type:"line",data:data.data.errorData}
                ]
            });
        },
        error: function () {
            // view("异常！");
            // alert("异常！");
        }

    });
}







