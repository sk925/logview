package ibczy.logview.analysis.task;

import ibczy.logview.analysis.DataLoader;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;


/**
 * Created by BC349 on 2018/7/12.
 */
public class LoadTask implements Runnable{

    private DataLoader dataLoader;
    private String tag;
    public LoadTask(String tag, DataLoader dataLoader){
        this.tag = tag;
        this.dataLoader = dataLoader;
    }
    @Override
    public void run() {
        String tags[] = tag.split(",");
        while(true){
            System.out.println("更新数据");
            for(String tag:tags){
                File file = new File("count" + File.separator + tag.replace(".","") + ".txt");
                try {
                    String res = FileUtils.readFileToString(file);
                    if(res == null || res.equals(""))
                        continue;
                    String []datas = res.split("\\|");
                    synchronized (DataLoader.class){
                        dataLoader.getDataMap(tag).clear();
                        for(String data:datas){
                            String []unit = data.split("=");
                            dataLoader.getDataMap(tag).put(unit[0],Long.parseLong(unit[1]));
                        }
                    }
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
            try {
                Thread.sleep(30 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
