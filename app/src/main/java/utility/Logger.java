package utility;

import android.util.Log;

/**
 * Created by Abhimaan on 02/02/16.
 */
public class Logger
{
    static boolean isEnabled = true;

    public static void error(Object cls, Object obj)
        {
            if (isEnabled)
                {
                    Log.e(cls.getClass().getName(), obj.toString());
                }
        }

    public static void debug(Object cls, Object obj)
        {
            if (isEnabled)
                {

                    Log.d(cls.getClass().getName(), obj.toString());
                }
        }

    public static void verbose(Object cls, Object obj)
        {
            if (isEnabled)
                {

                    Log.v(cls.getClass().getName(), obj.toString());
                }
        }
}
