package me.anky.popularmovies;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anky_ on 4/11/2016.
 */

public class TrailerFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<List<MovieTrailer>> {
    private static final String LOG_TAG = TrailerFragment.class.getSimpleName();

    private TextView mEmptyTrailerListTextView;

    private static final String YOUTUBE_REQUEST_URL = "https://youtu.be/";

    private static final int TRAILER_LOADER_ID = 1;

    private MovieTrailerAdapter movieTrailerAdapter;

    private String mMovieId;

    public TrailerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //            // Get a ref to the LoaderManager, in order to interact with loaders
        LoaderManager loaderManager = getActivity().getLoaderManager();

        /**
         * Initialise the loader. pass in the int ID constant defined above and pass in null for
         * the bundle. Pass in this activity for the LoaderCallbacks parameter
         */
        loaderManager.initLoader(TRAILER_LOADER_ID, null, this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Restart the Loader onResume
        getActivity().getLoaderManager().restartLoader(TRAILER_LOADER_ID, null, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.trailer_fragment, container, false);

        movieTrailerAdapter = new MovieTrailerAdapter(getActivity(),
                new ArrayList<MovieTrailer>());

        ListView listView = (ListView) rootView.findViewById(R.id.trailer_list);
        mEmptyTrailerListTextView = (TextView) rootView.findViewById(R.id.empty_trailer_list);
        listView.setEmptyView(mEmptyTrailerListTextView);
        listView.setAdapter(movieTrailerAdapter);

        // Open Youtube or a website to show the Trailer
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MovieTrailer movieTrailer = movieTrailerAdapter.getItem(i);

                String trailerKey = movieTrailer.getTrailerKey();

                Uri youtubeUri = Uri.parse(YOUTUBE_REQUEST_URL + trailerKey);

                Intent youtubeIntent = new Intent(Intent.ACTION_VIEW, youtubeUri);
                startActivity(youtubeIntent);
            }
        });

        return rootView;
    }

    @Override
    public Loader<List<MovieTrailer>> onCreateLoader(int i, Bundle bundle) {
        // Read Movie ID from the Intent
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra("movieData")) {
            PopularMovie movieData = intent.getParcelableExtra("movieData");
            mMovieId = movieData.getMovieId();
        }

        Uri baseUri = Uri.parse(MovieActivityFragment.MOVIE_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendPath(mMovieId)
                .appendPath("videos")
                .appendQueryParameter("api_key", BuildConfig.OPEN_MOVIE_API_KEY);
        return new TrailerLoader(getContext(), uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<MovieTrailer>> loader, List<MovieTrailer> movieTrailers) {
        movieTrailerAdapter.clear();

        mEmptyTrailerListTextView.setText(R.string.no_trailers_found);

        if (movieTrailers != null && !movieTrailers.isEmpty()) {
            movieTrailerAdapter.addAll(movieTrailers);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<MovieTrailer>> loader) {
        movieTrailerAdapter.clear();
    }
}
