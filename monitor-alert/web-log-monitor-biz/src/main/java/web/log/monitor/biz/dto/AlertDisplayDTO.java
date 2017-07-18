package web.log.monitor.biz.dto;

import lombok.Data;

/************************************************************
 * Copy Right Information : 
 * Project : ${ProjectName}
 * JDK version used : ${SDK}
 * Comments : 
 *
 * Modification history : 
 *
 * Sr *** Date      *** Modified By *** Why & What is modified
 * 1. *** 2017/7/14  *** fulongwen   *** Initial
 ***********************************************************/
@Data
public class AlertDisplayDTO {

    private int currentErrorCount;
    private double predictErrorCount;
    private double devation;
    private double variance;
    private double outerlier;
    private double meanErrorCount;


}
