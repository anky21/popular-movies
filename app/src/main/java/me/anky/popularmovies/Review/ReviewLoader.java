package me.anky.popularmovies.Review;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by anky_ on 5/11/2016.
 */

public class ReviewLoader extends AsyncTaskLoader<List<MovieReview>> {
    public static final String LOG_TAG = ReviewLoader.class.getSimpleName();

    private String mUrl;

    public ReviewLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<MovieReview> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        List<MovieReview> movieReviews = ReviewQueryUtils.fetchReviewData(mUrl);
        return movieReviews;
    }
}
