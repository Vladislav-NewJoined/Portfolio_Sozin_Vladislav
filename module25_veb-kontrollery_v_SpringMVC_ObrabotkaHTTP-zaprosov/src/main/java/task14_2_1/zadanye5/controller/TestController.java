package task14_2_1.zadanye5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.bind.annotation.*;
import task14_2_1.zadanye5.model.TestObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<Map<String, String>> saveObject(@RequestBody TestObject testObject) {
        // здесь можно добавить логику сохранения объекта, например в базу данных
        System.out.println("Object created successfully");
        System.out.println("В приложении Postman в ответе на запрос POST, также " +
                "изменены регистры значений поля 'Example Name'");

        // Преобразование значений поля name
        testObject.convertNameToUpperCase();
        String nameInUpperCase = testObject.getName();

        testObject.convertNameToLowerCase();
        String nameInLowerCase = testObject.getName();

        String originalName = testObject.getOriginalName();

        // Подготовка данных для ответа
        Map<String, String> response = new HashMap<>();
        response.put("originalName", originalName);
        response.put("nameInUpperCase", nameInUpperCase);
        response.put("nameInLowerCase", nameInLowerCase);

        // Завершить работу программы через 1 секунду
        taskScheduler.schedule(() -> System.exit(0), new Date(System.currentTimeMillis() + 1000));

        // Возвращаем подготовленный ответ
        return ResponseEntity.ok(response);
    }
}
