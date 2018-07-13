package ibczy.logview.analysis.task;

import com.codahale.metrics.MetricRegistryListener;
import ibczy.logview.analysis.DataLoader;
import ibczy.logview.analysis.SingleSparkContext;
import org.apache.commons.io.FileUtils;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by BC349 on 2018/7/12.
 */
public abstract class BaseTask implements Runnable {

    protected String tag;
    protected File writeFile;
    protected String rootPath;
    protected List<String> dates;
    protected SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    protected DataLoader dataLoader = DataLoader.getInstance();
    protected JavaSparkContext jsc = SingleSparkContext.getSingle();

    public BaseTask(String tag, String rootPath) {
        this.tag = tag;
        this.rootPath = rootPath.replace("[TAG]", tag).replace("[TAGB]", tag.replace(".", "-"));
        this.dates = dataLoader.getDates();
        writeFile = new File("count" + File.separator + tag.replace(".", "") + ".txt");
    }

    protected void persistCount(int type) {
        if(type == 1){
            dates.forEach(dateStr -> {
                if (!dataLoader.getDataMap(tag).containsKey(dateStr))
                    dataLoader.getDataMap(tag).put(dateStr, 0l);
            });
        }
        try {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Long> entry : dataLoader.getDataMap(tag).entrySet()) {
                sb.append(entry.getKey()).append("=");
                sb.append(entry.getValue()).append("|");
            }
            if (sb.length() > 0)
                sb.deleteCharAt(sb.length() - 1);
            FileUtils.write(writeFile, sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 计算当天的数据量，
     * 并把当天的日期放到日期列表
     * 把当天的数据放入到监控map中
     * @param today
     */
    protected void countTodayData(String today){
        String realPath = rootPath.replace("DATE",today.replace("-",""));
        JavaRDD<String> lines = jsc.textFile(realPath);
        try{
            dataLoader.getDataMap(tag).put(today,lines.count());
        }catch(Exception e){
            dataLoader.getDataMap(tag).put(today,0l);
        }
        dataLoader.getDataMap(tag).remove(dataLoader.getDates().get(0));//把最久的日期数据删除
        dataLoader.getDates().remove(0);//把最久的日期删除
        dataLoader.getDates().add(today);
    }
}