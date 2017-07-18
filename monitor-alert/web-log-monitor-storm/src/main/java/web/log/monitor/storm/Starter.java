package web.log.monitor.storm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.shade.com.google.common.collect.Maps;
import org.apache.storm.topology.TopologyBuilder;
import web.log.monitor.storm.bolt2.*;
import web.log.monitor.storm.spout.WebLogSpout;


/************************************************************
 * Copy Right Information : 
 * Project : ${ProjectName}
 * JDK version used : ${SDK}
 * Comments : 
 *
 *
 *
 * ·Shuffle grouping（随机分组）：这种方式会随机分发tuple给bolt的各个task，每个bolt实例接收到的相
 同数量的tuple。
 ·Fields grouping（按字段分组）：根据指定字段的值进行分组。比如说，一个数据流根据“word”字段
 进行分组，所有具有相同“word”字段值的tuple会路由到同一个bolt的task中。
 ·All grouping（全复制分组）：将所有的tuple复制后分发给所有bolt task。每个订阅数据流的task都会接
 收到tuple的拷贝。
 ·Globle grouping（全局分组）：这种分组方式将所有的tuples路由到唯一一个task上。Storm按照最小的
 task ID来选取接收数据的task。注意，当使用全局分组方式时，设置bolt的task并发度是没有意义的，因为
 所有tuple都转发到同一个task上了。使用全局分组的时候需要注意，因为所有的tuple都转发到一个JVM实
 例上，可能会引起Storm集群中某个JVM或者服务器出现性能瓶颈或崩溃。
 ·None grouping（不分组）：在功能上和随机分组相同，是为将来预留的。
 ·Direct grouping（指向型分组）：数据源会调用emitDirect（）方法来判断一个tuple应该由哪个Storm组
 件来接收。只能在声明了是指向型的数据流上使用。
 ·Local or shuffle grouping（本地或随机分组）：和随机分组类似，但是，会将tuple分发给同一个worker
 内的bolt task（如果worker内有接收数据的bolt task）。其他情况下，采用随机分组的方式。取决于topology
 的并发度，本地或随机分组可以减少网络传输，从而提高topology性能。
 * Modification history : 
 *
 * Sr *** Date      *** Modified By *** Why & What is modified
 * 1. *** 2017/7/10 *** fulongwen   *** Initial
 ***********************************************************/
public class Starter {

    private static Logger LOGGER = LogManager.getLogger(Starter.class.getName());

    private static final String TOPOLOGY_NAME  = "web-log-monitor";

    private static final String WEB_LOG_SPOUT_ID = "1";
    private static final String DEMO_BOLT_ID = "demoBolt";
    private static final String TO_JSON_BOLT_ID = "2";
    private static final String FILTER_BOLD_ID = "3";
    private static final String HTTP_ERROR_STATISTIC_BOLD_ID = "4";
    private static final String LOC_CODE_STATISTIC_BOLD_ID = "5";
    private static final String URL_CODE_STATISTIC_BOLD_ID = "6";
    private static final String MNO_CODE_STATISTIC_BOLD_ID = "7";
    private static final String PID_CODE_STATISTIC_BOLD_ID = "8";
    private static final String HTTP_CODE_STATISTIC_BOLD_ID = "9";
    private static final String CITY_STATISTIC_BOLD_ID = "10";

    public static void main(String[] arg) {

        LOGGER.info("====================== Start a topology ======================");

        // define Spout
        LOGGER.info("====================== Define Spout ===================");
        WebLogSpout webLogSpout = new WebLogSpout();

        // define bolts
        LOGGER.info("====================== Define bolts ===================");
        NewToJsonBolt toJsonBolt = new NewToJsonBolt();
        NewHttpStatisticBolt httpErrorStatisticBolt = new NewHttpStatisticBolt();
        NewMnoStatisticBolt mnoStatisticBolt = new NewMnoStatisticBolt();
        NewPidStatisticBolt pidStatisticBolt = new NewPidStatisticBolt();
        NewUrlStatisticBolt urlStatisticBolt = new NewUrlStatisticBolt();
        NewLocStatisticBolt locStatisticBolt = new NewLocStatisticBolt();
        NewHttpCodeStatisticBolt httpCodeStatisticBolt = new NewHttpCodeStatisticBolt();
        NewCityStatisticBolt cityStatisticBolt = new NewCityStatisticBolt();


        // define Topology
//        TridentTopology topology = new TridentTopology();
        LOGGER.info("====================== Define Topology ===================");
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout(WEB_LOG_SPOUT_ID, webLogSpout, 1);
        builder.setBolt(TO_JSON_BOLT_ID,toJsonBolt,3).shuffleGrouping(WEB_LOG_SPOUT_ID);
        builder.setBolt(HTTP_ERROR_STATISTIC_BOLD_ID,httpErrorStatisticBolt,1).shuffleGrouping(TO_JSON_BOLT_ID);
        builder.setBolt(MNO_CODE_STATISTIC_BOLD_ID,mnoStatisticBolt,1).shuffleGrouping(HTTP_ERROR_STATISTIC_BOLD_ID);
        builder.setBolt(PID_CODE_STATISTIC_BOLD_ID,pidStatisticBolt,1).shuffleGrouping(MNO_CODE_STATISTIC_BOLD_ID);
        builder.setBolt(URL_CODE_STATISTIC_BOLD_ID,urlStatisticBolt,1).shuffleGrouping(PID_CODE_STATISTIC_BOLD_ID);
        builder.setBolt(LOC_CODE_STATISTIC_BOLD_ID,locStatisticBolt,1).shuffleGrouping(URL_CODE_STATISTIC_BOLD_ID);
        builder.setBolt(HTTP_CODE_STATISTIC_BOLD_ID,httpCodeStatisticBolt,1).shuffleGrouping(LOC_CODE_STATISTIC_BOLD_ID);
        builder.setBolt(CITY_STATISTIC_BOLD_ID,cityStatisticBolt,1).shuffleGrouping(HTTP_CODE_STATISTIC_BOLD_ID);

        //Define Config
        LOGGER.info("====================== Define Config ===================");
        Config conf = new Config();
        conf.setDebug(true);
        conf.setNumWorkers(3);

        //提交 本地
        LOGGER.info("====================== Submit Topology ===================");
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology(TOPOLOGY_NAME, Maps.newHashMap(), builder.createTopology());


        // 提交到集群
//        try{
//            String topoName = "web-log";
//            StormSubmitter.submitTopology(topoName, conf, builder.createTopology());
//        }catch (Exception e){
//            LOGGER.info(e.getMessage());
//        }

    }
}
