package me.anky.popularmovies.Review;

/**
 * Created by anky_ on 5/11/2016.
 */

// Create an object for Movie Review
public class MovieReview {
    private String mAuthor;
    private String mReview;

    public MovieReview(String author, String review){
        mAuthor = author;
        mReview = review;
    }

    public String getAuthor(){
        return mAuthor;
    }

    public String getReview(){
        return mReview;
    }
}
