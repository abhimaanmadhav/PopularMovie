package movies.movieDetails.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.ref.WeakReference;

import movies.constants.Constants;
import movies.movieDetails.MovieDetailCallback;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import utility.Logger;

/**
 * Created by Abhimaan on 05/02/16.
 */
public class MovieDetailServiceManager
{
    private MovieDetailsService service;
    int mCurrentpage = 0;
    WeakReference<MovieDetailCallback> mMovieDetailCallback;

    public MovieDetailServiceManager(MovieDetailCallback listener)
        {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd")
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASEURL).addConverterFactory(GsonConverterFactory
                            .create(gson))
                    .build();
            service = retrofit.create(MovieDetailsService.class);
            mMovieDetailCallback = new WeakReference<MovieDetailCallback>(listener);
        }

    public void fetchTrailers(int id)
        {
            service.fetchMovieTrailer(id, Constants.APIKEY).enqueue(new Callback<TrailerResponse>
                    ()
            {
                @Override
                public void onResponse(Response<TrailerResponse> response)
                    {
                        Logger.error(this, "trailer" + response.body());
                        if (mMovieDetailCallback.get() != null)
                            {
                                if (response.isSuccess())
                                    {
                                        mMovieDetailCallback.get().trailerRespose(response.body()
                                                .trailerModels);
                                    } else
                                    {
                                        mMovieDetailCallback.get().onFailure();
                                    }
                            }
                    }

                @Override
                public void onFailure(Throwable t)
                    {
                        Logger.error(this, "trailer error" + t.toString());
                        if (mMovieDetailCallback.get() != null)
                            {
                                mMovieDetailCallback.get().onFailure();
                            }
                    }
            });
        }
public void resetPages(){
    mCurrentpage=0;
}
    public void fetchReviews(int id)
        {
            service.fetchMovieReview(id, Constants.APIKEY, ++mCurrentpage).enqueue(new Callback<ReviewResponse>()

            {
                @Override
                public void onResponse(Response<ReviewResponse> response)
                    {
                        Logger.error(this, "review" + response.body());
                        if (mMovieDetailCallback.get() != null)
                            {
                                if (response.isSuccess())
                                    {
                                        mMovieDetailCallback.get().reviewResponse(response.body()
                                                .reviewModels);
                                    } else
                                    {
                                        Logger.error(this, "reviews failed" );
                                        mMovieDetailCallback.get().onFailure();
                                    }
                            }
                    }

                @Override
                public void onFailure(Throwable t)
                    {
                        Logger.error(this, "reviews failed" + t.getMessage());
                        mCurrentpage--;
                        if (mMovieDetailCallback.get() != null)
                            {
                                mMovieDetailCallback.get().onFailure();
                            }
                    }
            });
        }

}
