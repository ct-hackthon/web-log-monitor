package web.log.monitor.storm.bolt2;

import org.apache.storm.shade.com.google.common.collect.Maps;
import web.log.monitor.common.spring.SpringContextUtil;
import web.log.monitor.common.time.TimeUtil;
import web.log.monitor.dao.dao.PidErrorStatisticDao;
import web.log.monitor.dao.entity.PidErrorStatisticDo;
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
public class NewPidStatisticBolt extends AbstractStatisticBolt{

    private static Map<String,StatusEntry> pidCountMap = Maps.newConcurrentMap();

    private static Long timeWindow = 0L;

    @Override
    public void doStatistic(WebLog weblog) {
        PidErrorStatisticDao pidErrorStatisticDao = SpringContextUtil.getBean("pidErrorStatisticDao");

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

        StatusEntry  countEntry = pidCountMap.get(weblog.getPid());
        if(null!=countEntry){
            countEntry.append(thisEntry);
            pidCountMap.put(weblog.getPid(),countEntry);
        }else {
            pidCountMap.put(weblog.getPid(),thisEntry);
        }



        if(timeId>timeWindow){

            Long removeId;
            synchronized (timeWindow){
                removeId =  timeWindow;
            }

            for(String key : pidCountMap.keySet()){
                StatusEntry totalCount = pidCountMap.get(key);

                PidErrorStatisticDo domain = new PidErrorStatisticDo();
                domain.setTimeId(removeId);
                domain.setTimeBox(TimeUtil.getMinuteText(removeId));
                domain.setPid(key);
                domain.setErrorCount(totalCount.getErrorCount());
                domain.setNormalCount(totalCount.getNormalCount());
                pidErrorStatisticDao.saveOrUpdate(domain);
            }

            pidCountMap.clear();

            synchronized (timeWindow){
                timeWindow = TimeUtil.raiseTimeId(removeId);
            }
        }

    }
}
