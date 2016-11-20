package me.anky.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.util.List;

/**
 * Created by anky_ on 2/10/2016.
 */

/**
 * {@link PopularMovieAdapter} is an {@link PopularMovie} that provides the layout for each list
 * item on a data source, which is a list of {@link PopularMovie} objects
 */
public class PopularMovieAdapter extends ArrayAdapter<PopularMovie> {
    private static final String LOG_TAG = PopularMovieAdapter.class.getSimpleName();

    /**
     * Create a new {@link PopularMovieAdapter} object
     *
     * @param context       is the current context that the adapter is being created in
     * @param popularMovies is the list of {@link PopularMovie}s to be displayed
     */

    public PopularMovieAdapter(Context context, List<PopularMovie> popularMovies) {
        super(context, 0, popularMovies);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        // Get the {@link PopularMovie} object located at this position in the list
        PopularMovie currentMovie = getItem(position);

        String posterPath = currentMovie.getPosterPath();
        String myUrl = null;
        try {
            myUrl = QueryUtils.getImageUrl(posterPath);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // Find the ImageView in the list_item.xml layout with the ID image
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.poster_of_the_movie);

        Picasso.with(getContext())
                .load(myUrl)
                .placeholder(R.drawable.loading_icon) // Displays this image while loading
                .error(R.drawable.errorstop)    // // Displays this image when there is an error
                .into(imageView);

        // Return the whole list item layout so that it can be shown in the GridView
        return listItemView;
    }
}
