package web.log.monitor.service.impl;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import web.log.monitor.service.api.MLService;
import web.log.monitor.service.entity.HttpErrorStatisticData;
import web.log.monitor.service.entity.HttpErrorStatisticTrainSet;

import java.util.stream.Stream;

/************************************************************
 * Copy Right Information : 
 * Project : ${ProjectName}
 * JDK version used : ${SDK}
 * Comments : 
 *
 * Modification history : 
 *
 * Sr *** Date      *** Modified By *** Why & What is modified
 * 1. *** 2017/7/4 *** fulongwen *** Initial
 ***********************************************************/
public class MLServiceImpl implements MLService {


    public static void simple() {
        // TODO Auto-generated method stub
        double[][] data = { { 0.1, 0.2 }, {338.8, 337.4 }, {118.1, 118.2 },
                {888.0, 884.6 }, {9.2, 10.1 }, {228.1, 226.5 }, {668.5, 666.3 }, {998.5, 996.3 },
                {449.1, 448.6 }, {778.9, 777.0 }, {559.2, 558.2 }, {0.3, 0.4 }, {0.1, 0.6 }, {778.1, 775.5 },
                {668.8, 666.9 }, {339.3, 338.0 }, {448.9, 447.5 }, {10.8, 11.6 }, {557.7, 556.0 },
                {228.3, 228.1 }, {998.0, 995.8 }, {888.8, 887.6 }, {119.6, 120.2 }, {0.3, 0.3 },
                {0.6, 0.3 }, {557.6, 556.8 }, {339.3, 339.1 }, {888.0, 887.2 }, {998.5, 999.0 },
                {778.9, 779.0 }, {10.2, 11.1 }, {117.6, 118.3 }, {228.9, 229.2 }, {668.4, 669.1 },
                {449.2, 448.9 }, {0.2, 0.5 }
        };
        SimpleRegression regression = new SimpleRegression();
        for (int i = 0; i < data.length; i++) {
            regression.addData(data[i][1], data[i][0]);
        }
//        regression.predict()
        System.out.println("slope is "+regression.getSlope());
        System.out.println("slope std err is "+regression.getSlopeStdErr());
        System.out.println("number of observations is "+regression.getN());
        System.out.println("intercept is "+regression.getIntercept());
        System.out.println("std err intercept is "+regression.getInterceptStdErr());
        System.out.println("r-square is "+regression.getRSquare());
        System.out.println("SSR is "+regression.getRegressionSumSquares());
        System.out.println("MSE is "+regression.getMeanSquareError());
        System.out.println("SSE is "+regression.getSumSquaredErrors());
        System.out.println("predict(0) is "+regression.predict(0));
        System.out.println("predict(1) is "+regression.predict(1));

    }

    public static void regression() {
        // TODO Auto-generated method stub
        double[] y;
        double[][] x;
        y = new double[]{11.0, 12.0, 13.0, 14.0, 15.0, 16.0};
        x = new double[6][];
        x[0] = new double[]{1.0, 0, 0, 0, 0, 0};
        x[1] = new double[]{1.0, 2.0, 0, 0, 0, 0};
        x[2] = new double[]{1.0, 0, 3.0, 0, 0, 0};
        x[3] = new double[]{1.0, 0, 0, 4.0, 0, 0};
        x[4] = new double[]{1.0, 0, 0, 0, 5.0, 0};
        x[5] = new double[]{1.0, 0, 0, 0, 0, 6.0};
        System.out.println(x[0].length+"-----------");
        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
        regression.newSampleData(y, x);
        double[] betaHat = regression.estimateRegressionParameters();
        System.out.println("Estimates the regression parameters b:");
        print(betaHat);
        double[] residuals = regression.estimateResiduals();
        System.out.println("Estimates the residuals, ie u = y - X*b:");
        print(residuals);
        double vary = regression.estimateRegressandVariance();
        System.out.println("Returns the variance of the regressand Var(y):");
        System.out.println(vary);
        double[] erros = regression.estimateRegressionParametersStandardErrors();
        System.out.println("Returns the standard errors of the regression parameters:");
        print(erros);
        double[][] varb = regression.estimateRegressionParametersVariance();
    }

    private static void print(double[] v) {
        // TODO Auto-generated method stub
        for(int i=0;i<v.length;i++){
            System.out.print(v[i]+ " ");
        }
        System.out.println();
    }


    public static void main(String[] arg){
        MLServiceImpl.simple();
    }



    @Override
    public void httpErrorPredict(HttpErrorStatisticData data) {

        SimpleRegression regression = new SimpleRegression();
        Stream<HttpErrorStatisticTrainSet> stream = data.getTrainSet().stream();
        stream.forEach(x -> regression.addData(x.getX(),x.getY()));

        double predictValue = regression.predict(data.getX());
        data.setPredictValue(predictValue);


    }
}
