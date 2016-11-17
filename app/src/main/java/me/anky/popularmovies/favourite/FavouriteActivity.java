package me.anky.popularmovies.favourite;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.anky.popularmovies.R;
import me.anky.popularmovies.Utilities;

public class FavouriteActivity extends AppCompatActivity
        implements FavouriteFragment.Callback {

    private final String LOG_TAG = FavouriteActivity.class.getSimpleName();

    private static final String FAVOURITEDETAILFRAGMENT_TAG = "FDFTAG";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        if(Utilities.isTablet(getApplicationContext())){
            if(savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.favourite_detail_contaier, new FavouriteDetailFragment(),
                                FAVOURITEDETAILFRAGMENT_TAG).commit();
            }
        }
    }

    @Override
    public void onFavouriteSelected(Uri contentUri) {
        if(Utilities.isTablet(getApplicationContext())){
            Bundle args = new Bundle();
            args.putParcelable(FavouriteDetailFragment.MOVIE_URI, contentUri);

            FavouriteDetailFragment fragment = new FavouriteDetailFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.favourite_detail_contaier, fragment,
                            FAVOURITEDETAILFRAGMENT_TAG).commit();
        } else {
            Intent intent = new Intent(this, FavouriteDetailActivity.class);
            intent.setData(contentUri);
            startActivity(intent);
        }
    }
}
