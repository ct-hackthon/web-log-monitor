package web.log.monitor.integration.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

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
@Component
public class Producer {

    private JmsTemplate jmsQueueTemplate;

    private Destination destination;

    public void sendMessage(final String message) {
//        System.out.println("---------------生产者发送消息-----------------");
//        System.out.println("---------------生产者发了一个消息：" + message);
        jmsQueueTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
    }


    @Autowired
    @Qualifier("jmsQueueTemplate")
    public void setJmsQueueTemplate(JmsTemplate jmsQueueTemplate) {
        this.jmsQueueTemplate = jmsQueueTemplate;
    }

    @Autowired
    public void setDestination(Destination destination) {
        this.destination = destination;
    }
}
