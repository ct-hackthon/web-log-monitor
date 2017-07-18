package web.log.monitor.dao.dao;

import org.springframework.stereotype.Repository;
import web.log.monitor.dao.entity.HttpErrorStatisticDo;
import web.log.monitor.dao.mapper.HttpErrorStatisticMapper;

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
 * 1. *** 2017/7/4  *** fulongwen        *** Initial
 ***********************************************************/
@Repository("httpErrorStatisticDao")
public class HttpErrorStatisticDao extends BaseDao{

    public HttpErrorStatisticDo getHttpErrorStatistic(int id){

        String sql = "SELECT * FROM http_error_statistic WHERE Id = ?";
        Object[] arg = {id};
        return jdbcTemplate.queryForObject(sql,arg,new HttpErrorStatisticMapper());
    }

    public HttpErrorStatisticDo getHttpErrorStatistic(Long timeId){

        String sql = "SELECT * FROM http_error_statistic WHERE TimeId = ?";
        Object[] arg = {timeId};
        return jdbcTemplate.queryForObject(sql,arg,new HttpErrorStatisticMapper());
    }


    public List<HttpErrorStatisticDo> queryHttpErrorStatisticList(Long timeIdMin, Long timeIdMax){

        String sql = "SELECT * FROM http_error_statistic WHERE TimeId > ? and TimeId < ?";
        Object[] arg = {timeIdMin,timeIdMax};
        return jdbcTemplate.query(sql,arg,new HttpErrorStatisticMapper());
    }

    public List<HttpErrorStatisticDo> queryHttpErrorStatisticList(Long timeId){

        String sql = "SELECT * FROM http_error_statistic WHERE TimeId = ?";
        Object[] arg = {timeId};
        return jdbcTemplate.query(sql,arg,new HttpErrorStatisticMapper());
    }

    public List<HttpErrorStatisticDo> queryHttpErrorStatisticListByLimit(Long timeId,Integer limi){

        String sql = "SELECT * FROM http_error_statistic WHERE TimeId < ?  ORDER BY TimeId  DESC limit ?";
        Object[] arg = {timeId,limi};
        return jdbcTemplate.query(sql,arg,new HttpErrorStatisticMapper());
    }


    public void saveOrUpdateHttpErrorStatistic(HttpErrorStatisticDo domain){

//        save(domain);
        List<HttpErrorStatisticDo> list = queryHttpErrorStatisticList(domain.getTimeId());

        if(list==null||list.isEmpty()){
            save(domain);
        }else{
            update(domain);
        }
    }

    private void save(HttpErrorStatisticDo domain){
        String sql = "Insert into http_error_statistic(NormalCount,ErrorCount,Percentage,TimeBox,TimeId,CreateTime) values(?,?,?,?,?,?)";
        Object[] arg = {domain.getNormalCount(),domain.getErrorCount(),domain.getPercentage(),domain.getTimeBox(),domain.getTimeId(), LocalDateTime.now()};
        jdbcTemplate.update(sql, arg);
    }

    private void update(HttpErrorStatisticDo domain){
        //TODO
    }
}
