package web.log.monitor.dao.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import web.log.monitor.BaseTest;

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
public class HttpErrorStatisticDaoTest extends BaseTest {

    @Autowired
    private HttpErrorStatisticDao httpErrorStatisticDao;

    @Test
    public void testGetHttpErrorStatistic(){
        httpErrorStatisticDao.getHttpErrorStatistic(1);
    }
}
