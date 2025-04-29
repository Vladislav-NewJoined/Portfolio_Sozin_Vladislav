
package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class Main {
    public static void main(String[] args) {
        System.out.println("Инструкция в файле: README_ИНСТРУКЦИЯ.txt, в корневой директории проекта");
        System.out.println("Здесь смотрим результаты, переходим на эту веб-страницу (порт) после запуска класса Main " +
                "и появления в консоли лога: \nStarted Main in 8.529 seconds (process running for 11.226):" +
                "\nhttp://localhost:8080/");

        SpringApplication.run(Main.class, args);
    }
}
