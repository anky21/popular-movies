package me.anky.popularmovies.Review;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.anky.popularmovies.BuildConfig;
import me.anky.popularmovies.MovieActivityFragment;
import me.anky.popularmovies.PopularMovie;
import me.anky.popularmovies.R;

/**
 * Created by anky_ on 5/11/2016.
 */

public class MovieReviewFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<List<MovieReview>> {
    private static final String LOG_TAG = MovieReviewFragment.class.getSimpleName();

    private static final int REVIEW_LOADER_ID = 10;

    private String mMovieId;

    private MovieReviewAdapter movieReviewAdapter;

    private TextView mEmptyReviewListTextView;

    public MovieReviewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialise the loader
        LoaderManager loaderManager = getActivity().getLoaderManager();
        loaderManager.initLoader(REVIEW_LOADER_ID, null, this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.review_fragment, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.review_list);
        mEmptyReviewListTextView = (TextView)rootView.findViewById(R.id.empty_review_textview);
        movieReviewAdapter = new MovieReviewAdapter(getActivity(),
                new ArrayList<MovieReview>());

        listView.setEmptyView(mEmptyReviewListTextView);
        listView.setAdapter(movieReviewAdapter);

        return rootView;
    }

    @Override
    public Loader<List<MovieReview>> onCreateLoader(int i, Bundle bundle) {
        // Read Movie ID from the Intent
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra("movieData")) {
            PopularMovie movieData = intent.getParcelableExtra("movieData");
            mMovieId = movieData.getMovieId();
        }

        Uri baseUri = Uri.parse(MovieActivityFragment.MOVIE_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendPath(mMovieId)
                .appendPath("reviews")
                .appendQueryParameter("api_key", BuildConfig.OPEN_MOVIE_API_KEY);
        return new ReviewLoader(getContext(), uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<MovieReview>> loader, List<MovieReview> movieReviews) {
        movieReviewAdapter.clear();

        mEmptyReviewListTextView.setText(R.string.no_reviews_found);

        if (movieReviews != null && !movieReviews.isEmpty()) {
            movieReviewAdapter.addAll(movieReviews);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<MovieReview>> loader) {
        movieReviewAdapter.clear();
    }
}