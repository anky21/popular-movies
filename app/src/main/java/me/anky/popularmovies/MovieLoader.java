package me.anky.popularmovies;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by anky_ on 4/10/2016.
 */

public class MovieLoader extends AsyncTaskLoader<List<PopularMovie>> {
    //Tag for log messages
    private static final String LOG_TAG = MovieLoader.class.getSimpleName();

    // Query URL
    private String mUrl;

    /**
     * Constructs a new {@link MovieLoader}
     * @param context of the activity
     * @param url to load data from
     */
    public MovieLoader(Context context, String url){
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread
     * @return a list of movies
     */
    @Override
    public List<PopularMovie> loadInBackground() {
        if(mUrl == null){
            return null;
        }
        // Perform the network request, parse the response, and extract a list of movies
        List<PopularMovie> popularMovies = QueryUtils.fetchMovieData(mUrl);
        return popularMovies;
    }
}
