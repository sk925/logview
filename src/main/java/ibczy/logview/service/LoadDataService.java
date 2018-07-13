package ibczy.logview.service;

import ibczy.logview.analysis.DataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by BC349 on 2018/7/11.
 */
@Component
public class LoadDataService {
    @Value("${analysis.tag}")
    private String tag;

    @Autowired
    private DataLoader dataLoader;

    public Map<String,Map<String,Long>> load(){
        Map<String,Map<String,Long>> map = new HashMap<>();
        String []tags = tag.split(",");

        synchronized (DataLoader.class){
            for(String tag:tags){
                map.put(tag,dataLoader.getDataMap(tag));
            }
        }
        return map;
    }
}
