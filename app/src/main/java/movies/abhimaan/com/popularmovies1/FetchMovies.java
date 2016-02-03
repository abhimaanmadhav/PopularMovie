package movies.abhimaan.com.popularmovies1;

import com.abhimaan.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Abhimaan on 28/01/16.
 */
public interface FetchMovies
{
    @GET("/3/discover/movie")
    Call<MovieResponse> getMovies(@Query("api_key") String key, @Query("sort_by") String
            sortOrder, @Query("page") int pageNo);


}
