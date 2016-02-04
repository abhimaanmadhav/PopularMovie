package movies.abhimaan.com.popularmovies1;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

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
import utility.Logger;
import utility.Utils;

/**
 * Created by Abhimaan on 01/02/16.
 */
public class MovieFragment extends Fragment implements Callback<MovieResponse>, AdapterView
        .OnItemClickListener, AbsListView.OnScrollListener
{
    MoviesService service;
    String TAG = "moviefragment";
    private final String GRIDPOS = "GRID_POS", GRID_DATA = "griddata";
    GridView mGridView;
    Feedback mFeedback;
    int page = 0;
    boolean requesting = false;
    String mCurrentConstrain = null;
    int totalPages = -1;
    int selectedPosition = 1;
    ArrayList<MovieDetailsModel> list;

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
            service = retrofit.create(MoviesService.class);
            setRetainInstance(true);
            if (Utils.isConnected(getActivity()))
                {
                    fetchMoviesWithConstrain("popularity.desc", true);
                } else
                {
                    Toast.makeText(getActivity(), getString(R.string.no_network_msg), Toast
                            .LENGTH_LONG).show();
                }
            list = new ArrayList<MovieDetailsModel>();
        }

    @Override
    public void onAttach(Context context)
        {
            super.onAttach(context);
            Logger.error(this, "attach");
            mFeedback = ((Feedback) getActivity());
        }

    @Override
    public void onAttach(Activity activity)
        {
            super.onAttach(activity);
            Logger.error(this, "attach");
            mFeedback = ((Feedback) activity);
        }

    @Override
    public void onDetach()
        {
            super.onDetach();
            mFeedback = null;
            Logger.error(this, "detach");

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
            mGridView = (GridView) view.findViewById(R.id.gridview);

            mGridView.setOnItemClickListener(this);
            mGridView.setOnScrollListener(this);

            mGridView.setAdapter(new MovieAdapter(getActivity(), list));
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
                    ((MovieAdapter) mGridView.getAdapter()).addData(data.movieDetailseModels);
                    totalPages = data.totalPages;
                    if (data.page == 1 && !data.movieDetailseModels.isEmpty() && Utils.isTablet
                            (getActivity()))
                        {
                            ((MovieAdapter) mGridView.getAdapter()).setSelectedPosition(0);
                            View view = mGridView
                                    .getAdapter().getView(0, null, mGridView);

                            mGridView.getOnItemClickListener().onItemClick(mGridView, view, 0, 0);
                        }
                } else
                {
                    page--;
                    Log.d(TAG, "error code" + response.code());
                    Toast.makeText(getActivity(), getString(R.string.sort_rating), Toast
                            .LENGTH_LONG).show();
                }
            requesting = false;
        }

    @Override
    public void onFailure(Throwable t)
        {
            Log.d(TAG, "error code" + t.toString());
            page--;
            if (page == 1)
                {
                    mFeedback.unSelectMenuItems();
                }
            Toast.makeText(getActivity(), getString(R.string.sort_rating), Toast.LENGTH_LONG)
                    .show();
            requesting = false;
        }

    void fetchMoviesWithConstrain(String constrain, boolean reset)
        {

            if (requesting || (totalPages != -1 && page > totalPages))
                {
                    return;
                }
            if (reset && mGridView != null)
                {
                    page = 0;
                    totalPages = -1;
                    ((MovieAdapter) mGridView.getAdapter()).clear();

                }
            requesting = true;
            if (Utils.isConnected(getActivity()))
                {
                    service.getMovies(Constants.APIKEY, constrain, ++page).enqueue(this);
                }
            mCurrentConstrain = constrain;
        }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {

            selectedPosition = position;
            if (Utils.isTablet(getActivity()))
                {

                    mFeedback.dataChange((MovieDetailsModel) parent
                            .getItemAtPosition
                                    (position));
                    ((MovieAdapter) mGridView.getAdapter()).setSelectedPosition(position);

                } else
                {
                    Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                    intent.putExtra(MovieDetailFragment.ARG_PARAM1, (MovieDetailsModel) parent
                            .getItemAtPosition
                                    (position));
                    startActivity(intent);
                }
        }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState)
        {

        }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int
            totalItemCount)
        {
            if (totalItemCount == 0 || requesting)
                {
                    return;
                }
            if (totalItemCount <= view.getFirstVisiblePosition() + visibleItemCount + 4)
                {
                    fetchMoviesWithConstrain(mCurrentConstrain, false);
                } else
                {
                    Logger.debug(this, "else statemeny");
                }

        }

    interface Feedback
    {
        void unSelectMenuItems();

        void dataChange(MovieDetailsModel movieDetailsModel);
    }


    @Override
    public void onSaveInstanceState(Bundle outState)
        {
            Logger.error(this, "move fragment on saved instace");

            outState.putInt(GRIDPOS, mGridView.getFirstVisiblePosition());
            outState.putParcelableArrayList(GRID_DATA, ((MovieAdapter) mGridView.getAdapter())
                    .getData());
            super.onSaveInstanceState(outState);
        }
}
