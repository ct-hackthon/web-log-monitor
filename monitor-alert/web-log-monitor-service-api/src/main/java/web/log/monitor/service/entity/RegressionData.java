package web.log.monitor.service.entity;


import lombok.Data;

import java.time.LocalDateTime;



@Data
public class RegressionData {


    private double actualValue;
    private double predictValue;

    private double deviation;
    private double standardDeviation;

    private boolean isException;

    private Long timeMinuteId;
    private String timeMinuteText;

}
