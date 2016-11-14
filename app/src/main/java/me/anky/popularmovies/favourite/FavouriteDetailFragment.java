package me.anky.popularmovies.favourite;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;

import me.anky.popularmovies.QueryUtils;
import me.anky.popularmovies.R;

import static me.anky.popularmovies.DetailFragment.setFavouriteImageText;
import static me.anky.popularmovies.data.MovieContract.MovieEntry;

/**
 * Created by anky_ on 10/11/2016.
 */

public class FavouriteDetailFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    ContentResolver mContentResolver;
    private static final int FAVOURITE_LOADER = 23;

    static final String MOVIE_URI = "URI";

    private Uri mUri;

    private static final String[] FAVOURITE_COLUMNS = {
            MovieEntry._ID,
            MovieEntry.COLUMN_MOVIE_ID,
            MovieEntry.COLUMN_MOVIE_TITLE,
            MovieEntry.COLUMN_MOVIE_PATH,
            MovieEntry.COLUMN_MOVIE_DATE,
            MovieEntry.COLUMN_MOVE_RATING,
            MovieEntry.COLUMN_MOVIE_OVERVIEW,
    };

    // These indices are tied to FAVOURITE_COLUMNS.
    public static final int COL_ID = 0;
    public static final int COL_MOVIE_ID = 1;
    public static final int COL_MOVIE_TITLE = 2;
    public static final int COL_MOVIE_PATH = 3;
    public static final int COL_MOVIE_DATE = 4;
    public static final int COL_MOVE_RATING = 5;
    public static final int COL_MOVIE_OVERVIEW = 6;

    private TextView mTitleTv;
    private ImageView mThumbIv;
    private TextView mReleaseDateTv;
    private TextView mVoteAverageTv;
    private TextView mPlotTv;
    private LinearLayout mFavouriteView;
    private ImageView mFavouriteIcon;
    private TextView mFavouriteTv;

    public FavouriteDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            mUri = args.getParcelable(FavouriteDetailFragment.MOVIE_URI);
        }
        View rootView = inflater.inflate(R.layout.detail_fragment, container, false);
        mContentResolver = getActivity().getContentResolver();

        mTitleTv = (TextView) rootView.findViewById(R.id.tv_title);
        mThumbIv = (ImageView) rootView.findViewById(R.id.iv_movie_thumb);
        mReleaseDateTv = (TextView) rootView.findViewById(R.id.tv_release_date);
        mVoteAverageTv = (TextView) rootView.findViewById(R.id.tv_rating);
        mPlotTv = (TextView) rootView.findViewById(R.id.tv_overview);

        mFavouriteView = (LinearLayout) rootView.findViewById(R.id.favourite_view);
        mFavouriteIcon = (ImageView) rootView.findViewById(R.id.favorite_icon);
        mFavouriteTv = (TextView) rootView.findViewById(R.id.favourite_text_view);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(FAVOURITE_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (null != mUri) {
            return new CursorLoader(
                    getActivity(),
                    mUri,
                    FAVOURITE_COLUMNS,
                    null,
                    null,
                    null
            );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            final String movieId = data.getString(COL_MOVIE_ID);
            final String movieTitle = data.getString(COL_MOVIE_TITLE);
            mTitleTv.setText(movieTitle);

            final String posterPath = data.getString(COL_MOVIE_PATH);
            String myUrl = null;
            try {
                myUrl = QueryUtils.getImageUrl(posterPath);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Picasso.with(getActivity()).load(myUrl).into(mThumbIv);

            final String releaseDate = data.getString(COL_MOVIE_DATE);
            mReleaseDateTv.setText(releaseDate);

            final String voteAverage = data.getString(COL_MOVE_RATING);
            mVoteAverageTv.setText(voteAverage);

            final String moviePlot = data.getString(COL_MOVIE_OVERVIEW);
            mPlotTv.setText(moviePlot);

            mFavouriteIcon.setImageResource(R.drawable.ic_favorite_24dp);
            mFavouriteTv.setText(R.string.favourited);

            mFavouriteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String favouriteText = mFavouriteTv.getText().toString();

                    //Check whether the movie is favourited
                    if (favouriteText.equals(getString(R.string.favourited))) {
                        // Unmark as favourite and delete it from the database

                        mContentResolver.delete(
                                mUri,
                                null,
                                null
                        );

                        // Update favourite icon and text
                        setFavouriteImageText(false, mFavouriteIcon, mFavouriteTv);
                    } else {
                        // Add as favourite and insert it into the database
                        ContentValues values = new ContentValues();
                        values.put(MovieEntry.COLUMN_MOVIE_ID, movieId);
                        values.put(MovieEntry.COLUMN_MOVIE_TITLE, movieTitle);
                        values.put(MovieEntry.COLUMN_MOVIE_PATH, posterPath);
                        values.put(MovieEntry.COLUMN_MOVIE_DATE, releaseDate);
                        values.put(MovieEntry.COLUMN_MOVE_RATING, voteAverage);
                        values.put(MovieEntry.COLUMN_MOVIE_OVERVIEW, moviePlot);

                        Uri newUri = mContentResolver.insert(MovieEntry.CONTENT_URI, values);

                        // Update favourite icon and text
                        setFavouriteImageText(true, mFavouriteIcon, mFavouriteTv);
                    }
                }
            });
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
