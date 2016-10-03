package me.anky.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DetailedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        Intent intent = getIntent();
        if(intent != null && intent.hasExtra("movieData")){
            PopularMovie movieData = intent.getParcelableExtra("movieData");

            int posterResourceId = movieData.getPosterResourceId();

            TextView textView = (TextView)findViewById(R.id.text_view);
            textView.setText(posterResourceId);
        }
    }
}
