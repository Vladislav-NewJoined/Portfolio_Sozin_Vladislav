package task11_9_1.zadaniye4.data_sources;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import task11_9_1.zadaniye4.models.LocationDto;
import task11_9_1.zadaniye4.models.Post;

import java.io.IOException;
import java.util.List;

public interface ReceiverApiDataSource {
    @GET("/post")
    Call<List<Post>> getPosts();

    @POST("/posts")
    Call<Post> createUserPost(@Body Post post);
}

