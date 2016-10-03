package me.anky.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A fragment containing the grid view of movie posters
 */
public class MovieActivityFragment extends Fragment {

    private PopularMovieAdapter popularMovieAdapter;

    private ArrayList<PopularMovie> movieList;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null || !savedInstanceState.containsKey("movies")) {
            movieList = new ArrayList<PopularMovie>(Arrays.asList(popularMovies));
        } else {
            movieList = savedInstanceState.getParcelableArrayList("movies");
        }
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
        popularMovieAdapter = new PopularMovieAdapter(getActivity(), movieList);

        // Get a reference to the GridView, and attach this adapter to it.
        GridView gridView = (GridView) rootView.findViewById(R.id.movie_activity_grid_view);
        gridView.setAdapter(popularMovieAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), DetailedActivity.class);
                PopularMovie movieData = movieList.get(i);
                intent.putExtra("movieData", movieData);
                startActivity(intent);
            }
        });

        return rootView;
    }

}
