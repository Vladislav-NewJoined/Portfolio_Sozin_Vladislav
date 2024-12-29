package org.example.zadaniye8.domain;

import org.example.zadaniye8.data_sources.ReceiverApiDataSource;
import org.example.zadaniye8.models.PostModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ReceiverService {
    final ReceiverApiDataSource receiverApiDataSource;

    private int currentPage = 0;

    public ReceiverService(ReceiverApiDataSource receiverApiDataSource) {
        this.receiverApiDataSource = receiverApiDataSource;
    }

    public PostModel fetchAll(float latitude, float longitude, int RadiusKm) throws IOException {
        PostModel newPostModel = new PostModel(1, "New Post", "This is a new post with coordinates", currentPage, 10, 10000, latitude, longitude); // Пример создания нового поста

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

    }
}
