package ibczy.logview.analysis;

import ibczy.logview.analysis.task.CountTask;
import ibczy.logview.util.TimeUtil;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by BC349 on 2018/7/11.
 */
public class Start {

    @Value("${analysis.tag}")
    private String tag;
    @Value("${analysis.rootPath}")
    private String rootPath;
    @Autowired
    private DataLoader dataLoader;
    public void start(){
        List<String> dates = TimeUtil.getBeforeDays(new Date(),6);
        String []tags = tag.split(",");
        ExecutorService pool = Executors.newFixedThreadPool(tags.length);
        /*for(String tag:tags){
            CountTask task = new CountTask(tag,dataLoader,rootPath,dates);
            pool.execute(task);
        }*/
    }
}
