


function http_statistic_result_render(){
    $.ajax({
        url: host + '/weblog/dashboard/httpCurrentCd',// 跳转到 action
        data: {
            timeId: timeWindow
        },
        type: 'GET',
        cache: false,
        dataType: 'json',
        success: function (data) {
            $("#current_error_count").html(data.data.currentErrorCount);
            $("#prediect_error_count").html(data.data.predictErrorCount);
            $("#pre_cur_error_count").html(data.data.devation);
            $("#standard_devation").html(data.data.variance);
            $("#outerlier").html(data.data.outerlier);
            $("#mean_error_count").html(data.data.meanErrorCount);
            $("#Time_Window").html(timeWindow);
        },
    error: function () {
            // view("异常！");
            // alert("异常！");
        }

    });
}
