package me.anky.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by anky_ on 2/10/2016.
 */

public class PopularMovie implements Parcelable {
    /**
     * Image resource ID for the poster
     */
    private String mPosterPath;

    /**
     * Construct a new {@link PopularMovie} object
     *
     * @param posterPath is the path of the movie poster
     */
    public PopularMovie(String posterPath) {
        mPosterPath = posterPath;
    }

    private PopularMovie(Parcel in){
        mPosterPath=in.readString();
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mPosterPath);
    }

    public static final Parcelable.Creator<PopularMovie> CREATOR =
            new Parcelable.Creator<PopularMovie>(){

                @Override
                public PopularMovie createFromParcel(Parcel parcel) {
                    return new PopularMovie(parcel);
                }

                @Override
                public PopularMovie[] newArray(int i) {
                    return new PopularMovie[i];
                }
            };
}
