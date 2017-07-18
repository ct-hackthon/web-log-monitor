package web.log.monitor.dao.dao;

import org.springframework.stereotype.Repository;
import web.log.monitor.dao.entity.UrlErrorStatisticDo;
import web.log.monitor.dao.mapper.UrlErrorStatisticMapper;

import java.time.LocalDateTime;
import java.util.List;

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
@Repository("urlErrorStatisticDao")
public class UrlErrorStatisticDao extends BaseDao {


    public List<UrlErrorStatisticDo> queryUrlErrorStatisticList(Long timeId, String url){

        String sql = "SELECT * FROM url_error_statistic WHERE TimeId = ? and url = ?";
        Object[] arg = {timeId,url};
        return jdbcTemplate.query(sql,arg,new UrlErrorStatisticMapper());
    }

    public List<UrlErrorStatisticDo> queryLocErrorStatisticListByLimit(Long timeId, Integer limi){

        String sql = "SELECT * FROM url_error_statistic WHERE TimeId <=?  ORDER BY TimeId  DESC limit ?";
        Object[] arg = {timeId,limi};
        return jdbcTemplate.query(sql,arg,new UrlErrorStatisticMapper());
    }



    public void saveOrUpdate(UrlErrorStatisticDo domain){

//        save(domain);
        List<UrlErrorStatisticDo> list = queryUrlErrorStatisticList(domain.getTimeId(),domain.getUrl());
        if(list==null||list.isEmpty()){
            save(domain);
        }else{
            update(domain);
        }
    }

    private void save(UrlErrorStatisticDo domain){
        String sql = "Insert into url_error_statistic(NormalCount,ErrorCount,Url,TimeBox,TimeId,CreateTime) values(?,?,?,?,?,?)";
        Object[] arg = {domain.getNormalCount(),domain.getErrorCount(),domain.getUrl(),domain.getTimeBox(),domain.getTimeId(), LocalDateTime.now()};
        jdbcTemplate.update(sql, arg);
    }

    private void update(UrlErrorStatisticDo domain){
        //TODO
    }


    public  List<UrlErrorStatisticDo> listTopNErrorUrl(Long timeId,int limit){
        String sql = "SELECT * FROM url_error_statistic WHERE TimeId  = ?  order by ErrorCount  DESC limit ?";
        Object[] arg = {timeId,limit};
        return jdbcTemplate.query(sql,arg,new UrlErrorStatisticMapper());
    }
}
