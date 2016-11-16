package me.anky.popularmovies;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import me.anky.popularmovies.Review.MovieReviewFragment;
import me.anky.popularmovies.Trailer.TrailerFragment;

/**
 * Created by anky_ on 8/11/2016.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private Bundle fragmentBundle;

    public FragmentAdapter(Context context, FragmentManager fm, Bundle data){
        super(fm);
        fragmentBundle = data;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            final DetailFragment df = new DetailFragment();
            df.setArguments(fragmentBundle);
            return df;
        } else if (position == 1) {
            final TrailerFragment tf = new TrailerFragment();
            tf.setArguments(fragmentBundle);
            return tf;
        } else {
            final MovieReviewFragment mrf = new MovieReviewFragment();
            mrf.setArguments(fragmentBundle);
            return mrf;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.page_detail_title);
        } else if (position == 1) {
            return mContext.getString(R.string.page_trailer_title);
        } else {
            return mContext.getString(R.string.page_review_title);
        }
    }
}
