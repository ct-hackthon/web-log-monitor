package web.log.monitor.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import web.log.monitor.biz.AlertBizProcess;
import web.log.monitor.biz.DashBoardBizProcess;
import web.log.monitor.common.time.TimeUtil;
import web.log.monitor.web.vo.ResponseVo;

/************************************************************
 * Copy Right Information :
 * Project : ${ProjectName}
 * JDK version used : ${SDK}
 * Comments : 
 *
 * Modification history : 
 *
 * Sr *** Date      *** Modified By *** Why & What is modified
 * 1. *** 2017/7/12  *** fulongwen   *** Initial
 ***********************************************************/
@Controller
@RequestMapping("/dashboard")
public class DisplayContoller {



    private DashBoardBizProcess dashBoardBizProcess;

    private AlertBizProcess alertBizProcess;

    @Autowired
    public void setDashBoardBizProcess(DashBoardBizProcess dashBoardBizProcess) {
        this.dashBoardBizProcess = dashBoardBizProcess;
    }

    @Autowired
    public void setAlertBizProcess(AlertBizProcess alertBizProcess) {
        this.alertBizProcess = alertBizProcess;
    }

    @RequestMapping(value = "/ping")
    @ResponseBody
    public String ping(){
        return "hello world";
    }

    @RequestMapping(value = "/httpStatistic")
    @ResponseBody
    public ResponseVo httpStatistic(
            @RequestParam(name = "timeId") Long timeId){
        try{
            return ResponseVo.ofSuccess(dashBoardBizProcess.getHTTPStatisticLineChartDTO(timeId));
        }catch (Exception e){
            return ResponseVo.ofFail(e.getMessage());
        }
    }


    @RequestMapping(value = "/httpErrorBox")
    @ResponseBody
    public ResponseVo httpErrorBox(
            @RequestParam(name = "timeId") Long timeId){
        try{
            return ResponseVo.ofSuccess(dashBoardBizProcess.getHTTPErrorBoxDTO(timeId));
        }catch (Exception e){
            return ResponseVo.ofFail(e.getMessage());
        }
    }

    @RequestMapping(value = "/urlErrorStatistic")
    @ResponseBody
    public ResponseVo urlErrorStatistic(
            @RequestParam(name = "timeId") Long timeId){
        try{
            return ResponseVo.ofSuccess(dashBoardBizProcess.getUrlStatisticChartDTO(timeId));
        }catch (Exception e){
            return ResponseVo.ofFail(e.getMessage());
        }
    }

    @RequestMapping(value = "/httpCurrentCd")
    @ResponseBody
    public ResponseVo httpCurrentCd(
            @RequestParam(name = "timeId") Long timeId){
        try{
            return ResponseVo.ofSuccess(alertBizProcess.display(timeId));
        }catch (Exception e){
            return ResponseVo.ofFail(e.getMessage());
        }
    }


    @RequestMapping(value = "/cityErrorStatic")
    @ResponseBody
    public ResponseVo cityErrorStatic(
            @RequestParam(name = "timeId") Long timeId){
        try{
            return ResponseVo.ofSuccess(dashBoardBizProcess.getLocErrorStatisticDTO(timeId));
        }catch (Exception e){
            return ResponseVo.ofFail(e.getMessage());
        }
    }


    @RequestMapping(value = "/raiseTimeId")
    @ResponseBody
    public ResponseVo raiseTimeId(
            @RequestParam(name = "timeId") Long timeId){
        try{
            return ResponseVo.ofSuccess(TimeUtil.raiseTimeId(timeId));
        }catch (Exception e){
            return ResponseVo.ofFail(e.getMessage());
        }
    }
}
