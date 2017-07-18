package web.log.monitor.biz;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.log.monitor.biz.dto.HttpErrorBoxDTO;
import web.log.monitor.biz.dto.HttpErrorStatisticDTO;
import web.log.monitor.biz.dto.LocErrorStatisticDTO;
import web.log.monitor.biz.dto.UrlErrorStatisticDTO;
import web.log.monitor.common.time.TimeUtil;
import web.log.monitor.dao.dao.CityErrorStatisticDao;
import web.log.monitor.dao.dao.HttpErrorStatisticDao;
import web.log.monitor.dao.dao.LocErrorStatisticDao;
import web.log.monitor.dao.dao.UrlErrorStatisticDao;
import web.log.monitor.dao.entity.CityErrorStatisticDo;
import web.log.monitor.dao.entity.HttpErrorStatisticDo;
import web.log.monitor.dao.entity.LocErrorStatisticDo;
import web.log.monitor.dao.entity.UrlErrorStatisticDo;

import java.util.List;
import java.util.Map;

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
@Service("dashboardBizProcess")
public class DashBoardBizProcess {


    private HttpErrorStatisticDao httpErrorStatisticDao;
    private UrlErrorStatisticDao urlErrorStatisticDao;
    private LocErrorStatisticDao locErrorStatisticDao;
    private CityErrorStatisticDao cityErrorStatisticDao;

    @Autowired
    public void setHttpErrorStatisticDao(HttpErrorStatisticDao httpErrorStatisticDao) {
        this.httpErrorStatisticDao = httpErrorStatisticDao;
    }

    @Autowired
    public void setUrlErrorStatisticDao(UrlErrorStatisticDao urlErrorStatisticDao) {
        this.urlErrorStatisticDao = urlErrorStatisticDao;
    }
    @Autowired
    public void setLocErrorStatisticDao(LocErrorStatisticDao locErrorStatisticDao) {
        this.locErrorStatisticDao = locErrorStatisticDao;
    }
    @Autowired
    public void setCityErrorStatisticDao(CityErrorStatisticDao cityErrorStatisticDao) {
        this.cityErrorStatisticDao = cityErrorStatisticDao;
    }

    public HttpErrorStatisticDTO getHTTPStatisticLineChartDTO(Long timeId){

        HttpErrorStatisticDTO dto =  new HttpErrorStatisticDTO();



        List<HttpErrorStatisticDo> list = httpErrorStatisticDao.queryHttpErrorStatisticListByLimit(timeId,300);


        if(list!=null){
            int[] normalData = list.stream().mapToInt(HttpErrorStatisticDo::getNormalCount).sorted().toArray();
            int[] errorData = list.stream().mapToInt(HttpErrorStatisticDo::getErrorCount).sorted().toArray();
            String[] xAxisSeries = list.stream().map(HttpErrorStatisticDo::getTimeBox).sorted().toArray(String[]::new);


            dto.setNormalData(normalData);
            dto.setErrorData(errorData);
            dto.setXAxis(xAxisSeries);
        }



        return dto;
    }

    public UrlErrorStatisticDTO getUrlStatisticChartDTO(Long timeId){
                UrlErrorStatisticDTO dto = new UrlErrorStatisticDTO();

        List<UrlErrorStatisticDo> list = urlErrorStatisticDao.listTopNErrorUrl(timeId,10);

        int[] errorCount = list.stream().mapToInt(x-> x.getErrorCount()).toArray();
        int[] normalCount = list.stream().mapToInt(x->x.getNormalCount()/100).toArray();
        String[] urlCatagory = list.stream().map(x->x.getUrl()).toArray(String[]::new);
        dto.setErrorCount(errorCount);
        dto.setNormalCount(normalCount);
        dto.setUrl(urlCatagory);
        return dto;
    }

    public HttpErrorBoxDTO getHTTPErrorBoxDTO(Long timeId){


        HttpErrorBoxDTO dto = new HttpErrorBoxDTO();

        HttpErrorStatisticDo domain = httpErrorStatisticDao.getHttpErrorStatistic(timeId);

        List<HttpErrorStatisticDo> list = httpErrorStatisticDao.queryHttpErrorStatisticListByLimit(timeId,60);


        if(list!=null){
            int[] errorData = list.stream().mapToInt(HttpErrorStatisticDo::getErrorCount).sorted().toArray();

            dto.setErrorSeries(errorData);
            dto.setData(new int[]{domain.getErrorCount()});
            dto.setXAxis(TimeUtil.getMinuteText(timeId));

        }

        return dto;


    }




    public LocErrorStatisticDTO getLocErrorStatisticDTO(Long timeId){

        LocErrorStatisticDTO dto = new LocErrorStatisticDTO();
         List<LocErrorStatisticDTO.SS> list2 = Lists.newArrayList();
        List<CityErrorStatisticDo> list = cityErrorStatisticDao.listTopNErrorcity(timeId);
        for(CityErrorStatisticDo dot : list){

            LocErrorStatisticDTO.SS ss = new LocErrorStatisticDTO.SS();
            ss.setName(dot.getCity());
            ss.setValue(String.valueOf(dot.getErrorCount()*200));
            list2.add(ss);
        }
        dto.setList(list2);
        return dto;
    }

}
