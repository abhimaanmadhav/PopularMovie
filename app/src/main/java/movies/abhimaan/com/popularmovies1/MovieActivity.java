package movies.abhimaan.com.popularmovies1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MovieActivity extends AppCompatActivity implements MovieGridFragment.Feedback
{

    final String TAG = "MovieActivity";
    final String TAGMOVIEGRID = "moviegrid";
    MenuItem popular, rating;

    @Override
    protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_movie);
            setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
            getFragmentManager().beginTransaction().add(R.id.movie_grid, new MovieGridFragment(),
                    TAGMOVIEGRID).commit();
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
        {
            getMenuInflater().inflate(R.menu.fetch_menu, menu);
            popular = menu.findItem(R.id.action_popular);
            rating = menu.findItem(R.id.action_ratings);
            popular.setChecked(true);
            return super.onCreateOptionsMenu(menu);
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
        {
            switch (item.getItemId())
                {
                    case R.id.action_popular:
                    {
                        if (!item.isChecked())
                            {
                                ((MovieGridFragment) getFragmentManager().findFragmentByTag
                                        (TAGMOVIEGRID)).fetchMoviesWithConstrain("popularity.desc");
                                item.setChecked(true);
                                rating.setChecked(false);
                            }
                    }
                    break;
                    case R.id.action_ratings:
                    {
                        if (!item.isChecked())
                            {
                                ((MovieGridFragment) getFragmentManager().findFragmentByTag
                                        (TAGMOVIEGRID)).fetchMoviesWithConstrain("vote_average" +
                                        ".desc");
                                item.setChecked(true);
                                popular.setChecked(false);
                            }
                    }
                    break;
                }
            return super.onOptionsItemSelected(item);
        }


    @Override
    public void unSelectMenuItems()
        {
            popular.setChecked(false);
            rating.setChecked(false);
        }
}
