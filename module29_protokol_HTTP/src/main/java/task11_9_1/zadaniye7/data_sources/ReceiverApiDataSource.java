package task11_9_1.zadaniye7.data_sources;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import task11_9_1.zadaniye7.models.PostModel;

import java.util.List;

public interface ReceiverApiDataSource {
    @GET("/post")
    Call<List<PostModel>> getPosts();

    @POST("/posts")
    Call<PostModel> createUserPost(@Body PostModel postModel);
}

