package ibczy.logview.analysis;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;


public class SingleSparkContext {
    private static JavaSparkContext jsc = null;

    public synchronized static JavaSparkContext getSingle(){
        SparkConf conf = new SparkConf().setAppName("test");//.setMaster("local[*]");
        //conf.set("spark.executor.memory","4g");
        //conf.set("spark.driver.memory","4g");
        if(jsc == null)
            jsc = new JavaSparkContext(conf);
        return jsc;
    }
}
