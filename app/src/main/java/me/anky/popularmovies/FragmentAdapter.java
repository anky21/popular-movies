package me.anky.popularmovies;

import android.content.Context;
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

    public FragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new DetailFragment();
        } else if (position == 1) {
            return new TrailerFragment();
        } else {
            return new MovieReviewFragment();
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
