package me.anky.popularmovies;

import android.support.v4.app.Fragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.text.DecimalFormat;

import static me.anky.popularmovies.data.MovieContract.MovieEntry;

/**
 * Created by anky_ on 4/11/2016.
 */

public class DetailFragment extends Fragment {
    private static final String LOG_TAG = DetailFragment.class.getSimpleName();
    ContentResolver mContentResolver;

    private ImageView mFavouriteIcon;
    private TextView mFavouriteTextView;

    public DetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.detail_fragment, container, false);

        mContentResolver = getActivity().getContentResolver();

        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra("movieData")) {
            PopularMovie movieData = intent.getParcelableExtra("movieData");

            //Define the parameters in the movieData
            final String movieId = movieData.getMovieId();
            final String originalTitle = movieData.getOriginalTitle();
            final String posterPath = movieData.getPosterPath();
            final String releaseDate = movieData.getRleaseDate();
            final double voteAverage = movieData.getVoteAverage();
            final String overview = movieData.getOverview();

            // Display movie title in a text view
            TextView titleTV = (TextView) rootView.findViewById(R.id.tv_title);
            titleTV.setText(originalTitle);

            // Show the thumbnail of the movie in an image view
            ImageView thumbIV = (ImageView) rootView.findViewById(R.id.iv_movie_thumb);

            String myUrl = null;
            try {
                myUrl = QueryUtils.getImageUrl(posterPath);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Picasso.with(getActivity()).load(myUrl).into(thumbIV);

            // Display the release date in a text view
            TextView releaseDateTV = (TextView) rootView.findViewById(R.id.tv_release_date);
            releaseDateTV.setText(releaseDate);

            // Convert the double vote average to String and display it in a text view
            final TextView voteAverageTV = (TextView) rootView.findViewById(R.id.tv_rating);
            // Change decimal digits from 2 to 1
            DecimalFormat df = new DecimalFormat("#.#");
            String voteAverageString = df.format(voteAverage);
            voteAverageTV.setText("Rating: " + voteAverageString + "/10");

            // Display the overview of the movie
            TextView plotTV = (TextView) rootView.findViewById(R.id.tv_overview);
            plotTV.setText(overview);

            LinearLayout favouriteView = (LinearLayout) rootView.findViewById(R.id.favourite_view);
            mFavouriteIcon = (ImageView) rootView.findViewById(R.id.favorite_icon);
            mFavouriteTextView = (TextView) rootView.findViewById(R.id.favourite_text_view);

            // Set favourite image and text after figuring out whether the movie is favourited
            setFavouriteImageText(isFavourited(movieId));

            favouriteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String favouriteText = mFavouriteTextView.getText().toString();

                    //Check whether the movie is favourited
                    if (favouriteText.equals(getString(R.string.favourited))) {
                        // Unmark as favourite and delete it from the database

                        mContentResolver.delete(
                                MovieEntry.CONTENT_URI,
                                MovieEntry.COLUMN_MOVIE_ID + "=?",
                                new String[]{movieId}
                        );

                        // Update favourite icon and text
                        setFavouriteImageText(false);
                    } else {
                        // Add as favourite and insert it into the database
                        ContentValues values = new ContentValues();
                        values.put(MovieEntry.COLUMN_MOVIE_ID, movieId);
                        values.put(MovieEntry.COLUMN_MOVIE_TITLE, originalTitle);
                        values.put(MovieEntry.COLUMN_MOVIE_PATH, posterPath);
                        values.put(MovieEntry.COLUMN_MOVIE_DATE, releaseDate);
                        values.put(MovieEntry.COLUMN_MOVE_RATING, voteAverageTV.getText().toString());
                        values.put(MovieEntry.COLUMN_MOVIE_OVERVIEW, overview);

                        Uri newUri = mContentResolver.insert(MovieEntry.CONTENT_URI, values);

                        // Update favourite icon and text
                        setFavouriteImageText(true);
                    }
                }
            });
        }
        return rootView;
    }

    public void setFavouriteImageText(boolean favourite) {
        if (favourite) {
            mFavouriteIcon.setImageResource(R.drawable.ic_favorite_24dp);
            mFavouriteTextView.setText(R.string.favourited);
        } else {
            mFavouriteIcon.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            mFavouriteTextView.setText(R.string.mark_as_favourite);
        }
    }

    public boolean isFavourited(String movieId) {
        String[] projection = {MovieEntry.COLUMN_MOVIE_ID};
        String selection = MovieEntry.COLUMN_MOVIE_ID + "=?";
        String[] selectionArgs = new String[]{movieId};

        Cursor cursor = mContentResolver.query(
                MovieEntry.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null
        );
        if (cursor != null & cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(LOG_TAG, "DestFragment onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "DestFragment onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(LOG_TAG, "DestFragment onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(LOG_TAG, "DestFragment onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(LOG_TAG, "DestFragment onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "DestFragment onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(LOG_TAG, "DestFragment onDetach");
    }
}
