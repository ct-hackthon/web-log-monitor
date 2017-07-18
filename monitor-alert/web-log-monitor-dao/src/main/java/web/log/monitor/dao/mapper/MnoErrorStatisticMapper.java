package web.log.monitor.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import web.log.monitor.dao.entity.MnoErrorStatisticDo;

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
public class MnoErrorStatisticMapper implements RowMapper<MnoErrorStatisticDo> {
    @Override
    public MnoErrorStatisticDo mapRow(ResultSet resultSet, int i) throws SQLException {

        MnoErrorStatisticDo domain = new MnoErrorStatisticDo();

        domain .setId(resultSet.getInt("Id"));
        domain .setTimeId(resultSet.getLong("TimeId"));
        domain .setTimeBox(resultSet.getString("TimeBox"));
        domain .setNormalCount(resultSet.getInt("NormalCount"));
        domain .setErrorCount(resultSet.getInt("ErrorCount"));
        domain .setMno(resultSet.getString("Mno"));

        return domain;
    }
}
