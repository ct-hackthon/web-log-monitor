package web.log.monitor.storm.bolt2;

import org.apache.storm.shade.com.google.common.collect.Maps;
import web.log.monitor.common.spring.SpringContextUtil;
import web.log.monitor.common.time.TimeUtil;
import web.log.monitor.dao.dao.HttpCodeStatisticDao;
import web.log.monitor.dao.dao.LocErrorStatisticDao;
import web.log.monitor.dao.entity.HttpCodeStatisticDo;
import web.log.monitor.dao.entity.LocErrorStatisticDo;
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
public class NewHttpCodeStatisticBolt extends AbstractStatisticBolt {

    private static Map<String,Integer> httpCodeCountMap = Maps.newConcurrentMap();

    private static Long timeWindow = 0L;

    @Override
    public void doStatistic(WebLog weblog) {
        HttpCodeStatisticDao httpCodeStatisticDao = SpringContextUtil.getBean("httpCodeStatisticDao");

        long timeId = weblog.getLogTimeId();

        synchronized (timeWindow) {
            if (timeId < timeWindow) {
                return;
            }
            if (timeWindow == 0L) {
                timeWindow = timeId;
            }
        }



        Integer countEntry = httpCodeCountMap.get(weblog.getHttpCode());
        if (null != countEntry) {
            countEntry =  countEntry + 1;
            httpCodeCountMap.put(weblog.getHttpCode(), countEntry);
        } else {
            httpCodeCountMap.put(weblog.getHttpCode(), 1);
        }


        if (timeId > timeWindow) {

            Long removeId;
            synchronized (timeWindow) {
                removeId = timeWindow;
            }

            for (String key : httpCodeCountMap.keySet()) {
                Integer  totalCount = httpCodeCountMap.get(key);

                HttpCodeStatisticDo domain = new HttpCodeStatisticDo();
                domain.setTimeId(removeId);
                domain.setTimeBox(TimeUtil.getMinuteText(removeId));
                domain.setCode(key);
                domain.setCount(totalCount);
                httpCodeStatisticDao.saveOrUpdateHttpErrorStatistic(domain);
            }

            httpCodeCountMap.clear();

            synchronized (timeWindow) {
                timeWindow = TimeUtil.raiseTimeId(removeId);
            }
        }
    }
}
