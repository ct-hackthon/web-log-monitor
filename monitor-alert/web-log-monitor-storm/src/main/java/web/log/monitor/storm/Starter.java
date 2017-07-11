package web.log.monitor.storm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.shade.com.google.common.collect.Maps;
import org.apache.storm.topology.TopologyBuilder;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
import web.log.monitor.storm.bolt.DemoBolt;
import web.log.monitor.storm.spout.WebLogSpout;


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
public class Starter {

    private static Logger LOGGER = LogManager.getLogger(Starter.class.getName());

    private static final String WEB_LOG_SPOUT_ID = "webLogSpout";
    private static final String DEMO_BOLT_ID = "demoBolt";

    public static void main(String[] arg) {

        LOGGER.info("====================== Start a topology ======================");

        // define Spout
        LOGGER.info("====================== Define Spout ===================");
        WebLogSpout webLogSpout = new WebLogSpout();

        // define bolts
        LOGGER.info("====================== Define bolts ===================");
        DemoBolt demoBolt = new DemoBolt();


        // define Topology
//        TridentTopology topology = new TridentTopology();
        LOGGER.info("====================== Define Topology ===================");
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout(WEB_LOG_SPOUT_ID, webLogSpout, 1);
        builder.setBolt(DEMO_BOLT_ID,demoBolt).shuffleGrouping(WEB_LOG_SPOUT_ID);

        //Define Config
        LOGGER.info("====================== Define Config ===================");
        Config conf = new Config();
        conf.setDebug(true);
        conf.setNumWorkers(3);

        //提交 本地
        LOGGER.info("====================== Submit Topology ===================");
        LocalCluster cluster = new LocalCluster();
        System.out.println("start wordcount");
        cluster.submitTopology("web-log-monitor", Maps.newHashMap(), builder.createTopology());


        // 提交到集群
//        try{
//            String topoName = "web-log";
//            StormSubmitter.submitTopology(topoName, conf, builder.createTopology());
//        }catch (Exception e){
//            LOGGER.info(e.getMessage());
//        }

    }
}
