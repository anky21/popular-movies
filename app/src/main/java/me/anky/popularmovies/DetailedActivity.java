package me.anky.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.text.DecimalFormat;

public class DetailedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("movieData")) {
            PopularMovie movieData = intent.getParcelableExtra("movieData");

            //Define the parameters in the movieData
            String originalTitle = movieData.getOriginalTitle();
            String posterPath = movieData.getPosterPath();
            String releaseDate = movieData.getRleaseDate();
            double voteAverage = movieData.getVoteAverage();
            String overview = movieData.getOverview();

            // Display movie title in a text view
            TextView titleTV = (TextView) findViewById(R.id.tv_title);
            titleTV.setText(originalTitle);

            // Show the thumbnail of the movie in an image view
            ImageView thumbIV = (ImageView) findViewById(R.id.iv_movie_thumb);
            String myUrl = null;
            try {
                myUrl = QueryUtils.getImageUrl(posterPath);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            Picasso.with(this).load(myUrl).into(thumbIV);

            // Display the release date in a text view
            TextView releaseDateTV = (TextView) findViewById(R.id.tv_release_date);
            releaseDateTV.setText(releaseDate);

            // Convert the double vote average to String and display it in a text view
            TextView voteAverageTV = (TextView) findViewById(R.id.tv_rating);
            // Change decimal digits from 2 to 1
            DecimalFormat df = new DecimalFormat("#.#");
            String voteAverageString = df.format(voteAverage);
            voteAverageTV.setText("Rating: "+ voteAverageString + "/10");

            // Display the overview of the movie
            TextView plotTV = (TextView) findViewById(R.id.tv_overview);
            plotTV.setText(overview);

        }
    }
}
