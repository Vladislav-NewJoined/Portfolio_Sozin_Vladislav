package task14_2_1.zadanye4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import task14_2_1.zadanye4.model.TestObject;

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
                   4. Преобразуйте каждое значение поля в UpperCase, а другим методом в LowerCase.
               
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


		// Создание объекта testObject
		TestObject testObject = new TestObject();
		testObject.setName("Example Name");

		// Вывод значения поля name до преобразования
		System.out.println("Исходное значение поля name: " + testObject.getName());

		// Вызов метода для преобразования в верхний регистр
		testObject.convertNameToUpperCase();

		// Вывод значения поля name после преобразования
		System.out.println("Значение поля name после преобразования в верхний регистр: " + testObject.getName());


		// Создание объекта testObject2
		TestObject testObject2 = new TestObject();
		testObject2.setName("Example Name");

		// Вызов метода для преобразования в нижний регистр
		testObject2.convertNameToLowerCase();

		// Вывод значения поля name после преобразования
		System.out.println("Значение поля name после преобразования в нижний регистр: " + testObject2.getName());
	}
}
