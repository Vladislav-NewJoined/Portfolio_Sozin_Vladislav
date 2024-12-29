package org.example.zadaniye6;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import org.example.zadaniye6.models.LocationDto;

// Интерфейс сервиса с методом POST
public interface LocationService {
    @POST("/location") // Предположим, что есть эндпоинт для отправки координат
    Call<LocationDto> sendLocation(@Body LocationDto location);
}
