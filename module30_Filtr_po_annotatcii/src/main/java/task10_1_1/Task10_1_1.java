package task10_1_1;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Command {
    String name();
    boolean showInHelp();
}

class Commands {
    @Command(name = "hello", showInHelp = true)
    public void hello() {
        System.out.println("Hello, human");
    }

    @Command(name = "bye", showInHelp = true)
    public void bye() {
        System.out.println("Bye, human");
    }

    @Command(name = "stop", showInHelp = false)
        public void stop() {
            System.out.println("I'm stopped");
        }
    @Command(name = "qwerty", showInHelp = false)
    public void qwerty () {
    }
}

public class Task10_1_1 {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        System.out.println("""
                Задание:\s
                Модуль 10. Подходы к программированию. Задание №1:\s
                    Повторяя действия за спикером во время урока сделайте следующее:\s
                    1. Пройдите по дереву класса
                    2. Выведите методы и поля
                    3. Создайте фильтр по аннотации. Аннотация применяется к методам и полям.
                    4. В качестве дополнительного задания опишите, как вы поняли, что такое  Annotation и Reflection?\s

                Решение:\s""");

        Class2 class2 = new Class2();
        System.out.println("В следующем фрагменте мы проходим по дереву класса: ");
        showClassInfo(class2.getClass());


        Person person = new Person("qwerty", 20, "88005553535");
        System.out.println("В следующем фрагменте выводим методы и поля класса, используя функционал 'Рефлексия': ");
        System.out.println(person.getClass().getSimpleName());
        Field nameField = person.getClass().getDeclaredField("name");
        nameField.setAccessible(true);
        System.out.println(nameField.get(person));
        Method[] methods = person.getClass().getDeclaredMethods();
        for (int i = 0; i < methods.length; i++) {
            methods[i].invoke(person);
        }

        System.out.println("\nВ следующем фрагменте обрабатываем команды, используя функционал 'Аннотации': ");
        System.out.println("Напишите команду ниже текста этого фрагмента. Используйте команды: 'hello' или 'bye' " +
                "или 'stop' или 'qwerty' или любую другую, на Ваше усмотрение \n(на 'qwerty' и другие команды программа " +
                "не даст отклика): ");
        System.out.println("Select command");
        Commands commands = new Commands();
        for (Method method : commands.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Command.class)) {
                Command command = method.getAnnotation(Command.class);
                if (command.showInHelp()) {
                    System.out.println(command.name());
                }
            }
        }
        Scanner scanner = new Scanner(System.in);
        final String myCommand = scanner.nextLine();
        for (Method method : commands.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Command.class)) {
                Command command = method.getAnnotation(Command.class);
                if (command.name().equals(myCommand)) {
                    method.invoke(commands);
                }
            }
        }

        System.out.println("""
                \nВ следующем фрагменте привожу описание, как я понял, что такое Annotation и Reflection:
                  Annotation (аннотация) - это механизм в Java, который позволяет добавлять метаданные к коду
                  для облегчения его анализа и обработки на этапе выполнения программы. Аннотации могут быть
                  использованы для описания классов, методов, переменных и прочих элементов программы.
                
                  Reflection (отражение) - это механизм в Java, позволяющий программе изучать и модифицировать
                  свою структуру и поведение во время выполнения. С помощью рефлексии можно получить информацию
                  о классе, вызвать его методы, изменить значения полей и т.д. Рефлексия используется, например,
                  для создания универсальных библиотек, плагинов и тестовых фреймворков.\s""");
    }

    static void showClassInfo(Class _class) {
        System.out.println(_class.getSimpleName());
        for (Field field : _class.getDeclaredFields()) {
            if (field.isAnnotationPresent(FilterIt.class)) {
                FilterIt f = field.getAnnotation(FilterIt.class);
                if (f.show()) {
                    System.out.println(field.getName());
                }
            }
        }
        for (Method method : _class.getDeclaredMethods()) {
            if (method.isAnnotationPresent(FilterIt.class)) {
                FilterIt f = method.getAnnotation(FilterIt.class);
                if (f.show()) {
                    System.out.println(method.getName());
                }
            }
        }
        System.out.println();
        Class parentClass = _class.getSuperclass();
        if (parentClass != null) {
            showClassInfo(_class.getSuperclass());
        }
    }
}

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
@interface FilterIt {
    boolean show();
}



class Class1 {
    @FilterIt(show = true)
    String field1;
    @FilterIt(show = false)
    String field2;
    @FilterIt(show = true)
    String field3;

    @FilterIt(show = true)
    void method1() {}
    @FilterIt(show = true)
    void method2() {}
    @FilterIt(show = false)
    void method3() {}
}

class Class2 extends Class1 {
    @FilterIt(show = true)
    String field11;
    @FilterIt(show = true)
    Object field22;
    @FilterIt(show = false)
    int field33;

    @FilterIt(show = true)
    String method11() {return "";}

    @FilterIt(show = true)
    Integer method22() {return 0;}

    @FilterIt(show = false)
    Double method33() {return 0.0;}
}

class Person{
    private final String name;
    public int age;
    public final String phone;

    Person(String name, int age, String phone) {
        this.name = name;
        this.age = age;
        this.phone = phone;
    }

    void display () {
        System.out.println(name + age + phone);
    }

    void display1 () {
        System.out.println(name + phone);
    }


}