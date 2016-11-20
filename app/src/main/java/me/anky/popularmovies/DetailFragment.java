package me.anky.popularmovies;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static me.anky.popularmovies.data.MovieContract.MovieEntry;

/**
 * Created by anky_ on 4/11/2016.
 */

public class DetailFragment extends Fragment {
    private static final String LOG_TAG = DetailFragment.class.getSimpleName();
    ContentResolver mContentResolver;

    private Unbinder unbinder;

    @BindView(R.id.favorite_icon)
    ImageView mFavouriteIcon;
    @BindView(R.id.favourite_text_view)
    TextView mFavouriteTextView;
    @BindView(R.id.tv_title)
    TextView titleTV;
    @BindView(R.id.iv_movie_thumb)
    ImageView thumbIV;
    @BindView(R.id.tv_release_date)
    TextView releaseDateTV;
    @BindView(R.id.tv_rating)
    TextView voteAverageTV;
    @BindView(R.id.tv_overview)
    TextView plotTV;
    @BindView(R.id.favourite_view)
    LinearLayout favouriteView;

    public DetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.detail_fragment, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        mContentResolver = getActivity().getContentResolver();
        Bundle args = getArguments();
        if (args != null) {
            PopularMovie mMovieData = args.getParcelable("MOVIE_DATA");

            //Define the parameters in the movieData
            final String movieId = mMovieData.getMovieId();
            final String originalTitle = mMovieData.getOriginalTitle();
            final String posterPath = mMovieData.getPosterPath();
            final String releaseDate = mMovieData.getRleaseDate();
            final double voteAverage = mMovieData.getVoteAverage();
            final String overview = mMovieData.getOverview();

            titleTV.setText(originalTitle);

            String myUrl = null;
            try {
                myUrl = QueryUtils.getImageUrl(posterPath);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            Picasso.with(getActivity())
                    .load(myUrl)
                    .placeholder(R.drawable.loading_icon) // Displays this image while loading
                    .error(R.drawable.errorstop)    // Displays this image when there is an error
                    .into(thumbIV);

            releaseDateTV.setText(releaseDate);

            // Change decimal digits from 2 to 1
            DecimalFormat df = new DecimalFormat("#.#");
            String voteAverageString = df.format(voteAverage);
            voteAverageTV.setText("Rating: " + voteAverageString + "/10");

            plotTV.setText(overview);

            // Set favourite image and text after figuring out whether the movie is favourited
            setFavouriteImageText(isFavourited(movieId), mFavouriteIcon, mFavouriteTextView);

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
                        setFavouriteImageText(false, mFavouriteIcon, mFavouriteTextView);
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
                        setFavouriteImageText(true, mFavouriteIcon, mFavouriteTextView);
                    }
                }
            });
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public static void setFavouriteImageText(boolean favourite, ImageView imageView, TextView textView) {
        if (favourite) {
            imageView.setImageResource(R.drawable.ic_favorite_24dp);
            textView.setText(R.string.favourited);
        } else {
            imageView.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            textView.setText(R.string.mark_as_favourite);
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
}
