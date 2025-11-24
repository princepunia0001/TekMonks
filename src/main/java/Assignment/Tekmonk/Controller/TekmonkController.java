package Assignment.Tekmonk.Controller;


import Assignment.Tekmonk.Service.ServiceController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class TekmonkController{


    @Autowired
    private ServiceController serviceController;

    @GetMapping("/getTimeStories")
    public List<Map<String,String>> get6Stories(){
        return serviceController.getStory();
    }

}
