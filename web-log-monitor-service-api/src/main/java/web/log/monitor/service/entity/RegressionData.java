package web.log.monitor.service.entity;


import lombok.Data;

import java.time.LocalDateTime;



@Data
public class RegressionData {

    private String url;
    private double mileSeconds;
    private double actualValue;
    private double predictValue;
    private double standardDeviation;
    private boolean isException;
    private LocalDateTime dateTime;

}
