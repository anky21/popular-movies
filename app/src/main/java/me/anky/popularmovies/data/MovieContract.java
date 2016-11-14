package me.anky.popularmovies.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by anky_ on 7/11/2016.
 */

public class MovieContract {

    private MovieContract() {
    }

    public static final String CONTENT_AUTHORITY = "me.anky.popularmovies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIES = "movies";

    /**
     * Define constant values for the database
     */
    public static final class MovieEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MOVIES);

        /* The MIME type of the {@link #CONTENT_URI} for a list of movies */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;
        /* The MIME type of the {@link #CONTENT_URI} for a single movie */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;

        /* Name of database table for movies */
        public final static String TABLE_NAME = "movies";

        /**
         * Unique ID number for the movie
         * <p>
         * Type INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * M Movie ID
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_MOVIE_ID = "movie_id";

        /**
         * Movie title
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_MOVIE_TITLE = "title";

        /**
         * Poster path
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_MOVIE_PATH = "poster_path";

        /**
         * Release date
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_MOVIE_DATE = "release_date";

        /**
         * Vote average of the movie
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_MOVE_RATING = "rating";

        /**
         * Overview of the movie
         * <p>
         * Type: TEXT
         */
        public final static String COLUMN_MOVIE_OVERVIEW = "overview";

        public static Uri buildMovieUriWithId(String _ID){
            return CONTENT_URI.buildUpon().appendPath(_ID)
                    .build();
        }
    }
}
