package movies.abhimaan.com.popularmovies1;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import movies.BaseActivity;
import movies.movieDetails.MovieDetailFragment;
import utility.Logger;
import utility.Utils;

public class MovieActivity extends BaseActivity implements MovieFragment.Feedback,MovieDetailFragment.MovieDetailTabletInterface
{

    final String TAGMOVIEGRID = "moviegrid";
    final String TAGMOVIEDETAIL = "moviedetail";
    MenuItem popular, rating, fav;
    MovieDetailFragment detailFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_movie);
            setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
            MovieFragment fragment = (MovieFragment) getFragmentManager().findFragmentByTag
                    (TAGMOVIEGRID);
            if (fragment == null)
                {
                    fragment = new MovieFragment();
                    getFragmentManager().beginTransaction().add(R.id.movie_grid, fragment,
                            TAGMOVIEGRID)
                            .commit();
                }

            if (Utils.isTablet(this))
                {
                    detailFragment = (MovieDetailFragment) getFragmentManager().findFragmentByTag
                            (TAGMOVIEDETAIL);
                    if (detailFragment == null)
                        {
                            detailFragment = MovieDetailFragment.newInstance(new
                                    MovieDetailsModel());
                            getFragmentManager().beginTransaction().add(R.id.movie_detail,
                                    detailFragment,
                                    TAGMOVIEDETAIL).commit();
                        }


                }
            Logger.error(this, "move activyt on create " + (savedInstanceState == null));
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
        {
            getMenuInflater().inflate(R.menu.movies_menu, menu);
            popular = menu.findItem(R.id.action_popular);
            rating = menu.findItem(R.id.action_ratings);
            fav = menu.findItem(R.id.action_favorite);
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
                                ((MovieFragment) getFragmentManager().findFragmentByTag
                                        (TAGMOVIEGRID)).fetchMoviesWithConstrain(MovieFragment
                                        .SORT_POP);
                                item.setChecked(true);
                                rating.setChecked(false);
                                fav.setChecked(false);
                                if(isTablet()){
                                 detailFragment.setData(new MovieDetailsModel());
                                }
                            }
                    }
                    break;
                    case R.id.action_ratings:
                    {
                        if (!item.isChecked())
                            {
                                ((MovieFragment) getFragmentManager().findFragmentByTag
                                        (TAGMOVIEGRID)).fetchMoviesWithConstrain(MovieFragment
                                        .SORT_RATING);
                                item.setChecked(true);
                                popular.setChecked(false);
                                fav.setChecked(false);
                                if(isTablet()){
                                    detailFragment.setData(new MovieDetailsModel());
                                }
                            }
                    }
                    case R.id.action_favorite:
                    {
                        if (!item.isChecked())
                            {
                                ((MovieFragment) getFragmentManager().findFragmentByTag
                                        (TAGMOVIEGRID)).fetchFavorite();
                                item.setChecked(true);
                                popular.setChecked(false);
                                rating.setChecked(false);
                            }
                    }
                    break;
                    case R.id.action_share:
                    {
                        detailFragment.shareUrl();
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

    @Override
    public void dataChange(MovieDetailsModel movieDetailsModel)
        {
            if (isTablet())
                {
                    detailFragment.setData(movieDetailsModel);
                }
        }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState)
        {
            super.onSaveInstanceState(outState, outPersistentState);
            Logger.error(this, "move activty on saved instace");
        }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState)
        {
            Logger.error(this, "move activyt on restore instace");
            super.onRestoreInstanceState(savedInstanceState, persistentState);
        }

    @Override
    public void removedAsFavorite(int id)
        {
            ((MovieFragment) getFragmentManager().findFragmentByTag
                    (TAGMOVIEGRID)).fetchFavorite();
        }
}
