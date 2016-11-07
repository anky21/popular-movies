package me.anky.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static me.anky.popularmovies.data.MovieContract.*;

/**
 * Created by anky_ on 7/11/2016.
 */

public class MovieDbHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = MovieDbHelper.class.getSimpleName();

    // Name of the database file
    private static final String DATABASE_NAME = "movie.db";

    //Database version
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link MovieDbHelper}
     *
     * @param context of the app
     */
    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // Called when the database is created for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String containing the SQL statement to create the table
        String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE " + MovieEntry.TABLE_NAME + " ("
                + MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MovieEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL, "
                + MovieEntry.COLUMN_MOVIE_TITLE + " TEXT, "
                + MovieEntry.COLUMN_MOVIE_PATH + " TEXT, "
                + MovieEntry.COLUMN_MOVIE_DATE + " TEXT, "
                + MovieEntry.COLUMN_MOVE_RATING + " TEXT, "
                + MovieEntry.COLUMN_MOVIE_OVERVIEW + " TEXT);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_MOVIES_TABLE);
    }

    // Called when database needs to be upgraded
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
