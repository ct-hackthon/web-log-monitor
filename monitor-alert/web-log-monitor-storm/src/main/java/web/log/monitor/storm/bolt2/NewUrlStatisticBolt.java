package web.log.monitor.storm.bolt2;

import org.apache.storm.shade.com.google.common.collect.Maps;
import web.log.monitor.common.spring.SpringContextUtil;
import web.log.monitor.common.time.TimeUtil;
import web.log.monitor.dao.dao.PidErrorStatisticDao;
import web.log.monitor.dao.dao.UrlErrorStatisticDao;
import web.log.monitor.dao.entity.PidErrorStatisticDo;
import web.log.monitor.dao.entity.UrlErrorStatisticDo;
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
public class NewUrlStatisticBolt extends AbstractStatisticBolt {
    private static Map<String,StatusEntry> urlCountMap = Maps.newConcurrentMap();

    private static Long timeWindow = 0L;

    @Override
    public void doStatistic(WebLog weblog) {
        UrlErrorStatisticDao urlErrorStatisticDao = SpringContextUtil.getBean("urlErrorStatisticDao");

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

        StatusEntry countEntry = urlCountMap.get(weblog.getReqUrl());
        if (null != countEntry) {
            countEntry.append(thisEntry);
            urlCountMap.put(weblog.getReqUrl(), countEntry);
        } else {
            urlCountMap.put(weblog.getReqUrl(), thisEntry);
        }


        if (timeId > timeWindow) {

            Long removeId;
            synchronized (timeWindow) {
                removeId = timeWindow;
            }

            for (String key : urlCountMap.keySet()) {
                StatusEntry totalCount = urlCountMap.get(key);

                UrlErrorStatisticDo domain = new UrlErrorStatisticDo();
                domain.setTimeId(removeId);
                domain.setTimeBox(TimeUtil.getMinuteText(removeId));
                domain.setUrl(key);
                domain.setErrorCount(totalCount.getErrorCount());
                domain.setNormalCount(totalCount.getNormalCount());
                urlErrorStatisticDao.saveOrUpdate(domain);
            }

            urlCountMap.clear();

            synchronized (timeWindow) {
                timeWindow = TimeUtil.raiseTimeId(removeId);
            }
        }
    }
}
