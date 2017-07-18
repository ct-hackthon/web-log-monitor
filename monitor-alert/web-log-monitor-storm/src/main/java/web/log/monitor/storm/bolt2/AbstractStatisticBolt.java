package web.log.monitor.storm.bolt2;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import web.log.monitor.storm.bolt.BaseBolt;
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
 * 1. *** 2017/7/15  *** fulongwen   *** Initial
 ***********************************************************/
public abstract class AbstractStatisticBolt extends BaseBolt {

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        try {
            if (tuple != null) {
                for (int index = 0; index < tuple.size(); index++) {
                    WebLog data = (WebLog) tuple.getValue(index);
                    doStatistic(data);
                    basicOutputCollector.emit(new Values(data));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public abstract void doStatistic(WebLog weblog);
}
