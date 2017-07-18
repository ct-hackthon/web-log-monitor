package web.log.monitor.dao.dao;

import org.springframework.stereotype.Repository;
import web.log.monitor.dao.entity.LocErrorStatisticDo;
import web.log.monitor.dao.mapper.LocErrorStatisticMapper;

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
@Repository
public class LocErrorStatisticDao extends BaseDao {


    public List<LocErrorStatisticDo> queryLocErrorStatisticList(Long timeId,String loc){

        String sql = "SELECT * FROM loc_error_statistic WHERE TimeId = ? and  loc = ?";
        Object[] arg = {timeId,loc};
        return jdbcTemplate.query(sql,arg,new LocErrorStatisticMapper());
    }

    public List<LocErrorStatisticDo> queryLocErrorStatisticListByLimit(Long timeId, Integer limi){

        String sql = "SELECT * FROM loc_error_statistic WHERE TimeId <=?  ORDER BY TimeId  DESC limit ?";
        Object[] arg = {timeId,limi};
        return jdbcTemplate.query(sql,arg,new LocErrorStatisticMapper());
    }



    public void saveOrUpdateHttpErrorStatistic(LocErrorStatisticDo domain){

//        save(domain);
        List<LocErrorStatisticDo> list = queryLocErrorStatisticList(domain.getTimeId(),domain.getLoc());
        if(list==null||list.isEmpty()){
            save(domain);
        }else{
            update(domain);
        }
    }

    private void save(LocErrorStatisticDo domain){
        String sql = "Insert into loc_error_statistic(NormalCount,ErrorCount,Loc,TimeBox,TimeId,CreateTime) values(?,?,?,?,?,?)";
        Object[] arg = {domain.getNormalCount(),domain.getErrorCount(),domain.getLoc(),domain.getTimeBox(),domain.getTimeId(), LocalDateTime.now()};
        jdbcTemplate.update(sql, arg);
    }

    private void update(LocErrorStatisticDo domain){
        //TODO
    }



    public List<LocErrorStatisticDo> queryLocErrorStatisticList(Long timeId){

        String sql = "SELECT * FROM loc_error_statistic WHERE TimeId = ? ";
        Object[] arg = {timeId};
        return jdbcTemplate.query(sql,arg,new LocErrorStatisticMapper());
    }
}
