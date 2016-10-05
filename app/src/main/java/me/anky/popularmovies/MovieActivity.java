package me.anky.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MovieActivity extends AppCompatActivity {
    private static final String LOG_TAG = MovieActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MovieActivityFragment()).commit();
        }
    }
}
