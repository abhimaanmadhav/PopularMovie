package movies.movieDetails;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import movies.abhimaan.com.popularmovies1.MovieDetailsModel;

import movies.BaseActivity;
import movies.abhimaan.com.popularmovies1.R;

public class MovieDetailActivity extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_movie_detail);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getFragmentManager().beginTransaction().add(R.id.fragment_holder, MovieDetailFragment
                    .newInstance
                            ((MovieDetailsModel) getIntent().getParcelableExtra(MovieDetailFragment.ARG_PARAM1))).commit();

        }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
        {
            super.onConfigurationChanged(newConfig);
        }
}
