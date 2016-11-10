package me.anky.popularmovies;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import me.anky.popularmovies.data.MovieContract;

/**
 * Created by anky_ on 10/11/2016.
 */

public class FavouriteFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    // Identifier for the Loader
    private static final int FAVOURITE_LOADER = 0;
    FavouriteCursorAdapter mCursorAdapter;
    private TextView mEmptyStateTextView;
    private GridView favouriteGridView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Kick off the loader
        getActivity().getLoaderManager().initLoader(FAVOURITE_LOADER, null, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_activity_fragment, container, false);

        // Remove the ProgressBar
        ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        mEmptyStateTextView = (TextView) rootView.findViewById(R.id.empty_list_view);

        favouriteGridView = (GridView) rootView.findViewById(R.id.movie_activity_grid_view);

        mCursorAdapter = new FavouriteCursorAdapter(getContext(), null);

        favouriteGridView.setAdapter(mCursorAdapter);
        favouriteGridView.setEmptyView(mEmptyStateTextView);

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                MovieContract.MovieEntry._ID,
                MovieContract.MovieEntry.COLUMN_MOVIE_ID,
                MovieContract.MovieEntry.COLUMN_MOVIE_PATH};

        return new CursorLoader(getContext(),
                MovieContract.MovieEntry.CONTENT_URI,
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
