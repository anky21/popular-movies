package me.anky.popularmovies.Review;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import me.anky.popularmovies.R;

/**
 * Created by anky_ on 6/11/2016.
 */

public class MovieReviewAdapter extends ArrayAdapter<MovieReview> {
    public MovieReviewAdapter(Context context, List<MovieReview> movieReviews) {
        super(context, 0, movieReviews);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View reviewListView = convertView;
        if (reviewListView == null) {
            reviewListView = LayoutInflater.from(getContext()).inflate(
                    R.layout.review_list, parent, false);
        }
        MovieReview currentReview = getItem(position);

        String author = currentReview.getAuthor();
        String review = currentReview.getReview();

        TextView authorTextView = (TextView) reviewListView.findViewById(R.id.author_textview);
        TextView reviewTextView = (TextView) reviewListView.findViewById(R.id.review_textview);

        authorTextView.setText(author);
        reviewTextView.setText(review);

        return reviewListView;
    }
}
