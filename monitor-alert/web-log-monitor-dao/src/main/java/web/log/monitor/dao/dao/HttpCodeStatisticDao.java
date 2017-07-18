package web.log.monitor.dao.dao;

import org.springframework.stereotype.Repository;
import web.log.monitor.dao.entity.HttpCodeStatisticDo;
import web.log.monitor.dao.mapper.HttpCodeStatisticMapper;

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
@Repository("httpCodeStatisticDao")
public class HttpCodeStatisticDao extends BaseDao {


    public List<HttpCodeStatisticDo> queryCodeErrorStatisticList(Long timeId,String Code){

        String sql = "SELECT * FROM http_code_statistic WHERE TimeId = ? and  Code = ?";
        Object[] arg = {timeId,Code};
        return jdbcTemplate.query(sql,arg,new HttpCodeStatisticMapper());
    }

    public List<HttpCodeStatisticDo> queryCodeErrorStatisticListByLimit(Long timeId, Integer limi){

        String sql = "SELECT * FROM http_code_statistic WHERE TimeId <=?  ORDER BY TimeId  DESC limit ?";
        Object[] arg = {timeId,limi};
        return jdbcTemplate.query(sql,arg,new HttpCodeStatisticMapper());
    }



    public void saveOrUpdateHttpErrorStatistic(HttpCodeStatisticDo domain){

//        save(domain);
        List<HttpCodeStatisticDo> list = queryCodeErrorStatisticList(domain.getTimeId(),domain.getCode());
        if(list==null||list.isEmpty()){
            save(domain);
        }else{
            update(domain);
        }
    }

    private void save(HttpCodeStatisticDo domain){
        String sql = "Insert into http_code_statistic(Count,Code,TimeBox,TimeId,CreateTime) values(?,?,?,?,?)";
        Object[] arg = {domain.getCount(),domain.getCode(),domain.getTimeBox(),domain.getTimeId(), LocalDateTime.now()};
        jdbcTemplate.update(sql, arg);
    }

    private void update(HttpCodeStatisticDo domain){
        //TODO
    }
}
