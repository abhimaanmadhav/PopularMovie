package movies.movieDetails;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.abhimaan.Result;

import movies.abhimaan.com.popularmovies1.R;

public class MovieDetailActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_movie_detail);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getFragmentManager().beginTransaction().add(R.id.fragment_holder, MovieDetailFragment
                    .newInstance
                    ((Result) getIntent().getParcelableExtra(MovieDetailFragment.ARG_PARAM1))).commit();

        }
}
