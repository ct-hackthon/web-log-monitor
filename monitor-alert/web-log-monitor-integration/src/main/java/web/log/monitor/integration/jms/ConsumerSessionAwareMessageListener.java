package web.log.monitor.integration.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.SessionAwareMessageListener;

import javax.jms.*;

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
public class ConsumerSessionAwareMessageListener implements
        SessionAwareMessageListener<TextMessage> {



    public void onMessage(TextMessage message, Session session) throws JMSException {
        System.out.println("收到一条消息");
        System.out.println("消息内容是：" + message.getText());

    }

}