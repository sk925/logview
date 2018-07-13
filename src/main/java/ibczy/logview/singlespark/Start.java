package ibczy.logview.singlespark;

import ibczy.logview.analysis.task.CountTask;
import ibczy.logview.service.SchedulerStarter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by BC349 on 2018/7/12.
 * spark统计程序入口
 */
public class Start {
    public static final String root = "hdfs://10.1.1.11:8020//ibczy/reader/book.[TAG].log/log-reader-book-[TAGB]-DATE.txt";
    public static final String tag = "click,read.time,in.bookshelf,subscribe";
    public static ExecutorService pool;
    public static void main(String []args){
        String tags[] = tag.split(",");
        pool = Executors.newFixedThreadPool(tags.length);
        for(String t:tags){
            CountTask task = new CountTask(t,root);
            pool.execute(task);
        }
        //启动定时任务，每天的零点刷新数据。将最新一天的数据量加入到监控
        SchedulerStarter starter = new SchedulerStarter();
        try {
            starter.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
