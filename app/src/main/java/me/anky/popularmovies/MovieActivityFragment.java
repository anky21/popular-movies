package me.anky.popularmovies;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment containing the grid view of movie posters
 */
public class MovieActivityFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<List<PopularMovie>> {
    private static final int MOVIE_LOADER_ID = 1;

    private PopularMovieAdapter popularMovieAdapter;

    //Need an API key to request data from the Movie DB
    private static final String API_KEY = "USE YOUR OWN API KEY";

    private static final String MOVIE_REQUEST_URL =
            "http://api.themoviedb.org/3/discover/movie";

    private ArrayList<PopularMovie> movieList;

    public MovieActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add this line to handle menu events
        setHasOptionsMenu(true);

        if (savedInstanceState == null || !savedInstanceState.containsKey("movies")) {
            movieList = new ArrayList<PopularMovie>();
        } else {
            movieList = savedInstanceState.getParcelableArrayList("movies");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.moviefragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("movies", movieList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.movie_activity_fragment, container, false);
        // Create a new {@link ArrayAdapter} of movies
        popularMovieAdapter = new PopularMovieAdapter(getActivity(),
                new ArrayList<PopularMovie>());

        // Get a reference to the GridView, and attach this adapter to it.
        GridView gridView = (GridView) rootView.findViewById(R.id.movie_activity_grid_view);
        gridView.setAdapter(popularMovieAdapter);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager cm = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a ref to the LoaderManager, in order to interact with loaders
            LoaderManager loaderManager = getActivity().getLoaderManager();

            /**
             * Initialise the loader. pass in the int ID constant defined above and pass in null for
             * the bundle. Pass in this activity for the LoaderCallbacks parameter
             */
            loaderManager.initLoader(MOVIE_LOADER_ID, null, this);
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), DetailedActivity.class);
                PopularMovie movieData = popularMovieAdapter.getItem(i);
                intent.putExtra("movieData", movieData);
                startActivity(intent);
            }
        });
        return rootView;
    }

    @Override
    public Loader<List<PopularMovie>> onCreateLoader(int i, Bundle bundle) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getContext());
        String sortBy = sharedPreferences.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        String releaseDate = "2015-08-01"; // Only show movies after this release date
        String minimumVoteCount = "200"; // Movies with equal to or greater than minimum vote count

        Uri baseUri = Uri.parse(MOVIE_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("sort_by", sortBy);
        uriBuilder.appendQueryParameter("api_key", API_KEY);
        uriBuilder.appendQueryParameter("release_date.gte", releaseDate);
        uriBuilder.appendQueryParameter("vote_count.gte", minimumVoteCount);

        // Create a new loader for the given URL
        return new MovieLoader(getContext(), uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<PopularMovie>> loader, List<PopularMovie> popularMovies) {
        Log.v(getTag(), "Testing: load finished");
        // Clear the adapter of previous movie data
        popularMovieAdapter.clear();

        /**
         * If there is a valid list of {@link PopularMovie}s, then add them to the adapter's data
         * set. This will trigger the ListView to update.
         */
        if (popularMovies != null && !popularMovies.isEmpty()) {
            popularMovieAdapter.addAll(popularMovies);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<PopularMovie>> loader) {
        // Loader reset to clear out existing data
        popularMovieAdapter.clear();
    }
}
