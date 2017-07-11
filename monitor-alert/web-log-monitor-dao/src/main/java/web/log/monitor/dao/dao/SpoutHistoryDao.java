package web.log.monitor.dao.dao;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/************************************************************
 * Copy Right Information : 
 * Project : ${ProjectName}
 * JDK version used : ${SDK}
 * Comments : 
 *
 * Modification history : 
 *
 * Sr *** Date      *** Modified By *** Why & What is modified
 * 1. *** 2017/7/11  *** fulongwen   *** Initial
 ***********************************************************/
@Repository("spoutHistoryDao")
public class SpoutHistoryDao extends BaseDao {

    public void save(String text) {

        String sql = "Insert into spout_history(Text,CreateTime) values(?,?)";
        Object[] arg = {text, LocalDateTime.now()};
        jdbcTemplate.update(sql, arg);
    }

}
