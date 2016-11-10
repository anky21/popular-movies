package me.anky.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.TextView;

public class FavouriteActivity extends AppCompatActivity {
    // Identifier for the Loader
    private static final int FAVOURITE_LOADER = 0;
    FavouriteCursorAdapter mCursorAdapter;
    private TextView mEmptyStateTextView;
    private GridView favouriteGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
    }
}
