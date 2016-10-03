package me.anky.popularmovies;

/**
 * Created by anky_ on 3/10/2016.
 */

import android.net.Uri;

import java.net.MalformedURLException;

/**
 * Helper methods related to requesting and receiving movie data from the Movie DB
 */
public final class QueryUtils {
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185/";

    private QueryUtils(){
        // An empty Query Utils constructure
    }

    /**
     * Create the URL for images (posters, thumbnails)
     */
    public static String getImageUrl(String string) throws MalformedURLException {
        Uri builtUri = Uri.parse(IMAGE_BASE_URL).buildUpon().appendEncodedPath(string).build();
        String  myUrl = builtUri.toString();
        return myUrl;
    }

}
