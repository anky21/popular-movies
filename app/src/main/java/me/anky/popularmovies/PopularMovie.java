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
    private int mPosterResourceId;

    /**
     * Construct a new {@link PopularMovie} object
     *
     * @param posterResourceId is the resource ID of the movie poster
     */
    public PopularMovie(int posterResourceId) {
        mPosterResourceId = posterResourceId;
    }

    private PopularMovie(Parcel in){
        mPosterResourceId=in.readInt();
    }

    public int getPosterResourceId() {
        return mPosterResourceId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mPosterResourceId);
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
