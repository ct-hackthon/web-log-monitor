package web.log.monitor.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import web.log.monitor.dao.entity.HttpCodeStatisticDo;
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
public class HttpCodeStatisticMapper implements RowMapper<HttpCodeStatisticDo> {
    @Override
    public HttpCodeStatisticDo mapRow(ResultSet resultSet, int i) throws SQLException {

        HttpCodeStatisticDo domain = new HttpCodeStatisticDo();

        domain .setId(resultSet.getInt("Id"));
        domain .setTimeId(resultSet.getLong("TimeId"));
        domain .setTimeBox(resultSet.getString("TimeBox"));
        domain .setCode(resultSet.getString("Code"));
        domain .setCount(resultSet.getInt("Count"));

        return domain;
    }
}
