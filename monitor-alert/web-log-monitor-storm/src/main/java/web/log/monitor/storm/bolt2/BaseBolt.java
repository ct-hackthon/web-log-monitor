package web.log.monitor.storm.bolt2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import web.log.monitor.common.spring.SpringContextUtil;

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
 * 1. *** 2017/7/11  *** fulongwen   *** Initial
 ***********************************************************/
public abstract class BaseBolt extends BaseBasicBolt {

    private static Logger LOGGER = LogManager.getLogger(BaseBolt.class.getName());

    public void prepare(Map stormConf, TopologyContext context) {
        LOGGER.info("============================== init spring ==============================");
        SpringContextUtil.init();
        LOGGER.info("============================== init a bolt ==============================");
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("data"));
    }
}
