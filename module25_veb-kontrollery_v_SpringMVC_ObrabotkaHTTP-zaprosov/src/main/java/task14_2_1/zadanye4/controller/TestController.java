package task14_2_1.zadanye4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.bind.annotation.*;
import task14_2_1.zadanye3.model.TestObject;

import java.util.Date;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TaskScheduler taskScheduler;

    @GetMapping()
    public boolean getRootTest() {
        return true;
    }

    @PostMapping()
    public String postObject(@RequestBody Object object) {
        // здесь можно написать логику для сохранения объекта
        // например, можно использовать сервис для сохранения объекта в базу данных
        return "Object saved successfully";
    }

    @PostMapping("/saveObject")
    public String saveObject(@RequestBody TestObject testObject) {
        // здесь можно добавить логику сохранения объекта, например в базу данных
        System.out.println("Object created successfully"); // Вывод на консоль
        // Запланируем вызов System.exit(0); через 1 секунду
        taskScheduler.schedule(() -> System.exit(0), new Date(System.currentTimeMillis() + 1000));
        return "Object saved successfully";
    }
}
