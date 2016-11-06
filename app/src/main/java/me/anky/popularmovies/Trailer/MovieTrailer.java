package me.anky.popularmovies.Trailer;

/**
 * Created by anky_ on 4/11/2016.
 */

public class MovieTrailer {
    private String mTrailerKey;
    private String mTrailerNumber;

    public MovieTrailer (String trailerKey, String trailerNumber){
        mTrailerKey = trailerKey;
        mTrailerNumber = trailerNumber;
    }

    public String getTrailerKey() {
        return mTrailerKey;
    }

    public String getTrailerNumber() {
        return mTrailerNumber;
    }
}
