package ibczy.logview.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by BC349 on 2018/7/11.
 */
public class TimeUtil {

    public static List<String> getBeforeDays(Date date, int days){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        List<String> dates = new ArrayList<> ();
        dates.add(sf.format(date));
        Calendar base = Calendar.getInstance();
        base.setTime(date);
        for(int i=1;i<=days;i++){
            base.add(Calendar.DAY_OF_MONTH,-1);;
            Date d = base.getTime();
            dates.add(0,sf.format(d));
        }
        return dates;
    }
    public static void main(String []args){
        getBeforeDays(new Date(),3);
    }
}
