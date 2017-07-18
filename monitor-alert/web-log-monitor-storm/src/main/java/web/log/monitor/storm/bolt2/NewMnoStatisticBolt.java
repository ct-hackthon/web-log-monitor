package web.log.monitor.storm.bolt2;

import org.apache.storm.shade.com.google.common.collect.Maps;
import web.log.monitor.common.spring.SpringContextUtil;
import web.log.monitor.common.time.TimeUtil;
import web.log.monitor.dao.dao.MnoErrorStatisticDao;
import web.log.monitor.dao.entity.MnoErrorStatisticDo;
import web.log.monitor.storm.entity.StatusEntry;
import web.log.monitor.storm.entity.WebLog;

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
 * 1. *** 2017/7/15  *** fulongwen   *** Initial
 ***********************************************************/
public class NewMnoStatisticBolt extends AbstractStatisticBolt {


    private static Map<String,StatusEntry> mnoCountMap = Maps.newConcurrentMap();

    private static Long timeWindow = 0L;

    @Override
    public void doStatistic(WebLog weblog) {
        MnoErrorStatisticDao mnoErrorStatisticDao = SpringContextUtil.getBean("mnoErrorStatisticDao");

        long timeId = weblog.getLogTimeId();

        synchronized (timeWindow){
            if(timeId<timeWindow){
                return ;
            }
            if(timeWindow == 0L){
                timeWindow = timeId;
            }
        }

        StatusEntry  thisEntry =  new StatusEntry();
        if(weblog.getHttpCode().equalsIgnoreCase("200")||weblog.getHttpCode().equalsIgnoreCase("\"200\"")){
            thisEntry.setNormalCount(1);
        }else{
            thisEntry.setErrorCount(1);
        }

        StatusEntry  countEntry = mnoCountMap.get(weblog.getMno());
//        LOGGER.info("url " + data.getReqUrl());
        if(null!=countEntry){
            countEntry.append(thisEntry);
            mnoCountMap.put(weblog.getMno(),countEntry);
        }else {
            mnoCountMap.put(weblog.getMno(),thisEntry);
        }



        if(timeId>timeWindow){

            Long removeId;
            synchronized (timeWindow){
                removeId =  timeWindow;
            }

            for(String key : mnoCountMap.keySet()){
                StatusEntry totalCount = mnoCountMap.get(key);

                MnoErrorStatisticDo domain = new MnoErrorStatisticDo();
                domain.setTimeId(removeId);
                domain.setTimeBox(TimeUtil.getMinuteText(removeId));
                domain.setMno(key);
                domain.setErrorCount(totalCount.getErrorCount());
                domain.setNormalCount(totalCount.getNormalCount());
                mnoErrorStatisticDao.saveOrUpdate(domain);
            }

            mnoCountMap.clear();

            synchronized (timeWindow){
                timeWindow = TimeUtil.raiseTimeId(removeId);
            }
        }

    }
}
