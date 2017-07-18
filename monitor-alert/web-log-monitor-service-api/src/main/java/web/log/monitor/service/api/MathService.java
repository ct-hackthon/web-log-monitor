package web.log.monitor.service.api;


import web.log.monitor.service.entity.SimpleLinearRegressionTraningSet;

import java.util.List;

public interface MathService {

//        void httpErrorPredict(HttpErrorStatisticData data);

        Double simpleLinearRegression(List<SimpleLinearRegressionTraningSet> list,double x);

        Double mean(double[] data);

        Double variance(double[] data);

        Double standardDeviation(double[] data);
}
