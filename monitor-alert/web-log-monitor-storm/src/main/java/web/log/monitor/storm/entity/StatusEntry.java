package web.log.monitor.storm.entity;

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
public class StatusEntry {

    private Integer normalCount = 0;
    private Integer errorCount = 0;


    public void append(StatusEntry entry){
        this.normalCount = this.normalCount+entry.normalCount;
        this.errorCount = this.errorCount+entry.errorCount;
    }
}
