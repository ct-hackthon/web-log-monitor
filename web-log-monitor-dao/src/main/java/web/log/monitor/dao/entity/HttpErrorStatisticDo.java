package web.log.monitor.dao.entity;

import lombok.Data;

import java.time.LocalDateTime;

/************************************************************
 * Copy Right Information : 
 * Project : ${ProjectName}
 * JDK version used : ${SDK}
 * Comments : 
 *
 * Modification history : 
 *
 * Sr *** Date      *** Modified By *** Why & What is modified
 * 1. *** 2017/7/4 *** fulw         *** Initial
 ***********************************************************/
@Data
public class HttpErrorStatisticDo {

    private double normalCount;
    private double errorCount;
    private LocalDateTime timeBox;

}