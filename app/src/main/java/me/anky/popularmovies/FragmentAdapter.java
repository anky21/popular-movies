package me.anky.popularmovies;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import me.anky.popularmovies.Review.MovieReviewFragment;
import me.anky.popularmovies.Trailer.TrailerFragment;

/**
 * Created by anky_ on 8/11/2016.
 */

public class FragmentAdapter extends FragmentPagerAdapter {

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
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
}
