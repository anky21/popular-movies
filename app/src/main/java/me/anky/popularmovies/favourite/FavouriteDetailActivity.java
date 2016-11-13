package me.anky.popularmovies.favourite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.anky.popularmovies.R;

public class FavouriteDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_detail);

        if (savedInstanceState == null){
            Bundle args = new Bundle();
            args.putParcelable(FavouriteDetailFragment.MOVIE_URI, getIntent().getData());

            FavouriteDetailFragment fragment = new FavouriteDetailFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_favourite_detail, fragment).commit();
        }

    }
}
