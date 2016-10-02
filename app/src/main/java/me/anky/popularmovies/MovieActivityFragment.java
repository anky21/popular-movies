package me.anky.popularmovies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.Arrays;

/**
 * A fragment containing the grid view of movie posters
 */
public class MovieActivityFragment extends Fragment {

    private PopularMovieAdapter popularMovieAdapter;

    PopularMovie[] popularMovies = {
            new PopularMovie(R.drawable.a),
            new PopularMovie(R.drawable.b),
            new PopularMovie(R.drawable.c),
            new PopularMovie(R.drawable.d),
            new PopularMovie(R.drawable.a),
            new PopularMovie(R.drawable.b),
            new PopularMovie(R.drawable.c),
            new PopularMovie(R.drawable.d),
            new PopularMovie(R.drawable.a),
            new PopularMovie(R.drawable.b),
            new PopularMovie(R.drawable.c),
            new PopularMovie(R.drawable.d),
            new PopularMovie(R.drawable.a),
            new PopularMovie(R.drawable.b),
            new PopularMovie(R.drawable.c),
            new PopularMovie(R.drawable.d),
    };

    public MovieActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.movie_activity_fragment, container, false);
        popularMovieAdapter = new PopularMovieAdapter(getActivity(),
                Arrays.asList(popularMovies));

        // Get a reference to the GridView, and attach this adapter to it.
        GridView gridView = (GridView) rootView.findViewById(R.id.movie_activity_grid_view);
        gridView.setAdapter(popularMovieAdapter);

        return rootView;
    }

}
