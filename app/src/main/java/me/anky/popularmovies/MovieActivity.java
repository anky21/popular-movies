package me.anky.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import java.util.Arrays;

public class MovieActivity extends AppCompatActivity {
    private static final String LOG_TAG = MovieActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_activity);

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

        GridView gridView = (GridView)findViewById(R.id.movie_activity_grid_view);

        PopularMovieAdapter popularMovieAdapter = new PopularMovieAdapter(this,
                Arrays.asList(popularMovies));

        gridView.setAdapter(popularMovieAdapter);
    }

}
