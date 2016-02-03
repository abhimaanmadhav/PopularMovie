package utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import movies.abhimaan.com.popularmovies1.R;

/**
 * Created by Abhimaan on 03/02/16.
 */
public class Utils
{
    public static boolean isConnected(Context mContext)
        {
            ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context
                    .CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }

    public static boolean isTablet(Context mContext)
        {

            return mContext.getResources().getBoolean(R.bool.isTablet);
        }
}
