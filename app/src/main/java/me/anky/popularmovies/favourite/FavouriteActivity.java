package me.anky.popularmovies.favourite;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.anky.popularmovies.R;

public class FavouriteActivity extends AppCompatActivity
        implements FavouriteFragment.Callback {

    private final String LOG_TAG = FavouriteActivity.class.getSimpleName();

    private boolean mTwoPane;

    private static final String FAVOURITEDETAILFRAGMENT_TAG = "FDFTAG";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        if(findViewById(R.id.favourite_detail_contaier) != null){
            mTwoPane = true;
            if(savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.favourite_detail_contaier, new FavouriteDetailFragment(),
                                FAVOURITEDETAILFRAGMENT_TAG).commit();
            } else {
                mTwoPane = false;

            }
        }
    }

    @Override
    public void onFavouriteSelected(Uri contentUri) {
        if(mTwoPane){
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
