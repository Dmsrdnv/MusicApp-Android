package pl.holker.music_app_android.data.repo;

import pl.holker.music_app_android.data.model.ResponseMany;
import pl.holker.music_app_android.data.model.ResponseOne;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GendXApiService {
    @GET("/api/recognize_with_opencv")
    Call<ResponseMany> getGenderMany(@Query("image") String image);

    @GET("/api/recognize")
    Call<ResponseOne> getGenderOne(@Query("image") String image);
}
