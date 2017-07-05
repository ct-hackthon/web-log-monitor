package web.log.monitor.common;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/************************************************************
 * Copy Right Information : 
 * Project : ${ProjectName}
 * JDK version used : ${SDK}
 * Comments : 
 *
 * Modification history : 
 *
 * Sr *** Date      *** Modified By *** Why & What is modified
 * 1. *** 2017/7/5  *** fulw        *** Initial
 ***********************************************************/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:/spring/application-context-biz.xml",
})
public class BaseTest {
}
