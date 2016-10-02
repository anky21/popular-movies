package me.anky.popularmovies;

/**
 * Created by anky_ on 2/10/2016.
 */

public class PopularMovie {
    /**
     * Image resource ID for the poster
     */
    private int mPosterResourceId;

    /**
     * Construct a new {@link PopularMovie} object
     *
     * @param posterResourceId is the resource ID of the movie poster
     */
    public PopularMovie(int posterResourceId) {
        mPosterResourceId = posterResourceId;
    }

    public int getPosterResourceId() {
        return mPosterResourceId;
    }
}
