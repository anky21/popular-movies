package me.anky.popularmovies.Trailer;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.anky.popularmovies.BuildConfig;
import me.anky.popularmovies.MovieActivityFragment;
import me.anky.popularmovies.PopularMovie;
import me.anky.popularmovies.R;
import me.anky.popularmovies.Utilities;

/**
 * Created by anky_ on 4/11/2016.
 */

public class TrailerFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<List<MovieTrailer>> {
    private static final String LOG_TAG = TrailerFragment.class.getSimpleName();

    private TextView mEmptyTrailerListTextView;

    private static final String YOUTUBE_REQUEST_URL = "https://youtu.be/";

    private static final int TRAILER_LOADER_ID = 100;

    private MovieTrailerAdapter movieTrailerAdapter;

    private String mMovieId;

    private String mTrailer1;
    private String mMovieTitle;

    public TrailerFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.share_action, menu);

        MenuItem menuItem = menu.findItem(R.id.action_share);

        ShareActionProvider mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(createShareIntent());
        } else {
            Log.d(LOG_TAG, "Share Action Provider is null");
        }
    }

    private Intent createShareIntent() {
        String shareMessage = "Check this movie out: " + mMovieTitle + ", and the Trailer: " + mTrailer1;
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        return intent;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialise the Loader here if it's a phone
        if(!Utilities.isTablet(getContext())){
            LoaderManager loaderManager = getActivity().getLoaderManager();
            loaderManager.initLoader(TRAILER_LOADER_ID, null, this);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Initialise the Loader
        LoaderManager loaderManager = getActivity().getLoaderManager();
        loaderManager.initLoader(TRAILER_LOADER_ID, null, this);
    }

    @Override
    public Loader<List<MovieTrailer>> onCreateLoader(int i, Bundle bundle) {
        Bundle args = getArguments();
        if (args != null) {
            PopularMovie movieData = args.getParcelable("MOVIE_DATA");
            mMovieId = movieData.getMovieId();
            mMovieTitle = movieData.getOriginalTitle();
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

        movieTrailerAdapter.addAll(movieTrailers);
        if (movieTrailers != null && !movieTrailers.isEmpty()) {
            mTrailer1 = YOUTUBE_REQUEST_URL + movieTrailerAdapter.getItem(0).getTrailerKey();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<MovieTrailer>> loader) {
        movieTrailerAdapter.clear();
    }

    @Override
    public void onResume() {
        super.onResume();
        LoaderManager loaderManager = getActivity().getLoaderManager();

        /**
         * Initialise the loader. pass in the int ID constant defined above and pass in null for
         * the bundle. Pass in this activity for the LoaderCallbacks parameter
         */
        loaderManager.restartLoader(TRAILER_LOADER_ID, null, this);
    }
}
