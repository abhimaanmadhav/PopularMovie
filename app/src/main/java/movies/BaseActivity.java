package movies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;

import movies.abhimaan.com.popularmovies1.R;

/**
 * Created by Abhimaan on 03/02/16.
 */
public class BaseActivity extends AppCompatActivity
{
    public boolean isConnected()
        {
            ConnectivityManager manager = (ConnectivityManager) getSystemService(Context
                    .CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }

    public boolean isTablet()
        {

            return getResources().getBoolean(R.bool.isTablet);
        }

}
