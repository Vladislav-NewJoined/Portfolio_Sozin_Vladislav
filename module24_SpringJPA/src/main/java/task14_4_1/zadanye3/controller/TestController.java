package task14_4_1.zadanye3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.bind.annotation.*;
import task14_4_1.zadanye3.services.TestService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    TestService service;

    @Autowired
    private TaskScheduler taskScheduler;

    @GetMapping()
    public boolean getRootTest() {
        return false;
    }

    @GetMapping("/getApple/{count}")
    public List<String> getApple(@PathVariable("count") int count) {
        ArrayList<String> ret = new ArrayList<>();
        for (int i = 0; i < count; ++i)
            ret.add(service.appleName + " #" + i);
        // Завершить работу программы через 1 секунду
        taskScheduler.schedule(() -> System.exit(0), new Date(System.currentTimeMillis() + 1000));
        return ret;
    }

    @PostMapping("/setAppleName")
    public boolean setAppleName(@RequestBody() Map<String, Object> body) {
        try {
            if (body.containsKey("name")) {
                return service.setAppleName(((String) body.get("name")));
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    @PostMapping("/setObject")
    public boolean setObject(@RequestBody() Map<String, Object> body) {
        service.setObject(body);
        return true;
    }

    @GetMapping("/getObject")
    public Map<String, Object> getObject() {
        return service.getObject();
    }

    @GetMapping("/getLower")
    public Map<String, Object> getLower() {
        return service.getLower();
    }

    @GetMapping("/getUpper")
    public Map<String, Object> getUpper() {
        return service.getUpper();
}
}
