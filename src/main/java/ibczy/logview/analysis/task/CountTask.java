package ibczy.logview.analysis.task;

import org.apache.spark.api.java.JavaRDD;
import java.util.Date;

/**
 * Created by BC349 on 2018/7/11.
 */
public class CountTask extends BaseTask{

    public CountTask(String tag, String rootPath){
        super(tag,rootPath);
    }

    @Override
    public void run() {
        for(String dateStr:dates){
            String realPath = this.rootPath.replace("DATE",dateStr.replace("-",""));
            try{
                JavaRDD<String> lines = jsc.textFile(realPath);
                dataLoader.getDataMap(tag).put(dateStr,lines.count());
            }catch(NullPointerException e){
                e.printStackTrace();
            }catch(Exception e){
                System.err.println(e.getMessage());
            }
        }
        persistCount(1);

        while(true){
            try {
                Thread.sleep(60 * 1000);
                String dateStr = sf.format(new Date());
                synchronized (dataLoader.getDataMap(tag)){
                    if(!dataLoader.getDates().contains(dateStr) && !dataLoader.getDataMap(tag).containsKey(dateStr)){
                        countTodayData(dateStr);
                        Thread.sleep(60 * 1000);
                    }
                }
                String realPath = this.rootPath.replace("DATE",dateStr.replace("-",""));
                JavaRDD<String> lines = jsc.textFile(realPath);
                try{
                    dataLoader.getDataMap(tag).put(dateStr,lines.count());
                }catch(Exception e){
                    dataLoader.getDataMap(tag).put(dateStr,0l);
                }
                persistCount(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }catch(Exception e){
                System.err.println(e.getMessage());
            }

        }
    }
}
