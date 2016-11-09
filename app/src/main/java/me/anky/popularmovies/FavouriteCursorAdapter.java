package me.anky.popularmovies;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;

import static me.anky.popularmovies.data.MovieContract.MovieEntry;

/**
 * Created by anky_ on 9/11/2016.
 */

public class FavouriteCursorAdapter extends CursorAdapter {

    public FavouriteCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }



    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView posterImage = (ImageView)view.findViewById(R.id.poster_of_the_movie);

        int movieIdColumnIndex = cursor.getColumnIndex(MovieEntry.COLUMN_MOVIE_ID);
        int posterColumnIndex = cursor.getColumnIndex(MovieEntry.COLUMN_MOVIE_PATH);

        String movieId = cursor.getString(movieIdColumnIndex);
        String posterPath = cursor.getString(posterColumnIndex);

        String myUrl = null;
        try {
            myUrl = QueryUtils.getImageUrl(posterPath);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Picasso.with(context).load(myUrl).into(posterImage);



    }


}
