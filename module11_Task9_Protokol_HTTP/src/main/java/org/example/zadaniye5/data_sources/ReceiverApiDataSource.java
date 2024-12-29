package org.example.zadaniye5.data_sources;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import org.example.zadaniye5.models.Post;

import java.util.List;

public interface ReceiverApiDataSource {
    @GET("/post")
    Call<List<Post>> getPosts();

    @POST("/posts")
    Call<Post> createUserPost(@Body Post post);
}

