package task11_9_1.zadaniye6;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import task11_9_1.zadaniye6.models.Post;

import java.util.List;

// Библиотека GSON добавляется отсюда: https://github.com/google/gson в виде зависимости Maven

interface ReceiversService {
    @GET("/post")
    Call<List<Post>> getPosts();

    @POST("/posts")
    Call<Post> createUserPost(@Body Post post);
}

public class Task11_9_6 {
    public static void main(String[] args) {
        System.out.println("""
                Задание:\s
                Модуль 11. Протокол “HTTP”. Задание №9, Проект:\s
                Цель задания: совершенствование  навыков работы с протоколом HTTP\s
                    Задание:
                    1. В ранее созданном проекте, в папке main  создайте New Package c названием models, далее
                       класс Receiver
                    2. В созданный класс Receiver вставляем  соответствующий код класса  из папки target из ранее
                       созданного проекта
                    3. В ранее созданном Package под названием models создаем класс с названием InRadiusDto, далее
                       копируем в него соответствующий код класса из папки target проекта созданного на предыдущих уроках
                    4. В папке main созданного ранее проекта создайте папку New Package с названием data_sources и
                       New Package с названием domain
                    5. В папке data_sources создайте класс ReceiverApiDataSource в него скопируйте содержимое
                       интерфейса ReceiversService из класса Main который содержится в папке target
                    6. В папке  domain создайте класс ReceiveService в нем сделайте
                       import data_sources.ReceiverApiDataSource и в созданном публичном классе реализуйте
                       получение экземпляра  receiverApiDataSource далее реализуйте метод fetch и перенесите
                       в него содержимое из класса  Main в находящегося в папке models, подставьте в
                       соответствующую строчку кода  receiverApiDataSource в исключении try реализуйте
                       возврат res.body, а в исключении catch возврат null
                    7. В классе Main находящегося в папке models создайте сервис и передайте в него ранее созданный
                       receiverApiDataSource
                    8. Реализуйте циклическую загрузку данных при вводе значения latitude и longitude
                \s""");

        System.out.println("""
                Решение по заданию 6, В папке  domain создайте класс ReceiveService в нем сделайте
                       import data_sources.ReceiverApiDataSource и в созданном публичном классе реализуйте
                       получение экземпляра  receiverApiDataSource далее реализуйте метод fetch и перенесите
                       в него содержимое из класса  Main в находящегося в папке models, подставьте в
                       соответствующую строчку кода  receiverApiDataSource в исключении try реализуйте
                       возврат res.body, а в исключении catch возврат null:
                \s""");

        System.out.println("""
                Примечание: адрес веб-страницы 'https://receivers.api.ecohub.eco' в настоящее время является
                недействительным, поэтому вместо него использован веб-сервис для тестирования и создания фейковых
                координат в ответ на запросы (или просто фейковой информации, соответствующей запрошенным координатам):
                'https://jsonplaceholder.typicode.com' и, соответственно, названия папок были изменены на актуальные
                для этого ресурса. Таким образом, поскольку API-ресурс в исходных данных по заданию недоступен, мною
                сделано решение, примерно соответствующее условиям данного задания, посколку внутренняя структура данных
                API-ресурсов отличается, и некоторые фрагменты кода Java могут не совпадать, с теми, которые
                используются в видеоуроке при обращении к изначально заданному ресурсу 'https://receivers.api.ecohub.eco'.
                \s""");
    }
}
