package hsv.rp.com.CurrencyConverter.Platform;

import android.content.Context;

/**
 * Utility class to provide platform info to other components
 *
 * @author ramesh130@gmail.com
 */
public class Platform {
    private final String LOG_TAG = Platform.class.getSimpleName();
    private static Context mContext;

    /**
     * Get the context
     *
     * @return Activity context
     */
    public static Context getContext(){
        return  mContext;
    }

    /**
     * Set the context
     *
     * @param context Activity context
     */
    public static void setContext(Context context){
        mContext = context;
    }
}
