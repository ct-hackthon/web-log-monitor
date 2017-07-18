package web.log.monitor.storm.bolt2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.storm.shade.com.google.common.collect.Maps;
import web.log.monitor.common.spring.SpringContextUtil;
import web.log.monitor.common.time.TimeUtil;
import web.log.monitor.dao.dao.HttpErrorStatisticDao;
import web.log.monitor.dao.entity.HttpErrorStatisticDo;
import web.log.monitor.storm.entity.StatusEntry;
import web.log.monitor.storm.entity.WebLog;

import java.math.BigDecimal;
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
public class NewHttpStatisticBolt extends AbstractStatisticBolt {

    private static Logger LOGGER = LogManager.getLogger(NewHttpStatisticBolt.class.getName());

    private static Map<Long,StatusEntry> httpCountMap = Maps.newConcurrentMap();
    private static Long timeWindow = 0L;





    /**
     *  根据日志投递时间ID做聚合，
     * @param data
     */
    @Override
    public void doStatistic( WebLog data){

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

        StatusEntry thisEntry =  new StatusEntry();
        if(data.getHttpCode().equalsIgnoreCase("200")||data.getHttpCode().equalsIgnoreCase("\"200\"")){
            thisEntry.setNormalCount(1);
        }else{
            thisEntry.setErrorCount(1);
        }

        StatusEntry  countEntry = httpCountMap.get(timeId);
        if(null!=countEntry){
            countEntry.append(thisEntry);
            httpCountMap.put(timeId,countEntry);
        }else {
            httpCountMap.put(timeId,thisEntry);
        }


        if(timeId>timeWindow){

            Long removeId;
            synchronized (timeWindow){
                removeId =  timeWindow;
            }

            StatusEntry statusEntry = httpCountMap.get(removeId)==null? new StatusEntry():httpCountMap.get(removeId);
            HttpErrorStatisticDo domain = new HttpErrorStatisticDo();
            domain.setTimeId(removeId);
            domain.setNormalCount(statusEntry.getNormalCount());
            domain.setErrorCount(statusEntry.getErrorCount());

            domain.setTimeBox(TimeUtil.getMinuteText(removeId));


            try {
                BigDecimal err = BigDecimal.valueOf(statusEntry.getErrorCount());
                BigDecimal total   = BigDecimal.valueOf(statusEntry.getErrorCount()+statusEntry.getNormalCount());

                domain.setPercentage(err.divide(total,4).doubleValue());
            }catch (Exception e) {
                domain.setPercentage(0.0D);
            }




            httpErrorStatisticDao.saveOrUpdateHttpErrorStatistic(domain);


            httpCountMap.clear();

            synchronized (timeWindow){
                timeWindow = TimeUtil.raiseTimeId(removeId);
            }
        }
    }


}
