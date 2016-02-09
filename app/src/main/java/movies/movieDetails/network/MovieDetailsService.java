package movies.movieDetails.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Abhimaan on 05/02/16.
 */
public interface MovieDetailsService
{
    @GET("/3/movie/{id}/videos")
    Call<TrailerResponse> fetchMovieTrailer(@Path("id") int id, @Query("api_key") String
            key);
    @GET("/3/movie/{id}/reviews")
    Call<ReviewResponse> fetchMovieReview(@Path("id") int id, @Query("api_key") String
            key, @Query("page") int page);
}
