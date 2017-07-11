package web.log.monitor.storm.spout;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;
import web.log.monitor.common.spring.SpringContextUtil;

import javax.jms.*;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

/************************************************************
 * Copy Right Information : 
 * Project : ${ProjectName}
 * JDK version used : ${SDK}
 * Comments : 
 *
 * Modification history : 
 *
 * Sr *** Date      *** Modified By *** Why & What is modified
 * 1. *** 2017/7/10 *** fulongwen   *** Initial
 ***********************************************************/
public class WebLogSpout extends BaseRichSpout implements MessageListener {


    private static Logger LOGGER = LogManager.getLogger(WebLogSpout.class.getName());


    private static LinkedBlockingQueue<String> queue;
    private SpoutOutputCollector collector;


    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("data"));
    }

    @Override
    public void onMessage(Message message) {

           if(message instanceof TextMessage){
               LOGGER.info("Receive a message " + message);
               try {
                   String data  = ((TextMessage) message).getText();
                   if(queue!=null){
                       LOGGER.info("LinkedQueueSize" + queue.size());
                       queue.offer(data);
                   }else{
                       LOGGER.warn("LinkedQueueSize is null ");
                   }

               } catch (JMSException e) {
                   LOGGER.info("Error occur on receiving message");
               }

           }
    }

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {


        LOGGER.info("============================== init spring ==============================");
        SpringContextUtil.init();
        LOGGER.info("============================== init a spout ==============================");

        synchronized (this){
            this.queue = new LinkedBlockingQueue<>();
            this.collector = spoutOutputCollector;
        }
        LOGGER.info("============================== create blockqing queue ==============================");
        LOGGER.info("LinkedBlockingQueue size " + queue.size());

        LOGGER.info("============================== init a spoutOutputCollector ==============================");

    }

    @Override
    public void nextTuple() {

        String data = this.queue.poll();
        if(data == null) {
            Utils.sleep(50L);
        } else {
            LOGGER.debug("sending tuple: " + data);
            this.collector.emit(new Values(data));
        }
    }
}
