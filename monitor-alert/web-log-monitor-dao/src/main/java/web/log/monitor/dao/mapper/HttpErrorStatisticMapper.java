package web.log.monitor.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import web.log.monitor.dao.entity.HttpErrorStatisticDo;

import java.sql.ResultSet;
import java.sql.SQLException;

/************************************************************
 * Copy Right Information : 
 * Project : ${ProjectName}
 * JDK version used : ${SDK}
 * Comments : 
 *
 * Modification history : 
 *
 * Sr *** Date      *** Modified By *** Why & What is modified
 * 1. *** 2017/7/5 *** fulongwen *** Initial
 ***********************************************************/
public class HttpErrorStatisticMapper implements RowMapper<HttpErrorStatisticDo> {
    @Override
    public HttpErrorStatisticDo mapRow(ResultSet resultSet, int i) throws SQLException {

        HttpErrorStatisticDo domain = new HttpErrorStatisticDo();

        domain .setId(resultSet.getInt("Id"));
        domain .setTimeId(resultSet.getLong("TimeId"));
        domain .setTimeBox(resultSet.getString("TimeBox"));
        domain .setNormalCount(resultSet.getLong("NormalCount"));
        domain .setErrorCount(resultSet.getLong("ErrorCount"));
//        domain .setCreateTime(resultSet.getTime("CreateTime"));

        return domain;
    }
}
