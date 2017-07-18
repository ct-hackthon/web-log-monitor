package web.log.monitor.storm.bolt2;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import web.log.monitor.common.time.TimeUtil;
import web.log.monitor.storm.entity.WebLog;


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
public class NewToJsonBolt extends BaseBolt {


    private static Logger LOGGER = LogManager.getLogger(NewToJsonBolt.class.getName());


    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
//        LOGGER.info("NewToJsonBolt  receive data ");

        try {
            if (tuple != null) {
                for (int index = 0; index < tuple.size(); index++) {
                    String data = tuple.getString(index);


                    if (data.contains("loc$httpCode$reqUrl$reqTime$mno$resSize$netType$uid$pid$hf$X-Date$lat$lng$host$reqTarget$minute")) {
                        LOGGER.info(data);
                    } else {

                        String[] fields = data.split("\\$");


                        WebLog webLog = new WebLog();
                        String loc = fields[0] == null ? "" : fields[0].trim();
                        String httpCode = fields[1] == null ? "" : fields[1].trim();
                        String reqUrl = fields[2] == null ? "" : fields[2].trim();
                        String reqTime = fields[3] == null ? "" : fields[3].trim();
                        String mno = fields[4] == null ? "" : fields[4].trim();
                        String resSize = fields[5] == null ? "" : fields[5].trim();
                        String netType = fields[6] == null ? "" : fields[6].trim();
                        String uid = fields[7] == null ? "" : fields[7].trim();
                        String pid = fields[8] == null ? "" : fields[8].trim();
                        String hf = fields[9] == null ? "" : fields[9].trim();
                        String xDate = fields[10] == null ? "" : fields[10].trim();
                        String lat = fields[11] == null ? "" : fields[11].trim();
                        String lgt = fields[12] == null ? "" : fields[12].trim();
                        String host = fields[13] == null ? "" : fields[13].trim();
                        String reqTarget = fields[14] == null ? "" : fields[14].trim();
                        String minute = fields[15] == null ? "" : fields[15].trim();

                        String realCity;
                        try {
                            String city = fields[16] == null ? "" : fields[16].trim();
                            if (city.isEmpty()) {
                                realCity = "";
                            } else {
                                String[] citys = city.split(",");
                                realCity = citys[1] == null ? "" : citys[1];
                            }
                        } catch (Exception e) {
                            realCity = "";
                        }


                        Long timeId = TimeUtil.getMiniteId(xDate);
                        String timeIdStr = TimeUtil.getMinuteText(timeId);

                        webLog.setLogTimeId(timeId);
                        webLog.setLogTimeIdStr(timeIdStr);
                        webLog.setLoc(loc);
                        webLog.setHttpCode(httpCode);
                        webLog.setReqUrl(reqUrl);
                        webLog.setReqTime(reqTime);
                        webLog.setMno(mno);
                        webLog.setReqSize(resSize);
                        webLog.setNetType(netType);
                        webLog.setUid(uid);
                        webLog.setPid(pid);
                        webLog.setHf(hf);
                        webLog.setLat(lat);
                        webLog.setLgt(lgt);
                        webLog.setHost(host);
                        webLog.setReqTarget(reqTarget);
                        webLog.setMinute(minute);
                        webLog.setCity(realCity);


                        basicOutputCollector.emit(new Values(webLog));
                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
