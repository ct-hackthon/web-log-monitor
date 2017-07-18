package web.log.monitor.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import web.log.monitor.dao.entity.UrlErrorStatisticDo;

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
 * 1. *** 2017/7/13  *** fulongwen   *** Initial
 ***********************************************************/
public class UrlErrorStatisticMapper implements RowMapper<UrlErrorStatisticDo> {
    @Override
    public UrlErrorStatisticDo mapRow(ResultSet resultSet, int i) throws SQLException {

        UrlErrorStatisticDo domain = new UrlErrorStatisticDo();

        domain .setId(resultSet.getInt("Id"));
        domain .setTimeId(resultSet.getLong("TimeId"));
        domain .setTimeBox(resultSet.getString("TimeBox"));
        domain .setNormalCount(resultSet.getInt("NormalCount"));
        domain .setErrorCount(resultSet.getInt("ErrorCount"));
        domain .setUrl(resultSet.getString("Url"));

        return domain;
    }
}
