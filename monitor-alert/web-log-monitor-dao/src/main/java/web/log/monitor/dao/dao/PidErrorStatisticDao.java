package web.log.monitor.dao.dao;

import org.springframework.stereotype.Repository;
import web.log.monitor.dao.entity.PidErrorStatisticDo;
import web.log.monitor.dao.mapper.PidErrorStatisticMapper;

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
 * 1. *** 2017/7/15  *** fulongwen   *** Initial
 ***********************************************************/
@Repository("pidErrorStatisticDao")
public class PidErrorStatisticDao extends BaseDao{

    public List<PidErrorStatisticDo> queryPidErrorStatisticList(Long timeId,String pid){

        String sql = "SELECT * FROM pid_error_statistic WHERE TimeId = ? and  Pid = ?";
        Object[] arg = {timeId,pid};
        return jdbcTemplate.query(sql,arg,new PidErrorStatisticMapper());
    }

    public List<PidErrorStatisticDo> queryPidErrorStatisticListByLimit(Long timeId, Integer limit){

        String sql = "SELECT * FROM pid_error_statistic WHERE TimeId <=?  ORDER BY TimeId  DESC limit ?";
        Object[] arg = {timeId,limit};
        return jdbcTemplate.query(sql,arg,new PidErrorStatisticMapper());
    }



    public void saveOrUpdate(PidErrorStatisticDo domain){
//        save(domain);
        List<PidErrorStatisticDo> list = queryPidErrorStatisticList(domain.getTimeId(),domain.getPid());
        if(list==null||list.isEmpty()){
        save(domain);
        }else{
            update(domain);
        }
    }

    private void save(PidErrorStatisticDo domain){
        String sql = "Insert into pid_error_statistic(NormalCount,ErrorCount,Pid,TimeBox,TimeId,CreateTime) values(?,?,?,?,?,?)";
        Object[] arg = {domain.getNormalCount(),domain.getErrorCount(),domain.getPid(),domain.getTimeBox(),domain.getTimeId(), LocalDateTime.now()};
        jdbcTemplate.update(sql, arg);
    }

    private void update(PidErrorStatisticDo domain){
        //TODO
    }


    public  List<PidErrorStatisticDo> listTopNErrorUrl(Long timeId,int limit){
        String sql = "SELECT * FROM pid_error_statistic WHERE TimeId  = ?  order by ErrorCount  DESC limit ?";
        Object[] arg = {timeId,limit};
        return jdbcTemplate.query(sql,arg,new PidErrorStatisticMapper());
    }
}
