package web.log.monitor.storm.bolt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.storm.shade.com.google.common.collect.Maps;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.tuple.Tuple;
import web.log.monitor.common.spring.SpringContextUtil;
import web.log.monitor.common.time.TimeUtil;
import web.log.monitor.dao.dao.UrlErrorStatisticDao;
import web.log.monitor.dao.entity.HttpErrorStatisticDo;
import web.log.monitor.dao.entity.UrlErrorStatisticDo;
import web.log.monitor.storm.entity.StatusEntry;
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
 * 1. *** 2017/7/13  *** fulongwen   *** Initial
 ***********************************************************/
public class UrlErrorStatisticBolt extends BaseBolt{

    private static Logger LOGGER = LogManager.getLogger(UrlErrorStatisticBolt.class.getName());

    private static Map<Long,Map<String,StatusEntry>> urlCountMap = Maps.newConcurrentMap();
    private static Map<String,StatusEntry> newUrlCountMap = Maps.newConcurrentMap();

    private static Long timeWindow = 0L;


    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {

//         LOGGER.info("Url Error Statistic Bold receive data");

        try {
            if (tuple != null) {
                for (int index = 0; index < tuple.size(); index++) {
                    WebLog data = (WebLog) tuple.getValue(index);
                    doStatistic3(data);
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
    private void doStatistic( WebLog data) {
        LOGGER.info("Url Error Statistic do statitis");
        UrlErrorStatisticDao urlErrorStatisticDao = SpringContextUtil.getBean("urlErrorStatisticDao");
//        UrlErrorStatisticDao urlErrorStatisticDao = SpringContextUtil.getBean("urlErrorStatisticDao");
        long timeId = data.getLogTimeId();

        synchronized (timeWindow) {
            if (timeId < timeWindow) {
                return;
            }
            if (timeWindow == 0L) {
                timeWindow = timeId;
            }
        }

        LOGGER.info("timeId : " + timeId);
        LOGGER.info("TimeWindow : " + timeWindow);

        StatusEntry  thisEntry =  new StatusEntry();
        if(data.getHttpCode().equalsIgnoreCase("200")||data.getHttpCode().equalsIgnoreCase("\"200\"")){
            thisEntry.setNormalCount(1);
        }{
            thisEntry.setErrorCount(1);
        }

         Map<String,StatusEntry> urlMap = urlCountMap.get(timeId);
         if(urlMap==null){
             urlMap = Maps.newHashMap();
             urlCountMap.put(timeId,urlMap);
         }

         StatusEntry  countEntry = urlMap.get(data.getReqUrl());
         if(null!=countEntry){
             countEntry.append(thisEntry);
             urlMap.put(data.getReqUrl(),countEntry);
         }else {
             urlMap.put(data.getReqUrl(),thisEntry);
         }

        LOGGER.info("locCountMap size : " + urlCountMap.keySet().size());

        if(urlCountMap.keySet().size()>30){

            Long removeId;
            synchronized (timeWindow){
                removeId =  timeWindow;
            }

            Map<String,StatusEntry> toSaveMap = urlCountMap.get(removeId);

            if(toSaveMap!=null){
                for(String url :toSaveMap.keySet()){
                    StatusEntry statusEntry = toSaveMap.get(url)==null? new StatusEntry():toSaveMap.get(url);
                    UrlErrorStatisticDo domain = new UrlErrorStatisticDo();
                    domain.setTimeId(removeId);
                    domain.setNormalCount(statusEntry.getNormalCount());
                    domain.setErrorCount(statusEntry.getErrorCount());
                    domain.setUrl(url);
                    domain.setTimeBox(TimeUtil.getMinuteText(removeId));
                    urlErrorStatisticDao.saveOrUpdate(domain);
                }

            }

            Iterator<Map.Entry<Long, Map<String,StatusEntry>>> it = urlCountMap.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<Long, Map<String,StatusEntry>> entry = it.next();
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
    private void doStatistic3(WebLog data){

        UrlErrorStatisticDao urlErrorStatisticDao = SpringContextUtil.getBean("urlErrorStatisticDao");

        long timeId = data.getLogTimeId();

        synchronized (timeWindow){

            if(timeId<timeWindow){
                return ;
            }

            if(timeWindow == 0L){
                timeWindow = timeId;
            }
        }

        StatusEntry  thisEntry =  new StatusEntry();
        if(data.getHttpCode().equalsIgnoreCase("200")||data.getHttpCode().equalsIgnoreCase("\"200\"")){
            thisEntry.setNormalCount(1);
        }else{
            thisEntry.setErrorCount(1);
        }

        StatusEntry  countEntry = newUrlCountMap.get(data.getReqUrl());
//        LOGGER.info("url " + data.getReqUrl());
        if(null!=countEntry){
            countEntry.append(thisEntry);
            newUrlCountMap.put(data.getReqUrl(),countEntry);
        }else {
            newUrlCountMap.put(data.getReqUrl(),thisEntry);
        }



        if(timeId>timeWindow){

            LOGGER.info("Close Windows");
            Long removeId;
            synchronized (timeWindow){
                removeId =  timeWindow;
            }

//            LOGGER.info("newUrlCountMap size " + newUrlCountMap.size() );


            for(String iterUrl : newUrlCountMap.keySet()){

//                LOGGER.info("save iterUrl " + iterUrl);


                StatusEntry totalCount = newUrlCountMap.get(iterUrl);
//                LOGGER.info("StatusEntry " + totalCount);
                UrlErrorStatisticDo domain = new UrlErrorStatisticDo();
                domain.setTimeId(removeId);
                domain.setTimeBox(TimeUtil.getMinuteText(removeId));
                domain.setUrl(iterUrl);
                domain.setErrorCount(totalCount.getErrorCount());
                domain.setNormalCount(totalCount.getNormalCount());

                urlErrorStatisticDao.saveOrUpdate(domain);
            }

            newUrlCountMap.clear();

//            Iterator<Map.Entry<String, StatusEntry>> it = newUrlCountMap.entrySet().iterator();
//            while(it.hasNext()){
//                Map.Entry<String, StatusEntry> entry = it.next();
//                String url = entry.getKey();
//                StatusEntry totalCount = entry.getValue();
//
//                UrlErrorStatisticDo domain = new UrlErrorStatisticDo();
//                domain.setTimeId(removeId);
//                domain.setTimeBox(TimeUtil.getMinuteText(removeId));
//                domain.setUrl(url);
//                domain.setErrorCount(totalCount.getErrorCount());
//                domain.setNormalCount(totalCount.getNormalCount());
//
//                urlErrorStatisticDao.saveOrUpdateHttpErrorStatistic(domain);
//
//                it.remove();
//            }

            synchronized (timeWindow){
                timeWindow = TimeUtil.raiseTimeId(removeId);
            }
        }

    }
}
