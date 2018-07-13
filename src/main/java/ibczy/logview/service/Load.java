package ibczy.logview.service;

import ibczy.logview.analysis.DataLoader;
import ibczy.logview.analysis.task.LoadTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by BC349 on 2018/7/12.
 */
@Component
public class Load {

    @Value("${analysis.tag}")
    private String tag;
    @Autowired
    private DataLoader dataLoader;
    @PostConstruct
    public void load(){
        LoadTask task = new LoadTask(tag,dataLoader);
        Thread t = new Thread(task);
        t.start();
    }
}
