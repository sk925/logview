package ibczy.logview;

import ibczy.logview.analysis.SingleSparkContext;
import ibczy.logview.util.TimeUtil;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.tools.nsc.Global;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by BC349 on 2018/7/12.
 */
public class Test {
    public static void main(String []args){
        Task task = new Task();
        Thread t = new Thread(task);
        t.start();
    }
     static class Task implements Runnable{

        Map<String,Long> map = new HashMap<>();

        JavaSparkContext jsc;

        public Task (){
            jsc = SingleSparkContext.getSingle();
        }
        @Override
        public void run() {
            List<String> dates = TimeUtil.getBeforeDays(new Date(),7);
            String path = "hdfs://10.1.1.11:8020//ibczy/reader/book.click.log/log-reader-book-click-DATE.txt";
            for(String dateStr:dates){
                String p = path.replace("DATE",dateStr);
                try{
                    JavaRDD<String> lines =  jsc.textFile(p);
                    long c = lines.count();
                    map.put(dateStr,c);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            System.out.println(map);
        }
    }

}
