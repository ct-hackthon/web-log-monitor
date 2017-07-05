package web.log.monitor.dao.dao;

import org.springframework.stereotype.Repository;
import web.log.monitor.dao.entity.HttpErrorStatisticDo;
import web.log.monitor.dao.mapper.HttpErrorStatisticMapper;

/************************************************************
 * Copy Right Information : 
 * Project : ${ProjectName}
 * JDK version used : ${SDK}
 * Comments : 
 *
 * Modification history : 
 *
 * Sr *** Date      *** Modified By *** Why & What is modified
 * 1. *** 2017/7/4  *** fulw        *** Initial
 ***********************************************************/
@Repository("httpErrorStatisticDao")
public class HttpErrorStatisticDao extends BaseDao{

    public HttpErrorStatisticDo getHttpErrorStatistic(int id){

        String sql = "SELECT * FROM http_error_statistic WHERE Id = ?";
        Object[] arg = {id};
        return jdbcTemplate.queryForObject(sql,arg,new HttpErrorStatisticMapper());
    }


}
