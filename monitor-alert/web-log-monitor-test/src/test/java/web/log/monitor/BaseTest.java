package web.log.monitor;

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
 * 1. *** 2017/7/5  *** fulongwen        *** Initial
 ***********************************************************/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath:/spring/application-context-biz.xml",
        "classpath:/spring/application-context-dao.xml",
        "classpath:/spring/application-context-integration.xml",
        "classpath:/spring/application-context-integration-jms-broker.xml",
        "classpath:/spring/application-context-integration-jms-producer.xml",
        "classpath:/spring/application-context-integration-jms-consumer.xml",

})
public class BaseTest {
}
