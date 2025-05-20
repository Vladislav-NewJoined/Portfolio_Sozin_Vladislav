package task14_4_1.zadanye5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import task14_4_1.zadanye5.model.TestObject;

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
// В Docker'е подключение (контейнер) называется:
// postgresTest2
// 3dfb0f477632946d5dde15a0728bfadbd431bc14431e59e0caca68c468241cf0
// База данных в приложении DBeaver: somedbPGtest2
// URL базы данных: jdbc:postgresql://localhost:5432/somedbPGtest2
// Пользователь: someuser
// Пароль: 123

@SpringBootApplication
public class SpringBootJpaPostgresqlApplication {
	public static void main(String[] args) {
		System.out.println("""
            Модуль 14. Spring. Задание №4:\s
                Задания:
                5. Реализуйте дополнительный метод getUpper который тип данных string преобразует
                   в верхний регистр
            
                Решение:\s""");

		System.out.println("""
            При помощи следующей команды в терминале IntelliJ IDEA:
            docker run --name postgresTest2 -d -p 5432:5432 -e POSTGRES_DB=somedbPGtest2 -e POSTGRES_USER=someuser -e POSTGRES_PASSWORD=123 postgres:alpine
            Создан контейнер в Docker Desktop:
            Имя контейнера: postgresTest2
            ID контейнера: 3dfb0f477632946d5dde15a0728bfadbd431bc14431e59e0caca68c468241cf0
            Также создана база данных PostgreSQL в DBeaver:
            Имя базы данных: somedbPGtest2
            URL базы данных: jdbc:postgresql://localhost:5432/somedbPGtest2
            Пользователь: someuser
            Пароль: 123
            IMAGE базы данных: postgres:alpine
            В DB Browser в проекте в IntelliJ IDEA создано подключение к этой базе данных.
                \s""");

		System.out.println("""
            Для упрощения обработки, задания 2-5 объединяем в одно.
            
            В приложении Postman отправляем запросы вида, как показано ниже (сначала запрос POST):
            1.
            POST http://localhost:8080/test/setObject
            {
             "str": "ULULulul",
             "i": 123
            }
            
            2.
            GET http://localhost:8080/test/getObject
            
            3.
            GET http://localhost:8080/test/getLower
            
            4.
            GET http://localhost:8080/test/getUpper
            
            и получаем ответы, согласно заданию.\s""");

		SpringApplication app = new SpringApplication(SpringBootJpaPostgresqlApplication.class);
		app.run(args);

		// Создание объекта testObject
		TestObject testObject = new TestObject();
		testObject.setName("Example Name");

		System.out.println("\nОтправьте запрос в Postman.");
	}
}
