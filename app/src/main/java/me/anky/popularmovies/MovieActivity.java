package me.anky.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import me.anky.popularmovies.Review.MovieReviewFragment;
import me.anky.popularmovies.Trailer.TrailerFragment;

public class MovieActivity extends AppCompatActivity
        implements MovieActivityFragment.MovieCallback {
    private static final String LOG_TAG = MovieActivity.class.getSimpleName();
    public boolean mTwoPane;

    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    private static final String TRAILERFRAGMENT_TAG = "TFTAG";
    private static final String REVIEWFRAGMENT_TAG = "RFTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_activity);

        if (findViewById(R.id.fragment_detail_container) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_detail_container, new DetailFragment(),
                                DETAILFRAGMENT_TAG).commit();
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_trailer_container, new TrailerFragment(),
//                                TRAILERFRAGMENT_TAG).commit();
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_review_container, new MovieReviewFragment(),
//                                REVIEWFRAGMENT_TAG).commit();
            } else {
                mTwoPane = false;
            }
        }
    }

    @Override
    public void onItemSelected(PopularMovie movieData) {
        if (mTwoPane) {
            Log.v(LOG_TAG, "Testing mTwoPane " + mTwoPane);
            Bundle args = new Bundle();
            args.putParcelable("MOVIE_DATA", movieData);
            // Replace DetailFragment
            DetailFragment df = new DetailFragment();
            df.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_detail_container, df, DETAILFRAGMENT_TAG)
                    .commit();

            // Replace TrailerFragment
            TrailerFragment tf = new TrailerFragment();
            tf.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_trailer_container, tf, TRAILERFRAGMENT_TAG)
                    .commit();

            // Replace MovieReviewFragment
            MovieReviewFragment mrf = new MovieReviewFragment();
            mrf.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_review_container, mrf, REVIEWFRAGMENT_TAG)
                    .commit();
        } else {
            Intent intent = new Intent(this, DetailedActivity.class);
            intent.putExtra("movieData", movieData);
            startActivity(intent);
        }
    }
}
