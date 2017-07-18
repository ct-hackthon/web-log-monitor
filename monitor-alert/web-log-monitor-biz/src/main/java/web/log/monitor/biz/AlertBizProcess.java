package web.log.monitor.biz;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.log.monitor.biz.dto.AlertDisplayDTO;
import web.log.monitor.dao.dao.HttpErrorStatisticDao;
import web.log.monitor.dao.entity.HttpErrorStatisticDo;
import web.log.monitor.service.api.MathService;
import web.log.monitor.service.entity.SimpleLinearRegressionTraningSet;

import java.util.List;

/************************************************************
 * Copy Right Information : 
 * Project : ${ProjectName}
 * JDK version used : ${SDK}
 * Comments : 
 *
 * Modification history : 
 *
 * Sr *** Date      *** Modified By *** Why & What is modified
 * 1. *** 2017/7/6 *** fulongwen *** Initial
 ***********************************************************/
@Service("alertBizProcess")
public class AlertBizProcess {

    private HttpErrorStatisticDao httpErrorStatisticDao;

    private MathService mathService;

    @Autowired
    public void setHttpErrorStatisticDao(HttpErrorStatisticDao httpErrorStatisticDao) {
        this.httpErrorStatisticDao = httpErrorStatisticDao;
    }

    @Autowired
    public void setMathService(MathService mathService) {
        this.mathService = mathService;
    }

    public AlertDisplayDTO display(Long timeId){

        AlertDisplayDTO dto = new AlertDisplayDTO();

        HttpErrorStatisticDo domain = httpErrorStatisticDao.getHttpErrorStatistic(timeId);
        List<HttpErrorStatisticDo> list = httpErrorStatisticDao.queryHttpErrorStatisticListByLimit(timeId,60);

        if(domain!=null){
            dto.setCurrentErrorCount(domain.getErrorCount());
        }

        List<SimpleLinearRegressionTraningSet> traningSets = Lists.newArrayList();
        if(list!=null){
            list.forEach(x->{
                SimpleLinearRegressionTraningSet traningSet = new SimpleLinearRegressionTraningSet();
                traningSet.setX(traningSets.size()+1);
                traningSet.setY(x.getErrorCount());
                traningSets.add(traningSet);
            });
        }

        double mean = mathService.mean(list.stream().mapToDouble(x->x.getErrorCount()).toArray());
        double variance = mathService.standardDeviation(list.stream().mapToDouble(x->x.getErrorCount()).toArray());
        double predictValue = mathService.simpleLinearRegression(traningSets,domain.getErrorCount());

        dto.setDevation(Double.valueOf(domain.getErrorCount())-predictValue);
        dto.setPredictErrorCount(predictValue);
        dto.setMeanErrorCount(mean);
        dto.setVariance(variance);
        dto.setOuterlier(2*variance);
        return dto;

    }
}
