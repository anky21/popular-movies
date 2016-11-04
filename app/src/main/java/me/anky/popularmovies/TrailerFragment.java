package me.anky.popularmovies;

import android.support.v4.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anky_ on 4/11/2016.
 */

public class TrailerFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<List<MovieTrailer>> {
    private static final String LOG_TAG = TrailerFragment.class.getSimpleName();

    private static final int TRAILER_LOADER_ID = 1;

    private MovieTrailerAdapter movieTrailerAdapter;

    // Movie ID for testing purpose
    private String movieId = "284052";

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.trailer_fragment, container, false);

        movieTrailerAdapter = new MovieTrailerAdapter(getActivity(),
                new ArrayList<MovieTrailer>());

        ListView listView = (ListView) rootView.findViewById(R.id.trailer_list);

        listView.setAdapter(movieTrailerAdapter);
        return rootView;
    }

    @Override
    public Loader<List<MovieTrailer>> onCreateLoader(int i, Bundle bundle) {
        Uri baseUri = Uri.parse(MovieActivityFragment.MOVIE_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendPath(movieId)
                .appendPath("videos")
                .appendQueryParameter("api_key", BuildConfig.OPEN_MOVIE_API_KEY);
        return new TrailerLoader(getContext(), uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<MovieTrailer>> loader, List<MovieTrailer> movieTrailers) {
        Log.v(LOG_TAG, "Testing: Load finished");
        if (movieTrailers != null && !movieTrailers.isEmpty()) {
            movieTrailerAdapter.addAll(movieTrailers);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<MovieTrailer>> loader) {
        movieTrailerAdapter.clear();
    }
}
