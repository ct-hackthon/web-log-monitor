package web.log.monitor.storm.filter;

import org.apache.storm.trident.operation.BaseFilter;
import org.apache.storm.trident.tuple.TridentTuple;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
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
 * 1. *** 2017/7/10 *** fulongwen *** Initial
 ***********************************************************/
public abstract class AbstractBaseFilter extends BaseFilter {


    @Override
    public boolean isKeep(TridentTuple tridentTuple) {
        String log  = (String)tridentTuple.get(0);

        return doFilter(log);
    }

    protected  abstract  String getResources();

    protected boolean doFilter(String content) {

        URL url = this.getClass().getResource(getResources());
        File file = new File(url.getFile());
        if (file.canRead()) {
            try {
                Path hf_list_path = file.toPath();
                Stream<String> hf_list = Files.lines(hf_list_path);

                return hf_list.noneMatch(x -> content.contains(x));
            } catch (IOException e) {
                e.printStackTrace();
                return true;
            }

        }

        return true;
    }
}
