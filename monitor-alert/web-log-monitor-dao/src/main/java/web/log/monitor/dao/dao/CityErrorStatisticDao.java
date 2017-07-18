package web.log.monitor.dao.dao;

import org.springframework.stereotype.Repository;
import web.log.monitor.dao.entity.CityErrorStatisticDo;
import web.log.monitor.dao.mapper.CityErrorStatisticMapper;

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
@Repository("cityErrorStatisticDao")
public class CityErrorStatisticDao extends BaseDao{

    public List<CityErrorStatisticDo> querycityErrorStatisticList(Long timeId, String city){

        String sql = "SELECT * FROM city_error_statistic WHERE TimeId = ? and city = ?";
        Object[] arg = {timeId,city};
        return jdbcTemplate.query(sql,arg,new CityErrorStatisticMapper());
    }

    public List<CityErrorStatisticDo> queryLocErrorStatisticListByLimit(Long timeId, Integer limi){

        String sql = "SELECT * FROM city_error_statistic WHERE TimeId <=?  ORDER BY TimeId  DESC limit ?";
        Object[] arg = {timeId,limi};
        return jdbcTemplate.query(sql,arg,new CityErrorStatisticMapper());
    }



    public void saveOrUpdate(CityErrorStatisticDo domain){

//        save(domain);
        List<CityErrorStatisticDo> list = querycityErrorStatisticList(domain.getTimeId(),domain.getCity());
        if(list==null||list.isEmpty()){
            save(domain);
        }else{
            update(domain);
        }
    }

    private void save(CityErrorStatisticDo domain){
        String sql = "Insert into city_error_statistic(NormalCount,ErrorCount,City,TimeBox,TimeId,CreateTime) values(?,?,?,?,?,?)";
        Object[] arg = {domain.getNormalCount(),domain.getErrorCount(),domain.getCity(),domain.getTimeBox(),domain.getTimeId(), LocalDateTime.now()};
        jdbcTemplate.update(sql, arg);
    }

    private void update(CityErrorStatisticDo domain){
        //TODO
    }


    public  List<CityErrorStatisticDo> listTopNErrorcity(Long timeId,int limit){
        String sql = "SELECT * FROM city_error_statistic WHERE TimeId  = ?  order by ErrorCount  DESC limit ?";
        Object[] arg = {timeId,limit};
        return jdbcTemplate.query(sql,arg,new CityErrorStatisticMapper());
    }


    public  List<CityErrorStatisticDo> listTopNErrorcity(Long timeId){
        String sql = "SELECT * FROM city_error_statistic WHERE TimeId  = ?  order by ErrorCount  DESC ";
        Object[] arg = {timeId};
        return jdbcTemplate.query(sql,arg,new CityErrorStatisticMapper());
    }
}
