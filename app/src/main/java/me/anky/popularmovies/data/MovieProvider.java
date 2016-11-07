package me.anky.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import static me.anky.popularmovies.data.MovieContract.CONTENT_AUTHORITY;
import static me.anky.popularmovies.data.MovieContract.MovieEntry;
import static me.anky.popularmovies.data.MovieContract.PATH_MOVIES;

/**
 * Created by anky_ on 7/11/2016.
 */

/**
 * {@link ContentProvider} for Popular Movies app
 */
public class MovieProvider extends ContentProvider {
    public static final String LOG_TAG = MovieProvider.class.getSimpleName();

    /**
     * Database helper object
     */
    private MovieDbHelper mDbHelper;

    /**
     * URI matcher code for the content URI for the movies table
     */
    private static final int MOVIES = 10;

    /**
     * URI matcher code for the content URI for a single movie in the table
     */
    private static final int MOVIE_ID = 20;

    /**
     * UriMatcher object to match a content URI to a corresponding code
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer
    static {
        // The content URI of the form "content://me.anky.popularmovies/movies" will map to the
        // integer code {@link #MOVIES}
        // URI used to provide access to multiple rows of the table
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_MOVIES, MOVIES);

        // The content URI of the form "content://me.anky.popularmovies/movies/#" will map to the
        // integer code {@link #MOVIE_ID}
        // URI used to provide access to one single row of the table
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_MOVIES + "/#",
                MOVIE_ID);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Get readable database
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Cursor to hold the result of the query
        Cursor cursor;

        // Get the code that the URI matcher matches to
        int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                cursor = db.query(MovieEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case MOVIE_ID:
                selection = MovieEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(MovieEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        // Set notification URI on the Cursor
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                return MovieEntry.CONTENT_LIST_TYPE;
            case MOVIE_ID:
                return MovieEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        String movieId = contentValues.getAsString(MovieEntry.COLUMN_MOVIE_ID);
        if (movieId == null) {
            throw new IllegalArgumentException("Movie request an ID");
        }

        // Get writable database
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Insert the new movie with the given values
        long id = db.insert(MovieEntry.TABLE_NAME, null, contentValues);

        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Notify all listeners that the data has changed
        getContext().getContentResolver().notifyChange(uri, null);

        // Return the new URI with the ID appended at the end
        return ContentUris.withAppendedId(uri, id);

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Get writeable database
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Number of rows deleted
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                rowsDeleted = db.delete(MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case MOVIE_ID:
                selection = MovieEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = db.delete(MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        // Notify all listeners if >= 1 row has been deleted
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        if (contentValues.containsKey(MovieEntry.COLUMN_MOVIE_ID)) {
            String movieId = contentValues.getAsString(MovieEntry.COLUMN_MOVIE_ID);
            if (movieId == null) {
                throw new IllegalArgumentException("Movie reques an ID");
            }
        }

        // Do not update if there are no values to update
        if (contentValues.size() == 0) {
            return 0;
        }

        // Get writeable database
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Number of rows updated
        int rowsUpdated = db.update(MovieEntry.TABLE_NAME, contentValues, selection, selectionArgs);

        // Notify all listeners if at least one row has been updated
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }
}
