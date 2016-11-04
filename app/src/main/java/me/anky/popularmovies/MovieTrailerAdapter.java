package me.anky.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by anky_ on 4/11/2016.
 */

public class MovieTrailerAdapter extends ArrayAdapter<MovieTrailer> {
    public MovieTrailerAdapter(Context context, List<MovieTrailer> movieTrailers) {
        super(context, 0, movieTrailers);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View trailerListView = convertView;
        if (trailerListView == null){
            trailerListView = LayoutInflater.from(getContext()).inflate(
                    R.layout.trailer_list, parent, false);
        }

        MovieTrailer currentTrailer = getItem(position);

        String trailerNumber = currentTrailer.getTrailerNumber();

        TextView trailerTextView = (TextView)trailerListView.findViewById(R.id.trailer_number_view);
        trailerTextView.setText(trailerNumber);

        return trailerListView;
    }
}
