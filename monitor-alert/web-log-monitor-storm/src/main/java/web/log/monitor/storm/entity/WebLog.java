package web.log.monitor.storm.entity;

import lombok.Data;

import java.io.Serializable;

/************************************************************
 * Copy Right Information : 
 * Project : ${ProjectName}
 * JDK version used : ${SDK}
 * Comments : 
 *
 *     loc$httpCode$reqUrl$reqTime$mno$resSize$netType$uid$pid$hf$X-Date$lat$lng$host$reqTarget$minute
 * Modification history : 
 *
 * Sr *** Date      *** Modified By *** Why & What is modified
 * 1. *** 2017/7/10 *** fulongwen   *** Initial
 ***********************************************************/
@Data
public class WebLog implements Serializable {

    private String reqSize;   //网络请求大小
    private String netType;	//网络类型 2G 3G 4G wifi cellular unknown
    private String httpCode;  //HTTP请求状态码
    private String mno;	    //运营商 移动，联通
    private String reqUrl; 	 //请求的URL
    private String reqTime;	 //收到网络响应的时间 responseTime-startTime
    private String hf;	    //异常原因
//    private String endTime;  //结束时间
//    private String startTime;	//开始时间
//    private String connTime;	//建立网络连接的时间

    private String resSize;	 //收到响应数据大小

    private String lt;		//经纬度模拟标识 lt：是否模拟经纬度0是正常，1是模拟的
    private String lat;		//纬度
    private String lgt;		//经度
    private String pid;		//产品号
    private String uid;	    //userId
    private String loc;
    private String XDate;    //2017-04-01 13:00:00.385
    private String host;
    private String reqTarget;
    private String minute;
    private String city;


    private Long logTimeId;     //日志投递时间id
//    private Long timeId;        // 日志时间id
    private String logTimeIdStr;
}
