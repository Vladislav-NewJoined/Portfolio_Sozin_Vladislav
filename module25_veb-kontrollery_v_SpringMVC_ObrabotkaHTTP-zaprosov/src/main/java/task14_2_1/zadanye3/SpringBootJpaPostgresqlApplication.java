package task14_2_1.zadanye3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Здесь выполнять инициализацию проекта: https://start.spring.io/

// Здесь инструкции, как работать с Postman:
// https://www.youtube.com/watch?v=S142sn__F_0
// https://www.youtube.com/watch?v=A_jGdrGRLd0

//Данные для postman
//https://www.youtube.com/watch?v=S142sn__F_0
//Ссылка на скачивание Postman - https://www.postman.com/downloads/
//Ссылка на скачивание кода API - https://github.com/bestLessons/postma...
//sozin.vladislav@mail.ru
//sozinvladislav
//пароль 1234567

// В качестве тестовой используется эта база данных 'Postgress': (В этом задании не используется)
// Сначала нужно подключить Docker
// База данных: somedbPGtest
// Пользователь: someuser
// Пароль: 123

@SpringBootApplication
public class SpringBootJpaPostgresqlApplication {
	public static void main(String[] args) {
		System.out.println("""
               Модуль 14. Spring. Задание №2:\s
                   Задания:
                   3. Реализуйте его хранение.
               
                   Решение:
               \s""");

		System.out.println(" В коде ниже реализовано хранение данного объекта. " +
				"В рамках решения задания, отправляем через приложение Postman запрос POST такого вида: " +
				"JSON" +
				"{\n" +
				"    \"id\": 1,\n" +
				"    \"name\": \"Example Name\"\n" +
				"}" +
				", указав заголовок запроса: Content-Type: application/json," +
				" и URL моего эндпоинта http://localhost:8080/test/saveObject," +
				" и получаем ответ: 'Object saved successfully'");

		SpringApplication app = new SpringApplication(SpringBootJpaPostgresqlApplication.class);
		app.run(args);
	}
}
