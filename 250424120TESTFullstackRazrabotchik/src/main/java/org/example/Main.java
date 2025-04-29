
package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class Main {
    public static void main(String[] args) {
        System.out.println("My project has started");
        System.out.println("Здесь смотрим результаты: http://localhost:8080/");

//        Вы можете просто ввести URL в адресную строку браузера для GET-запросов:
//        http://localhost:8080/api/items?page=0&size=20
//        http://localhost:8080/api/items?page=0&size=20&search=Item%2010
//        После запуска Main класса переходим сюда:
//        http://localhost:8080/
//        или
//        http://localhost:8080/index.html
//        или
//        http://localhost:8080/api/items?page=0&size=20


        SpringApplication.run(Main.class, args);
    }
}
