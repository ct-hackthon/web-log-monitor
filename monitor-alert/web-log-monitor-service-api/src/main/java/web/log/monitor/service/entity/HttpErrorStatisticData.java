package web.log.monitor.service.entity;


import lombok.Data;

import java.util.List;


@Data
public class HttpErrorStatisticData {


    private List<HttpErrorStatisticTrainSet> trainSet;

    private double x;
    private double actualValue;
    private double predictValue;

    private double deviation;
    private double standardDeviation;

    private boolean isException;

    private Long timeMinuteId;
    private String timeMinuteText;

}
