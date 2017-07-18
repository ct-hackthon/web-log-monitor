


var http_error_box_chart = echarts.init(document.getElementById('http-error-box-chart'));
http_error_box_chart.showLoading();


function http_statistic_box_chart_render(){

    $.ajax({
        url: host + '/weblog/dashboard/httpErrorBox',// 跳转到 action
        data: {
            timeId: timeWindow
        },
        type: 'GET',
        cache: false,
        dataType: 'json',
        success: function (data) {

            // var tmp = echarts.dataTool.prepareBoxplotData([
            //     [850, 740, 900, 1070, 930, 850, 950, 980, 980, 880, 1000, 980, 930, 650, 760, 810, 1000, 1000, 960, 960]
            // ]);

            var boxData = echarts.dataTool.prepareBoxplotData( [
               data.data.errorSeries
                ]
            );
            http_error_box_chart.hideLoading();
            http_error_box_chart.setOption({
                title: [
                    {
                        text: '异常检测箱',
                        left: 'center'
                    },
                    {
                        text: 'upper: Q3 + 1.5 * IRQ \nlower: Q1 - 1.5 * IRQ',
                        borderColor: '#999',
                        borderWidth: 1,
                        textStyle: {
                            fontSize: 14
                        },
                        left: '10%',
                        top: '90%'
                    }
                ],
                tooltip: {
                    trigger: 'item',
                    axisPointer: {
                        type: 'shadow'
                    }
                },
                grid: {
                    left: '10%',
                    right: '10%',
                    bottom: '15%'
                },
                xAxis: {
                    type: 'category',
                    data: timeWindow,
                    boundaryGap: true,
                    nameGap: 30,
                    splitArea: {
                        show: false
                    },
                    axisLabel: {
                        formatter: data.data.xaxis
                        // formatter: 'expr {value}'
                    },
                    splitLine: {
                        show: false
                    }
                },
                yAxis: {
                    type: 'value',
                    splitArea: {
                        show: true
                    }
                },
                series: [
                    {
                        name: 'boxplot',
                        type: 'boxplot',
                        data: boxData.boxData,
                        tooltip: {
                            formatter: function (param) {
                                return [
                                    'Experiment ' + param.name + ': ',
                                    'upper: ' + param.data[4],
                                    'Q3: ' + param.data[3],
                                    'median: ' + param.data[2],
                                    'Q1: ' + param.data[1],
                                    'lower: ' + param.data[0]
                                ].join('<br/>')
                            }
                        }
                    },
                    {
                        name: 'outlier',
                        type: 'scatter',
                        data: data.data.data
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

