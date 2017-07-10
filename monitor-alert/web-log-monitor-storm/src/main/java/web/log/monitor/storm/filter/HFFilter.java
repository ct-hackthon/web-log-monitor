package web.log.monitor.storm.filter;

import org.apache.storm.trident.operation.BaseFilter;
import org.apache.storm.trident.tuple.TridentTuple;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystems;
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
 * 1. *** 2017/7/10 *** fulongwen        *** Initial
 ***********************************************************/
public class HFFilter extends AbstractBaseFilter {


    @Override
    protected String getResources() {
        return "/storm/filter/hf.data";
    }

    public static void main(String[] arg){
        HFFilter filter =  new HFFilter();
        System.out.println(filter.doFilter("test"));
    }
}
