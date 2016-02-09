package movies.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import movies.abhimaan.com.popularmovies1.MovieDetailsModel;
import utility.Logger;

/**
 * Created by Abhimaan on 08/02/16.
 */
public class MovieDataBase extends SQLiteOpenHelper
{
    private static final int version = 1;
    private static final String NAME = "movies.db";
    private static MovieDataBase mDb;
    private SQLiteDatabase _db;
    private final String MOVIE_TABLE_NAME = "movie";
    private final String MOVIE_ID = "movie_id";
    private final String MOVIE_ISFAVORITE = "movie_isfavorite";
    private final String MOVIE_AVG_RATING = "movie_avg_rating";
    private final String MOVIE_OVERVIEW = "movie_overview";
    private final String MOVIE_POSTER_URL = "movie_poster_url";
    private final String MOVIE_TITLE = "movie_title";
    private final String MOVIE_DATE = "movie_date";

    public static MovieDataBase getInstanse(Context context)
        {
            if (mDb == null)
                {
                    mDb = new MovieDataBase(context);
                }
            return mDb;
        }

    private MovieDataBase(Context context)
        {
            super(context, NAME, null, version);
            _db = getWritableDatabase();
            _db.setVersion(version);
        }

    @Override
    public void onCreate(SQLiteDatabase db)
        {
            db.beginTransaction();
            db.execSQL("CREATE TABLE " + MOVIE_TABLE_NAME + " ( " + MOVIE_ID + " INTEGER primary " +
                    "key " +
                    "," + MOVIE_TITLE + " Text ," + MOVIE_AVG_RATING + " REAL ," +
                    MOVIE_OVERVIEW + " Text," + MOVIE_DATE + " Text," + MOVIE_POSTER_URL + " Text" +
                    " ," + MOVIE_ISFAVORITE +
                    " INTEGER DEFAULT '1')");
            db.setTransactionSuccessful();
            db.endTransaction();
        }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
        }

    public void insertFavorite(MovieDetailsModel model)
        {
            _db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put(MOVIE_ID, model.getId());
            values.put(MOVIE_AVG_RATING, model.getVoteAverage());
            values.put(MOVIE_OVERVIEW, model.getOverview());
            values.put(MOVIE_TITLE, model.getOriginalTitle());
            values.put(MOVIE_POSTER_URL, model.getPath());
            values.put(MOVIE_DATE, model.getReleaseDate());
            _db.insert(MOVIE_TABLE_NAME, null, values);
            _db.setTransactionSuccessful();
            _db.endTransaction();
        }

    public void removeFavorite(int id)
        {
            _db.beginTransaction();
            _db.delete(MOVIE_TABLE_NAME, MOVIE_ID + " = ?", new String[]{String.valueOf(id)});
            _db.setTransactionSuccessful();
            _db.endTransaction();
        }

    public boolean isMovieFavorite(int id)
        {
            _db.beginTransaction();
            Cursor c = _db.query(MOVIE_TABLE_NAME, null, MOVIE_ID + "= " + id, null, null, null,
                    null);
            _db.setTransactionSuccessful();
            _db.endTransaction();
            return (c != null && c.getCount() == 1);

        }

    public ArrayList<MovieDetailsModel> getFavorite()
        {
            _db.beginTransaction();
            Cursor c = _db.query(MOVIE_TABLE_NAME, null, null, null, null, null, null);
            _db.setTransactionSuccessful();
            _db.endTransaction();
            ArrayList<MovieDetailsModel> list = new ArrayList<>();
            MovieDetailsModel mode;
            Logger.error(this, "enquire count " + c.getCount());
            while (c != null && c.moveToNext())
                {
                    mode = new MovieDetailsModel();
                    mode.setId(c.getInt(c.getColumnIndex(MOVIE_ID)));
                    mode.setVoteAverage(c.getFloat(c.getColumnIndex(MOVIE_AVG_RATING)));
                    mode.setPosterPath(c.getString(c.getColumnIndex(MOVIE_POSTER_URL)));
                    mode.setOriginalTitle(c.getString(c.getColumnIndex(MOVIE_TITLE)));
                    mode.setOverview(c.getString(c.getColumnIndex(MOVIE_OVERVIEW)));
                    mode.setReleaseDate(c.getString(c.getColumnIndex(MOVIE_DATE)));
                    mode.setFavorite(true);
                    list.add(mode);
                }
            Logger.error(this, "as " + list.toString());
            return list;
        }
}
