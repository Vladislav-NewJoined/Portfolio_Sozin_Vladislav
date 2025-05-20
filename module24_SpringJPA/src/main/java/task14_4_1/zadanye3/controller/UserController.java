package task14_4_1.zadanye3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import task14_4_1.zadanye3.model.User;
import task14_4_1.zadanye3.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    ConfigurableApplicationContext applicationContext;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false) String title) {
//        userRepository.
//        var bean::String = applicationContext.getBean(String.class);
//        System.out.println(bean);
        try {
            List<User> users = new ArrayList<>();

            if (title == null)
                users.addAll(userRepository.findAll());
            else
                users.addAll(userRepository.findByNameContaining(title));

            if (users.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(users, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





    @GetMapping("/deleteAllElements")
    public ResponseEntity<String> deleteAllElements(@RequestParam("response") String response) {
        System.out.println("Введите «Да», если Вы хотите удалить все элементы (по Id). " +
                "Введите «Нет», если Вы не хотите удалять все элементы (по Id).");

        if (response.equalsIgnoreCase("Да")) {
            // Логика удаления всех элементов
            // userService.deleteAllElements();

            // Возвращаем строку для удаления всех элементов
            return new ResponseEntity<>("Все элементы удалены. Программа завершена.", HttpStatus.OK);
        } else {
            // Если ответ пользователя не "Да"
            return new ResponseEntity<>("Введён неположительный ответ. Программа завершена.", HttpStatus.OK);
        }
    }



//    @Autowired
//    private HttpGetRequestExample httpRequestExample;
//
//    @GetMapping("/triggerhttprequest")
//    public ResponseEntity<String> triggerHttpRequest() {
//        // Вызываем метод отправки запроса из HttpGetRequestExample
//        httpRequestExample.sendGetRequest();
//
//        return ResponseEntity.ok("HTTP GET запрос отправлен успешно");
//    }

}