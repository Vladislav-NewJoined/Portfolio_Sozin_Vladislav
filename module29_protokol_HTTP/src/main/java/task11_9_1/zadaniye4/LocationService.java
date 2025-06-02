package task11_9_1.zadaniye4;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import task11_9_1.zadaniye4.models.LocationDto;

// Интерфейс сервиса с методом POST
public interface LocationService {
    @POST("/location") // Предположим, что есть эндпоинт для отправки координат
    Call<LocationDto> sendLocation(@Body LocationDto location);
}
