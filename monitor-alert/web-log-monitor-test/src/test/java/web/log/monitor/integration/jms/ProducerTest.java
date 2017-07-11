package web.log.monitor.integration.jms;

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
 * 1. *** 2017/7/6 *** fulongwen *** Initial
 ***********************************************************/
public class ProducerTest extends BaseTest{


    @Autowired
    private Producer producer;

    @Test
    public void testSend() {
//        for (int i=0; i<2; i++) {
//            producer.sendMessage("你好，生产者！这是消息：" + (i+1));
//
//        }
    }
}
