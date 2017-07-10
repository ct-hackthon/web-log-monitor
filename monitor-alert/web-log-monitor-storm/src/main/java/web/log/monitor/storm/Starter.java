package web.log.monitor.storm;

import org.apache.storm.LocalCluster;
import org.apache.storm.shade.com.twitter.chill.config.Config;

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

    public static void main(String[] arg){

//        // define Spout
//        FixedBatchSpout spout = new FixedBatchSpout(new Fields("a", "b", "c"),
//                1, new Values(1, 2, 3), new Values(4, 1, 6),
//                new Values(3, 0, 8));
//        spout.setCycle(false);

        // define Topology
//        TridentTopology topology = new TridentTopology();
//        topology.newStream("spout", spout)
//                .each(new Fields("b"), new MyFunction(), new Fields("d"))
//                .each(new Fields("a", "b", "c", "d"), new PrintFunctionBolt(),
//                        new Fields(""));

        //Define Config
//        Config config = new Config();
//        config.setNumWorkers(2);
//        config.setNumAckers(1);
//        config.setDebug(false);

        // Start topology
//        StormSubmitter.submitTopology("trident_function", config,
//                topology.build());
    }
}
