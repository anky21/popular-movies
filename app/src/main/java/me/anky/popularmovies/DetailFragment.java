package me.anky.popularmovies;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.text.DecimalFormat;

/**
 * Created by anky_ on 4/11/2016.
 */

public class DetailFragment extends Fragment {
    private static final String LOG_TAG = DetailFragment.class.getSimpleName();

    public DetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.detail_fragment, container, false);
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra("movieData")) {
            PopularMovie movieData = intent.getParcelableExtra("movieData");

            //Define the parameters in the movieData
            String originalTitle = movieData.getOriginalTitle();
            String posterPath = movieData.getPosterPath();
            String releaseDate = movieData.getRleaseDate();
            double voteAverage = movieData.getVoteAverage();
            String overview = movieData.getOverview();

            // Display movie title in a text view
            TextView titleTV = (TextView) rootView.findViewById(R.id.tv_title);
            titleTV.setText(originalTitle);

            // Show the thumbnail of the movie in an image view
            ImageView thumbIV = (ImageView) rootView.findViewById(R.id.iv_movie_thumb);
            String myUrl = null;
            try {
                myUrl = QueryUtils.getImageUrl(posterPath);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Picasso.with(getActivity()).load(myUrl).into(thumbIV);

            // Display the release date in a text view
            TextView releaseDateTV = (TextView) rootView.findViewById(R.id.tv_release_date);
            releaseDateTV.setText(releaseDate);

            // Convert the double vote average to String and display it in a text view
            TextView voteAverageTV = (TextView) rootView.findViewById(R.id.tv_rating);
            // Change decimal digits from 2 to 1
            DecimalFormat df = new DecimalFormat("#.#");
            String voteAverageString = df.format(voteAverage);
            voteAverageTV.setText("Rating: " + voteAverageString + "/10");

            // Display the overview of the movie
            TextView plotTV = (TextView) rootView.findViewById(R.id.tv_overview);
            plotTV.setText(overview);

        }
        return rootView;
    }
}
