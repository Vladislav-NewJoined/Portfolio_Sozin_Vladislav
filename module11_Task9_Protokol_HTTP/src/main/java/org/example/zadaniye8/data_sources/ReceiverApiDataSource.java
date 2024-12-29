package org.example.zadaniye8.data_sources;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import org.example.zadaniye8.models.PostModel;

import java.util.List;

public interface ReceiverApiDataSource {
    @GET("/post")
    Call<List<PostModel>> getPosts();

    @POST("/posts")
    Call<PostModel> createUserPost(@Body PostModel postModel);
}

