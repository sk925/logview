package ibczy.logview.analysis.task;

import ibczy.logview.singlespark.Start;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by BC349 on 2018/7/12.
 * 定时启动加载最近一天数据的线程
 */
public class SchedulerJob implements Job{

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String []tags = Start.tag.split(",");
        String rootPath = Start.root;
        System.err.println("定时任务启动");
        for(String tag:tags){
            CountNewDayTask task = new CountNewDayTask(tag,rootPath);
            Start.pool.execute(task);
        }

    }
}
