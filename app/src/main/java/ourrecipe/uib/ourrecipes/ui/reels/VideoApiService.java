package ourrecipe.uib.ourrecipes.ui.reels;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface VideoApiService {
    @GET("v1/gifs/search?api_key=BlKwRpHcpktcCdC2miVc40qHx8IHNNxQ&limit=1&q=food")  // Replace with your actual API endpoint for fetching videos
    Call<List<VideoResponse>> getVideos();
}