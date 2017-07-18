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
 * 1. *** 2017/7/13  *** fulongwen   *** Initial
 ***********************************************************/
@Data
public class HttpCodeStatisticDo {

    private Integer id;
    private Integer count;
    private String code;
    private String timeBox;
    private Long  timeId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
