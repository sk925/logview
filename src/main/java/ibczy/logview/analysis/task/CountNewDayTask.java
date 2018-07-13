package ibczy.logview.analysis.task;

import java.util.Date;

/**
 * Created by BC349 on 2018/7/12.
 */
public class CountNewDayTask extends BaseTask{

    public CountNewDayTask(String tag, String rootPath){
        super(tag,rootPath);
    }

    @Override
    public void run() {

        while(true){
            System.err.println("开始统计最新一天的数据");
            String today = sf.format(new Date());
            synchronized (dataLoader.getDataMap(tag)){
                if(!dataLoader.getDates().contains(today) && !dataLoader.getDataMap(tag).containsKey(today)){
                    countTodayData(today);
                    persistCount(2);
                }                ;
            }

        }
    }
}
