package ibczy.logview.controller;

import ibczy.logview.service.LoadDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by BC349 on 2018/7/11.
 */
@RestController
public class LogDataController {

    @Autowired
    private LoadDataService loadDataService;
    @RequestMapping("/load")
    public Map<String,Map<String,Long>> logData(){
        return loadDataService.load();
    }

}
