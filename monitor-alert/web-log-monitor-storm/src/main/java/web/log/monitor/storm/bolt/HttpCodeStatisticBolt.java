package web.log.monitor.storm.bolt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.storm.shade.com.google.common.collect.Maps;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.tuple.Tuple;
import web.log.monitor.common.spring.SpringContextUtil;
import web.log.monitor.common.time.TimeUtil;
import web.log.monitor.dao.dao.HttpErrorStatisticDao;
import web.log.monitor.dao.entity.HttpErrorStatisticDo;
import web.log.monitor.storm.entity.WebLog;

import java.util.Iterator;
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
 * 1. *** 2017/7/12  *** fulongwen   *** Initial
 ***********************************************************/
public class HttpCodeStatisticBolt extends BaseBolt {

    private static Logger LOGGER = LogManager.getLogger(HttpCodeStatisticBolt.class.getName());

    private static Map<Long,Integer> normalCountMap = Maps.newConcurrentMap();
    private static Map<Long,Integer> errorCountMap = Maps.newConcurrentMap();
    private static Long timeWindow = 0L;

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {

       // LOGGER.info("Http Code Statistic Bold receive data");

        try {
            if (tuple != null) {
                for (int index = 0; index < tuple.size(); index++) {
                    WebLog data = (WebLog) tuple.getValue(index);
                    doStatistic2(data);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *  根据日志时间ID做聚合，
     * @param data
     */
    private void doStatistic( WebLog data){
        HttpErrorStatisticDao httpErrorStatisticDao = SpringContextUtil.getBean("httpErrorStatisticDao");
        long timeId = data.getLogTimeId();

        synchronized (timeWindow){

            if(timeId<timeWindow){
                return ;
            }

            if(timeWindow == 0L){
                timeWindow = timeId;
            }
        }
//        LOGGER.info("timeId : " + timeId);
//        LOGGER.info("TimeWindow : " + timeWindow);

        if(data.getHttpCode().equalsIgnoreCase("200")||data.getHttpCode().equalsIgnoreCase("\"200\"")){
//            LOGGER.info("HttpCode : " + data.getHttpCode());
            Integer count = normalCountMap.get(timeId);
            if(count==null){
                normalCountMap.put(timeId,1);
            }else{
                count++;
                normalCountMap.put(timeId,count);
            }


        }else{
//            LOGGER.info("HttpCode : " + data.getHttpCode());
            Integer count = errorCountMap.get(timeId);
            if(count==null){
                errorCountMap.put(timeId,1);
            }else{
                count++;
                errorCountMap.put(timeId,count);
            }
        }
//        LOGGER.info("normalCountMap size : " + normalCountMap.keySet().size());
        if(normalCountMap.keySet().size()>30){


            Long removeId;
            synchronized (timeWindow){
                removeId =  timeWindow;
            }
//            LOGGER.info("close time window " + removeId);
            Integer errorCount = errorCountMap.get(removeId)==null?0:errorCountMap.get(removeId);
            Integer normalCount = normalCountMap.get(removeId)==null?0:normalCountMap.get(removeId);

            HttpErrorStatisticDo domain = new HttpErrorStatisticDo();
            domain.setTimeId(removeId);
            domain.setErrorCount(errorCount);
            domain.setNormalCount(normalCount);
            domain.setTimeBox(TimeUtil.getMinuteText(removeId));

            httpErrorStatisticDao.saveOrUpdateHttpErrorStatistic(domain);

            Iterator<Map.Entry<Long, Integer>> it = errorCountMap.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<Long, Integer> entry = it.next();
                Long key = entry.getKey();
                if(key <=removeId){
                    it.remove();
                }
            }

            it = normalCountMap.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<Long, Integer> entry = it.next();
                Long key = entry.getKey();
                if(key <=removeId){
                    it.remove();
                }
            }


            synchronized (timeWindow){
                timeWindow = TimeUtil.raiseTimeId(removeId);
            }
        }
    }

    /**
     *  根据日志投递时间ID做聚合，
     * @param data
     */
    private void doStatistic2( WebLog data){
        HttpErrorStatisticDao httpErrorStatisticDao = SpringContextUtil.getBean("httpErrorStatisticDao");
        long timeId = data.getLogTimeId();

        synchronized (timeWindow){

            if(timeId<timeWindow){
                return ;
            }

            if(timeWindow == 0L){
                timeWindow = timeId;
            }
        }



        if(data.getHttpCode().equalsIgnoreCase("200")||data.getHttpCode().equalsIgnoreCase("\"200\"")){
            Integer count = normalCountMap.get(timeId);
            if(count==null){
                normalCountMap.put(timeId,1);
            }else{
                count++;
                normalCountMap.put(timeId,count);
            }

        }else{
            Integer count = errorCountMap.get(timeId);
            if(count==null){
                errorCountMap.put(timeId,1);
            }else{
                count++;
                errorCountMap.put(timeId,count);
            }
        }


        if(timeId>timeWindow){


            Long removeId;
            synchronized (timeWindow){
                removeId =  timeWindow;
            }
            Integer errorCount = errorCountMap.get(removeId)==null?0:errorCountMap.get(removeId);
            Integer normalCount = normalCountMap.get(removeId)==null?0:normalCountMap.get(removeId);

            HttpErrorStatisticDo domain = new HttpErrorStatisticDo();
            domain.setTimeId(removeId);
            domain.setErrorCount(errorCount);
            domain.setNormalCount(normalCount);
            domain.setTimeBox(TimeUtil.getMinuteText(removeId));

            httpErrorStatisticDao.saveOrUpdateHttpErrorStatistic(domain);

            Iterator<Map.Entry<Long, Integer>> it = errorCountMap.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<Long, Integer> entry = it.next();
                Long key = entry.getKey();
                if(key <=removeId){
                    it.remove();
                }
            }

            it = normalCountMap.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<Long, Integer> entry = it.next();
                Long key = entry.getKey();
                if(key <=removeId){
                    it.remove();
                }
            }

            synchronized (timeWindow){
                timeWindow = TimeUtil.raiseTimeId(removeId);
            }
        }
    }
}
