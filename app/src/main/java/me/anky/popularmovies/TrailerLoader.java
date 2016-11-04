package me.anky.popularmovies;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by anky_ on 4/11/2016.
 */

public class TrailerLoader extends AsyncTaskLoader<List<MovieTrailer>> {
    public static final String LOG_TAG = TrailerLoader.class.getSimpleName();

    private String mUrl;

    public TrailerLoader(Context context, String url){
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<MovieTrailer> loadInBackground() {
        Log.v(LOG_TAG, "Testing: load in background");
        if(mUrl == null){
            return null;
        }
        List<MovieTrailer> movieTrailers = TrailerQueryUtils.fetchTrailerData(mUrl);
        return movieTrailers;
    }
}
