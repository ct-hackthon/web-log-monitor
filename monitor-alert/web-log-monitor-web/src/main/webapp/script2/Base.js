const host = 'http://10.18.68.42:8080/weblog';
// const host = 'http://localhost:8080';
timeWindow = 201704010100;


function raiseTimeId(){
    $.ajax({
        url: host + '/weblog/dashboard/raiseTimeId',// 跳转到 action
        data: {
            timeId: timeWindow
        },
        type: 'GET',
        cache: false,
        dataType: 'json',
        success: function (data) {
            timeWindow = data.data
        }
    });
}

function render(){

    http_poi_line_chart_render();
    http_error_box_chart_render();
    http_statistic_result_render();
    http_map_render();

    raiseTimeId();
}

setInterval(render, 1000);

