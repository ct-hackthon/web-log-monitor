

var http_url_topN_bar_chart = echarts.init(document.getElementById('http-url-topN-bar-chart'));
http_url_topN_bar_chart.showLoading();


function http_url_topN_bar_chart_render(){


    // option = {
    //     tooltip : {
    //         trigger: 'axis',
    //         axisPointer : {            // 坐标轴指示器，坐标轴触发有效
    //             type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
    //         },
    //
    //
    //     },
    //     legend: {
    //         data:['利润', '支出', '收入']
    //     },
    //     grid: {
    //         left: '3%',
    //         right: '4%',
    //         bottom: '3%',
    //         containLabel: true
    //     },
    //     xAxis : [
    //         {
    //             type : 'value'
    //         }
    //     ],
    //     yAxis : [
    //         {
    //             type : 'category',
    //             axisTick : {show: false},
    //             data : ['周一','周二','周三','周四','周五','周六','周日']
    //         }
    //     ],
    //     series : [
    //         {
    //             name:'利润',
    //             type:'bar',
    //             label: {
    //                 normal: {
    //                     show: true,
    //                     position: 'inside'
    //                 }
    //             },
    //             data:[200, 170, 240, 244, 200, 220, 210]
    //         },
    //         {
    //             name:'收入',
    //             type:'bar',
    //             stack: '总量',
    //             label: {
    //                 normal: {
    //                     show: true
    //                 }
    //             },
    //             data:[320, 302, 341, 374, 390, 450, 420]
    //         },
    //         {
    //             name:'支出',
    //             type:'bar',
    //             stack: '总量',
    //             label: {
    //                 normal: {
    //                     show: true,
    //                     position: 'left'
    //                 }
    //             },
    //             data:[-120, -132, -101, -134, -190, -230, -210]
    //         }
    //     ]
    // };


    $.ajax({
        url: host + '/weblog/dashboard/urlErrorStatistic',
        data: {
            timeId: timeWindow
        },
        type: 'GET',
        cache: false,
        dataType: 'json',
        success: function (data) {
            http_url_topN_bar_chart.hideLoading();
            http_url_topN_bar_chart.setOption({
                title:{
                    text:"TOP 10 error url"
                },
                tooltip : {
                    trigger: 'axis',
                    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                        type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    },
                    // formatter: function (data) {
                    //     return data.data.url;
                    // }
                },
                legend: {
                    data:['错误', '正常']
                },
                // grid: {
                //     left: '3%',
                //     right: '4%',
                //     bottom: '3%',
                //     containLabel: true
                // },
                xAxis : [
                    {
                        type : 'value'
                    }
                ],
                yAxis : [
                    {
                        type : 'category',
                        axisTick : {show: false},
                        // data : [1,2,3,4,5,6,7,8,9,10]
                        data :data.data.url
                    }
                ],
                series : [
                    {
                        name:'错误',
                        type:'bar',
                        stack: '总量',
                        label: {
                            normal: {
                                show: true,
                                // position: 'inside'
                            }
                        },
                        data:data.data.errorCount
                    },
                    {
                        name:'正常',
                        type:'bar',
                        stack: '总量',
                        label: {
                            normal: {
                                show: true
                            }
                        },
                        data:data.data.normalCount
                    }
                ]
            });
        },
        error: function () {
            // view("异常！");
            // alert("异常！");
        }

    });
}

