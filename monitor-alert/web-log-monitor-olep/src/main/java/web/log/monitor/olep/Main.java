package web.log.monitor.olep;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.apache.xbean.spring.context.SpringApplicationContext;
import org.springframework.context.ApplicationContext;
import web.log.monitor.integration.jms.Producer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/************************************************************
 * Copy Right Information : 
 * Project : ${ProjectName}
 * JDK version used : ${SDK}
 * Comments : 
 *
 * Modification history : 
 *
 * Sr *** Date      *** Modified By *** Why & What is modified
 * 1. *** 2017/7/10 *** fulongwen   *** Initial
 ***********************************************************/
public class Main {


    public static void main(String[] arg){

        ApplicationContext context = new ClassPathXmlApplicationContext("/spring/application-context-integration.xml");

        System.out.println(context.getApplicationName());
        Producer producer = (Producer)context.getBean("producer");
        System.out.println("ffff");

        String s = "D:\\Project\\hackthon-ios-monitor\\ref\\iosMonitorLog.log.2017-04-01-00";

        Path log = Paths.get(s);
        try {
            Stream<String> log_lines = Files.lines(log);
            log_lines.forEach(x->producer.sendMessage(x));
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
