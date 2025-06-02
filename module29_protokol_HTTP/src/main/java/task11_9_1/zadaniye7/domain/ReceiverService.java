package task11_9_1.zadaniye7.domain;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import task11_9_1.zadaniye7.models.LocationDto;
import task11_9_1.zadaniye7.data_sources.ReceiverApiDataSource;
import task11_9_1.zadaniye7.models.PostModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReceiverService {
    final ReceiverApiDataSource receiverApiDataSource;

    public ReceiverService(ReceiverApiDataSource receiverApiDataSource) {
        this.receiverApiDataSource = receiverApiDataSource;
    }

    public PostModel fetch() {
        PostModel newPostModel = new PostModel(1, "New Post", "This is a new post with coordinates", 123.456, 78.910); // Пример создания нового поста
        try {
            URL url = new URL("https://jsonplaceholder.typicode.com/posts");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            System.out.println("Response Code: " + connection.getResponseCode());
            System.out.println("Response Body: " + response.toString());
            return newPostModel; // или любой другой объект PostModel, созданный в вашем коде
        } catch (Exception e) {
            System.out.println("HTTP Request failed");
            e.printStackTrace();
            return null; // Возвращаем null в случае неудачного запроса
        }
    }
}
