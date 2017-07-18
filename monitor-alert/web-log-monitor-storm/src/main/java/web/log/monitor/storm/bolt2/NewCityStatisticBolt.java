package web.log.monitor.storm.bolt2;

import org.apache.storm.shade.com.google.common.collect.Maps;
import web.log.monitor.common.spring.SpringContextUtil;
import web.log.monitor.common.time.TimeUtil;
import web.log.monitor.dao.dao.CityErrorStatisticDao;
import web.log.monitor.dao.dao.LocErrorStatisticDao;
import web.log.monitor.dao.entity.CityErrorStatisticDo;
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
public class NewCityStatisticBolt extends AbstractStatisticBolt{

    private static Map<String,StatusEntry> cityCountMap = Maps.newConcurrentMap();

    private static Long timeWindow = 0L;

    @Override
    public void doStatistic(WebLog weblog) {
        CityErrorStatisticDao cityErrorStatisticDao = SpringContextUtil.getBean("cityErrorStatisticDao");

        long timeId = weblog.getLogTimeId();

        synchronized (timeWindow) {
            if (timeId < timeWindow) {
                return;
            }
            if (timeWindow == 0L) {
                timeWindow = timeId;
            }
        }

        StatusEntry thisEntry = new StatusEntry();
        if (weblog.getHttpCode().equalsIgnoreCase("200") || weblog.getHttpCode().equalsIgnoreCase("\"200\"")) {
            thisEntry.setNormalCount(1);
        } else {
            thisEntry.setErrorCount(1);
        }

        StatusEntry countEntry = cityCountMap.get(weblog.getCity());
        if (null != countEntry) {
            countEntry.append(thisEntry);
            cityCountMap.put(weblog.getCity(), countEntry);
        } else {
            cityCountMap.put(weblog.getCity(), thisEntry);
        }


        if (timeId > timeWindow) {

            Long removeId;
            synchronized (timeWindow) {
                removeId = timeWindow;
            }

            for (String key : cityCountMap.keySet()) {
                StatusEntry totalCount = cityCountMap.get(key);

                CityErrorStatisticDo domain = new CityErrorStatisticDo();
                domain.setTimeId(removeId);
                domain.setTimeBox(TimeUtil.getMinuteText(removeId));
                domain.setCity(key);
                domain.setErrorCount(totalCount.getErrorCount());
                domain.setNormalCount(totalCount.getNormalCount());
                cityErrorStatisticDao.saveOrUpdate(domain);
            }

            cityCountMap.clear();

            synchronized (timeWindow) {
                timeWindow = TimeUtil.raiseTimeId(removeId);
            }
        }
    }
}
