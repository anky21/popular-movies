package me.anky.popularmovies;

import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by anky_ on 17/11/2016.
 */

public class Utilities {

    // Called to tell whether the app is running on a phone or a tablet
    public static boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }
}
