package web.log.monitor.common.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/************************************************************
 * Copy Right Information : 
 * Project : ${ProjectName}
 * JDK version used : ${SDK}
 * Comments : 
 *
 * Modification history : 
 *
 * Sr *** Date      *** Modified By *** Why & What is modified
 * 1. *** 2017/7/11  *** fulongwen   *** Initial
 ***********************************************************/
public class SpringContextUtil implements ApplicationContextAware {


    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    public static synchronized  void init(){
        String context = "/spring/application-context-storm.xml";
        if (applicationContext==null){
            applicationContext =new ClassPathXmlApplicationContext(context);
        }


    }

    @SuppressWarnings("uncheck")
    public static <T> T getBean(String name){
        return (T)applicationContext.getBean(name);
    }
}
