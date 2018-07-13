package ibczy.logview.analysis;

import ibczy.logview.util.TimeUtil;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by BC349 on 2018/7/11.
 * 负责管理不同事件日志的数据
 */
@Component
public class DataLoader {

    public static String CLICK = "click";
    public static String READ = "read.time";
    public static String SHELF = "in.bookshelf";
    public static String SUBSCRIBE = "subscribe";

    private Map<String,Long> clickMap = new ConcurrentHashMap<>();
    private Map<String,Long> readMap = new ConcurrentHashMap<>();
    private Map<String,Long> shelfMap = new ConcurrentHashMap<>();
    private Map<String,Long> subscribeMap = new ConcurrentHashMap<>();
    private volatile List<String> dates = TimeUtil.getBeforeDays(new Date(),6);

    public static DataLoader instance;

    public synchronized static DataLoader getInstance(){
        if(instance == null)
            instance = new DataLoader();
        return instance;
    }

    public Map<String,Long> getDataMap(String tag){
        if(tag.equals(CLICK))
            return clickMap;
        if(tag.equals(READ))
            return  readMap;
        if(tag.equals(SHELF))
            return shelfMap;
        if(tag.equals(SUBSCRIBE))
            return subscribeMap;
        return null;
    }
    public List<String> getDates(){
        return dates;
    }


}
