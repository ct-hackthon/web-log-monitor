package web.log.monitor.storm.bolt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;
import web.log.monitor.common.spring.SpringContextUtil;
import web.log.monitor.dao.dao.SpoutHistoryDao;

/************************************************************
 * Copy Right Information : 
 * Project : ${ProjectName}
 * JDK version used : ${SDK}
 * Comments : 
 *
 * Modification history : 
 *
 * Sr *** Date      *** Modified By *** Why & What is modified
 * 1. *** 2017/7/11 *** fulongwen   *** Initial
 ***********************************************************/
public class DemoBolt extends BaseBolt{


    private static Logger LOGGER = LogManager.getLogger(DemoBolt.class.getName());

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {

        LOGGER.info("Demo Bolt receive data " + tuple.getString(0));

        SpoutHistoryDao spoutHistoryDao = SpringContextUtil.getBean("spoutHistoryDao");

        spoutHistoryDao.save(tuple.getString(0));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
