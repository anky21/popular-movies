package me.anky.popularmovies.favourite;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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
        Log.v(LOG_TAG, "Testing onCreate");
        if(findViewById(R.id.favourite_detail_contaier) != null){
            mTwoPane = true;
            Log.v("Testing", "Testing mTwoPane = " + mTwoPane);
            if(savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.favourite_detail_contaier, new FavouriteDetailFragment(),
                                FAVOURITEDETAILFRAGMENT_TAG).commit();
            } else {
                mTwoPane = false;
                Log.v("Testing", "Testing mTwoPane = " + mTwoPane);
            }
        }
    }

    @Override
    public void onItemSelected(Uri contentUri) {
        if(mTwoPane){
            Log.v("Testing", "Testing 2 pane " + contentUri);
            Bundle args = new Bundle();
            args.putParcelable(FavouriteDetailFragment.MOVIE_URI, contentUri);

            FavouriteDetailFragment fragment = new FavouriteDetailFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.favourite_detail_contaier, fragment,
                            FAVOURITEDETAILFRAGMENT_TAG).commit();
        } else {
            Log.v("Testing", "Testing phone " + contentUri);
            Intent intent = new Intent(this, FavouriteDetailActivity.class);
            intent.setData(contentUri);
            startActivity(intent);
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(LOG_TAG, "MainActivity onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "MainActivity onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LOG_TAG, "MainActivity onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "MainActivity onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LOG_TAG, "MainActivity onStart");
    }
}
