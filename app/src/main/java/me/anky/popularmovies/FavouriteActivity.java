package me.anky.popularmovies;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import static me.anky.popularmovies.data.MovieContract.MovieEntry;

public class FavouriteActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {
    // Identifier for the Loader
    private static final int FAVOURITE_LOADER = 0;
    FavouriteCursorAdapter mCursorAdapter;
    private TextView mEmptyStateTextView;
    private GridView favouriteGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_activity_fragment);

        // Remove the ProgressBar
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_list_view);

        favouriteGridView = (GridView) findViewById(R.id.movie_activity_grid_view);

        mCursorAdapter = new FavouriteCursorAdapter(this, null);

        favouriteGridView.setAdapter(mCursorAdapter);
        favouriteGridView.setEmptyView(mEmptyStateTextView);

        // Kick off the loader
        getLoaderManager().initLoader(FAVOURITE_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                MovieEntry._ID,
                MovieEntry.COLUMN_MOVIE_ID,
                MovieEntry.COLUMN_MOVIE_PATH};

        return new CursorLoader(this,
                MovieEntry.CONTENT_URI,
                projection,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || !cursor.moveToFirst()) {
            mEmptyStateTextView.setText(R.string.empty_favourite_gridview_message);
        }
        mCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}
