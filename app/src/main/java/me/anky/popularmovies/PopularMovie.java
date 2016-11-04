package me.anky.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by anky_ on 2/10/2016.
 */

public class PopularMovie implements Parcelable {
    /**
     * Movie data
     */
    private String mPosterPath;
    private String mOriginalTitle;
    private String mOverview;
    private double mVoteAverage;
    private String mRleaseDate;
    private String mMovieId;

    /**
     * Construct a new {@link PopularMovie} object
     *
     * @param movieId   is the ID of the movie
     * @param posterPath    is the path of the movie poster
     * @param originalTitle is the title
     * @param overview      is the plot
     * @param voteAverage   is the rating
     * @param releaseDate   is the release date
     */
    public PopularMovie(String movieId, String posterPath, String originalTitle, String overview,
                        double voteAverage, String releaseDate) {
        mMovieId = movieId;
        mPosterPath = posterPath;
        mOriginalTitle = originalTitle;
        mOverview = overview;
        mVoteAverage = voteAverage;
        mRleaseDate = releaseDate;
    }

    private PopularMovie(Parcel in) {
        mMovieId = in.readString();
        mPosterPath = in.readString();
        mOriginalTitle = in.readString();
        mOverview = in.readString();
        mVoteAverage = in.readDouble();
        mRleaseDate = in.readString();
    }

    public String getMovieId(){
        return mMovieId;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public String getOverview() {
        return mOverview;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }

    public String getRleaseDate() {
        return mRleaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mMovieId);
        parcel.writeString(mPosterPath);
        parcel.writeString(mOriginalTitle);
        parcel.writeString(mOverview);
        parcel.writeDouble(mVoteAverage);
        parcel.writeString(mRleaseDate);
    }

    public static final Parcelable.Creator<PopularMovie> CREATOR =
            new Parcelable.Creator<PopularMovie>() {

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
