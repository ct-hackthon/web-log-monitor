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
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;

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


    private LinkedBlockingQueue<String> queue;
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
                   this.queue.offer(data);
               } catch (JMSException e) {
                   LOGGER.info("Error occur on receiving message");
               }

           }
    }

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {


        LOGGER.info("============================== init a spout ==============================");


        String context = "/spring/application-context-storm-jms-consumer.xml";
        String connectionFactoryBeanName = "connectionFactory";
        String destinationBeanName = "queueDestination";

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(context);
        ConnectionFactory connectionFactory = (ConnectionFactory)applicationContext.getBean(connectionFactoryBeanName);
        Destination destination =  (Destination)applicationContext.getBean(destinationBeanName);

        this.queue = new LinkedBlockingQueue<>();
        this.collector = spoutOutputCollector;

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
