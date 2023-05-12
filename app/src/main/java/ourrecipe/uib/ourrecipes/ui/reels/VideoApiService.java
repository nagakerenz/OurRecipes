package ourrecipe.uib.ourrecipes.ui.reels;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface VideoApiService {
    @GET("videos")  // Replace with your actual API endpoint for fetching videos
    Call<List<VideoResponse>> getVideos();
}