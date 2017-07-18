package web.log.monitor.storm.bolt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import web.log.monitor.common.spring.SpringContextUtil;
import web.log.monitor.common.time.TimeUtil;
import web.log.monitor.dao.dao.SpoutHistoryDao;
import web.log.monitor.storm.entity.WebLog;

/************************************************************
 * Copy Right Information : 
 * Project : ${ProjectName}
 * JDK version used : ${SDK}
 * Comments :
 *              HF filter , timeID computer
 *
 * Modification history : 
 *
 * Sr *** Date      *** Modified By *** Why & What is modified
 * 1. *** 2017/7/12  *** fulongwen   *** Initial
 ***********************************************************/
public class FilterBolt extends BaseBolt{

    private static Logger LOGGER = LogManager.getLogger(FilterBolt.class.getName());

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {


        try {
            if (tuple != null) {
                for (int index = 0; index < tuple.size(); index++) {
                    WebLog data = (WebLog) tuple.getValue(index);

                    basicOutputCollector.emit(new Values(data));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
