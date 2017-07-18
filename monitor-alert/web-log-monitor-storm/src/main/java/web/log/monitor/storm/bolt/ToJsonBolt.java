package web.log.monitor.storm.bolt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import web.log.monitor.common.time.TimeUtil;
import web.log.monitor.storm.entity.WebLog;

import java.util.List;
import java.util.Map;

/************************************************************
 * Copy Right Information : 
 * Project : ${ProjectName}
 * JDK version used : ${SDK}
 * Comments : 
 *
 * Modification history : 
 *
 * Sr *** Date      *** Modified By *** Why & What is modified
 * 1. *** 2017/7/12 *** fulongwen   *** Initial
 ***********************************************************/
public class ToJsonBolt extends BaseBolt {


    private static Logger LOGGER = LogManager.getLogger(ToJsonBolt.class.getName());


    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
//        LOGGER.info("NewToJsonBolt  receive data ");

        try {
            if (tuple != null) {
                for (int index = 0; index < tuple.size(); index++) {
                    String data = tuple.getString(index);

                    String[] data_tmp = data.split(">>");
                    String log_time = data_tmp[0];
                    String parse_str = data_tmp[1].trim();


                    String log_time_str = log_time.replace("[","").replace("]","").trim();
                    Long logTimeId = TimeUtil.getTimeidFromString(log_time_str);

                    String[] parse_str_arry = parse_str.split("####");
                    String part1 = parse_str_arry[0];
                    String part2 = parse_str_arry[1];

                    Map<String, String> map = JSON.parseObject(part1,
                            new TypeReference<Map<String, String>>() {}
                            );

                    if (null != map.get("network")) {

                        String value = map.get("network");

                        List<WebLog> list = JSONObject.parseArray(value, WebLog.class);

                        if (list != null) {
                            list.forEach(x -> htmlParamToMap(part2, x));
                            list.forEach(x->x.setLogTimeId(logTimeId));
                            list.forEach(x -> basicOutputCollector.emit(new Values(x)));
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    private void htmlParamToMap(String str, WebLog webLog) {

        if (str != null) {
            String[] stringArray = str.split("&");
            for (String entry : stringArray) {
                String[] key_value = entry.split("=");
                String key = key_value[0];
                String value;
                if (key_value.length == 2) {
                    value = key_value[1];
                } else {
                    value = "";
                }

                if (key.equalsIgnoreCase("lgt")) {
                    webLog.setLgt(value);
                }
                if (key.equalsIgnoreCase("lat")) {
                    webLog.setLat(value);
                }
                if (key.equalsIgnoreCase("pid")) {
                    webLog.setPid(value);
                }

            }
        }
    }
}
