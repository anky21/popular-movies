package me.anky.popularmovies.favourite;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.anky.popularmovies.R;
import me.anky.popularmovies.Utilities;

import static me.anky.popularmovies.data.MovieContract.MovieEntry;

/**
 * Created by anky_ on 10/11/2016.
 */

public class FavouriteFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private Unbinder unbinder;

    // Identifier for the Loader
    private static final int FAVOURITE_LOADER = 0;
    FavouriteCursorAdapter mCursorAdapter;

    @BindView(R.id.empty_list_view)
    TextView mEmptyStateTextView;
    @BindView(R.id.movie_collection_grid_view)
    GridView favouriteGridView;
    private int mPosition = GridView.INVALID_POSITION;
    private static final String SELECTED_KEY = "selected_position";


    /**
     * A callback interface that allows the fragment to pass data on to the activity
     */
    public interface Callback {
        public void onFavouriteSelected(Uri contentUri);
    }

    public FavouriteFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Kick off the loader
        getActivity().getLoaderManager().initLoader(FAVOURITE_LOADER, null, this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // When tablets rotate, saves the currently selected list item
        if (mPosition != GridView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, mPosition);
        }
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.favourite_fragment, container, false);

        unbinder = ButterKnife.bind(this,rootView);

        mCursorAdapter = new FavouriteCursorAdapter(getContext(), null);

        favouriteGridView.setAdapter(mCursorAdapter);
        favouriteGridView.setEmptyView(mEmptyStateTextView);

        // Click on a movie to show its details
        favouriteGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(i);
                if (cursor != null) {
                    String columnId = cursor.
                            getString(cursor.getColumnIndex(MovieEntry._ID));
                    ((Callback) getActivity()).onFavouriteSelected(MovieEntry.buildMovieUriWithId(columnId));
                }
                mPosition = i;
            }
        });

        // If there's instance state, mine it for useful info
        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                MovieEntry._ID,
                MovieEntry.COLUMN_MOVIE_ID,
                MovieEntry.COLUMN_MOVIE_PATH};

        return new CursorLoader(getContext(),
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

        if (mPosition != ListView.INVALID_POSITION) {
            // If we don't need to restart the loader, and there's a desired position to restore
            // to, do so now.
            favouriteGridView.smoothScrollToPosition(mPosition);
        }

        if (Utilities.isTablet(getContext())) {
            favouriteGridView.performItemClick(favouriteGridView.getChildAt(0), 0, mCursorAdapter.getItemId(0));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}
