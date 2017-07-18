package web.log.monitor.storm.bolt;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.storm.shade.com.google.common.collect.Maps;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.tuple.Tuple;
import web.log.monitor.common.spring.SpringContextUtil;
import web.log.monitor.common.time.TimeUtil;
import web.log.monitor.dao.dao.LocErrorStatisticDao;
import web.log.monitor.dao.entity.LocErrorStatisticDo;
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
public class LocErrorStatisticBolt extends BaseBolt{

    private static Logger LOGGER = LogManager.getLogger(LocErrorStatisticBolt.class.getName());

    private static Map<Long,Map<String,StatusEntry>> locCountMap = Maps.newConcurrentMap();
    private static Long timeWindow = 0L;

    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {

         LOGGER.info("Loc Error Statistic Bold receive data");

        try {
            if (tuple != null) {
                for (int index = 0; index < tuple.size(); index++) {
                    WebLog data = (WebLog) tuple.getValue(index);
                    doStatistic(data);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void doStatistic( WebLog data) {
        LOGGER.info("Loc Error Statistic do statitis");

        LocErrorStatisticDao locErrorStatisticDao = SpringContextUtil.getBean("locErrorStatisticDao");
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

         Map<String,StatusEntry> locMap = locCountMap.get(timeId);
         if(locMap==null){
             locMap = Maps.newHashMap();
             locCountMap.put(timeId,locMap);
         }

         StatusEntry  countEntry = locMap.get(data.getLoc());
         if(null!=countEntry){
             countEntry.append(thisEntry);
             locMap.put(data.getLoc(),countEntry);
         }else {
             locMap.put(data.getLoc(),thisEntry);
         }

        LOGGER.info("locCountMap size : " + locCountMap.keySet().size());

        if(locCountMap.keySet().size()>30){

            Long removeId;
            synchronized (timeWindow){
                removeId =  timeWindow;
            }

            Map<String,StatusEntry> locNormalMap = locCountMap.get(removeId);

            if(locNormalMap!=null){
                for(String loc :locNormalMap.keySet()){
                    StatusEntry statusEntry = locNormalMap.get(loc)==null? new StatusEntry():locNormalMap.get(loc);
                    LocErrorStatisticDo domain = new LocErrorStatisticDo();
                    domain.setTimeId(removeId);
                    domain.setNormalCount(statusEntry.getNormalCount());
                    domain.setErrorCount(statusEntry.getErrorCount());
                    domain.setLoc(loc);
                    domain.setTimeBox(TimeUtil.getMinuteText(removeId));
                    locErrorStatisticDao.saveOrUpdateHttpErrorStatistic(domain);
                }

            }

            Iterator<Map.Entry<Long, Map<String,StatusEntry>>> it = locCountMap.entrySet().iterator();
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
}
