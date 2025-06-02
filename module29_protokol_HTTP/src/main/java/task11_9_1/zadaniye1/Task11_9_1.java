package task11_9_1.zadaniye1;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import task11_9_1.zadaniye1.models.Post;

import java.io.IOException;
import java.util.List;

// Библиотека GSON добавляется отсюда: https://github.com/google/gson в виде зависимости Maven

interface ReceiversService {
    @GET("/post")
    Call<List<Post>> getPosts();

    @POST("/posts")
    Call<Post> createUserPost(@Body Post post);
}

// Интерфейс сервиса с методом POST
interface LocationService {
    @POST("/location") // Предположим, что есть эндпоинт для отправки координат
    Call<LocationDto> sendLocation(@Body LocationDto location);
}

public class Task11_9_1 {
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
                Решение по заданию 1, В ранее созданном проекте, в папке main  создайте New Package c
                  названием models, далее класс Receiver:
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

        System.out.println("""
                Примеры получения ответов на запросы с использованием протокола 'HTTP':\s
                \s""");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ReceiversService service = retrofit.create(ReceiversService.class);
        Post newPost = new Post(1, "New PostModel Title", "New PostModel Body"); // Пример создания нового поста
        Call<Post> repos = service.createUserPost(newPost); // Пример: создание нового поста
        try {
            Response<Post> res = repos.execute();
            System.out.println(res.body());
        } catch (IOException e) {
            e.printStackTrace();
        }

        LocationService locationService = retrofit.create(LocationService.class);

        // Создаем объект LocationDto с заданными координатами
        LocationDto location = new LocationDto(51.5074, 0.1278);

        // Отправляем координаты на сервер
        Call<LocationDto> locationCall = locationService.sendLocation(location);

        try {
            Response<LocationDto> response = locationCall.execute();
            // Проверка статус кода ответа
            int statusCode = response.code();
            System.out.println("Status code: " + statusCode);

            try (ResponseBody responseBody = response.errorBody()) {
                if (response.isSuccessful()) {
                    LocationDto receivedLocation = response.body();
                    System.out.println("Received location: " + receivedLocation);
                } else {
                    System.out.println("Request was not successful: " + responseBody.string());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

class InRadiusDto {
    final float latitude;
    final float longitude;
    final int radius;
    final int page;
    final int pageSize;

    InRadiusDto(float latitude, float longitude, int radius, int page, int pageSize) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.page = page;
        this.pageSize = pageSize;
    }
}

record Receiver(String id, String title) {

    @Override
    public String toString() {
        return "Receiver{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}

// Создаем класс LocationDto для координат
class LocationDto {
    private final double latitude;
    private final double longitude;

    public LocationDto(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "LocationDto{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}

