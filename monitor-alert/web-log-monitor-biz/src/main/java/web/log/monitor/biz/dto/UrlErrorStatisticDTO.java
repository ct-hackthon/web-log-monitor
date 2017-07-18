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
 * 1. *** 2017/7/13  *** fulongwen   *** Initial
 ***********************************************************/
@Data
public class UrlErrorStatisticDTO {

    private int[] normalCount;
    private int[] errorCount;
    private String[] url;


}
