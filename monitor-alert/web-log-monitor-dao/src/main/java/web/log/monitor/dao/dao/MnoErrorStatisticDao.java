package web.log.monitor.dao.dao;

import org.springframework.stereotype.Repository;
import web.log.monitor.dao.entity.MnoErrorStatisticDo;
import web.log.monitor.dao.mapper.MnoErrorStatisticMapper;

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
@Repository("mnoErrorStatisticDao")
public class MnoErrorStatisticDao extends BaseDao{

    public List<MnoErrorStatisticDo> queryMnoErrorStatisticList(Long timeId,String mno){

        String sql = "SELECT * FROM mno_error_statistic WHERE TimeId = ? and Mno = ?";
        Object[] arg = {timeId,mno};
        return jdbcTemplate.query(sql,arg,new MnoErrorStatisticMapper());
    }

    public List<MnoErrorStatisticDo> queryMnoErrorStatisticListByLimit(Long timeId, Integer limit){

        String sql = "SELECT * FROM mno_error_statistic WHERE TimeId <=?  ORDER BY TimeId  DESC limit ?";
        Object[] arg = {timeId,limit};
        return jdbcTemplate.query(sql,arg,new MnoErrorStatisticMapper());
    }



    public void saveOrUpdate(MnoErrorStatisticDo domain){
//        save(domain);
        List<MnoErrorStatisticDo> list = queryMnoErrorStatisticList(domain.getTimeId(),domain.getMno());
        if(list==null||list.isEmpty()){
        save(domain);
        }else{
            update(domain);
        }
    }

    private void save(MnoErrorStatisticDo domain){
        String sql = "Insert into mno_error_statistic(NormalCount,ErrorCount,Mno,TimeBox,TimeId,CreateTime) values(?,?,?,?,?,?)";
        Object[] arg = {domain.getNormalCount(),domain.getErrorCount(),domain.getMno(),domain.getTimeBox(),domain.getTimeId(), LocalDateTime.now()};
        jdbcTemplate.update(sql, arg);
    }

    private void update(MnoErrorStatisticDo domain){
        //TODO
    }


    public  List<MnoErrorStatisticDo> listTopNErrorMno(Long timeId,int limit){
        String sql = "SELECT * FROM mno_error_statistic WHERE TimeId  = ?  order by ErrorCount  DESC limit ?";
        Object[] arg = {timeId,limit};
        return jdbcTemplate.query(sql,arg,new MnoErrorStatisticMapper());
    }
}
