package ibczy.logview.service;

import ibczy.logview.analysis.task.SchedulerJob;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created by BC349 on 2018/7/12.
 */
public class SchedulerStarter {

    private final String cron = "30 00 0 * * ?";//每天零点零分30秒执行任务

    public void start() throws Exception{
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        JobDetail jobDetail = new JobDetail("count_new_day",Scheduler.DEFAULT_GROUP, SchedulerJob.class);
        CronTrigger cronTrigger = new CronTrigger("cronTrigger",Scheduler.DEFAULT_GROUP,cron);
        scheduler.scheduleJob(jobDetail,cronTrigger);
        scheduler.start();
    }
}
