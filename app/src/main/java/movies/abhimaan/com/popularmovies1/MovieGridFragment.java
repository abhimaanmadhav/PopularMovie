package movies.abhimaan.com.popularmovies1;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.abhimaan.MovieResponse;
import com.abhimaan.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import movies.constants.Constants;
import movies.movieDetails.MovieDetailActivity;
import movies.movieDetails.MovieDetailFragment;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Abhimaan on 01/02/16.
 */
public class MovieGridFragment extends Fragment implements Callback<MovieResponse>, AdapterView
        .OnItemClickListener
{
    FetchMovies service;
    String TAG = "moviefragment";
    GridView gridView;
    Feedback mFeedback;

    @Override
    public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd")
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASEURL).addConverterFactory(GsonConverterFactory
                            .create(gson))
                    .build();

            service = retrofit.create(FetchMovies.class);
            fetchMoviesWithConstrain("popularity.desc");
        }

    @Override
    public void onAttach(Context context)
        {
            super.onAttach(context);
            mFeedback = ((Feedback) getActivity());
        }

    @Override
    public void onDetach()
        {
            super.onDetach();
            mFeedback = null;

        }

    @Override
    public void onDestroy()
        {
            service = null;
            super.onDestroy();
        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState)
        {
            View view = inflater.inflate(R.layout.fragment_movie, container, false);
            gridView = (GridView) view.findViewById(R.id.gridview);
            gridView.setAdapter(new MovieAdapter(getActivity(), new ArrayList(0)));
            gridView.setOnItemClickListener(this);
//            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
//            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            return view;
        }

    @Override
    public void onResponse(Response<MovieResponse> response)
        {
            Log.d(TAG, "status" + response.isSuccess());
            if (response.isSuccess())
                {
                    Log.d(TAG, "sucess code" + response.body().toString());
                    MovieResponse data = response.body();
                    ((MovieAdapter) gridView.getAdapter()).resetData(data.results);
                } else
                {
                    Log.d(TAG, "error code" + response.code());
                    Toast.makeText(getActivity(), getString(R.string.sort_rating), Toast
                            .LENGTH_LONG).show();
                }
        }

    @Override
    public void onFailure(Throwable t)
        {
            Log.d(TAG, "error code" + t.toString());
            Toast.makeText(getActivity(), getString(R.string.sort_rating), Toast.LENGTH_LONG)
                    .show();
//
        }

    void fetchMoviesWithConstrain(String constrain)
        {
            service.getMovies(Constants.APIKEY, constrain).enqueue(this);
        }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
            intent.putExtra(MovieDetailFragment.ARG_PARAM1, (Result) parent.getItemAtPosition
                    (position));
            startActivity(intent);
        }

    interface Feedback
    {
        void unSelectMenuItems();
    }
}
