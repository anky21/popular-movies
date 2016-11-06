package me.anky.popularmovies.Review;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import me.anky.popularmovies.QueryUtils;

/**
 * Created by anky_ on 5/11/2016.
 */

public final class ReviewQueryUtils {
    public static final String LOG_TAG = ReviewQueryUtils.class.getSimpleName();

    private ReviewQueryUtils() {
    }

    public static List<MovieReview> fetchReviewData(String requestUrl) {
        URL url = QueryUtils.createUrl(requestUrl);

        String jsonResponse = null;

        try {
            jsonResponse = QueryUtils.makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        List<MovieReview> movieReviews = extractFeatureFromJson(jsonResponse);
        return movieReviews;
    }

    public static List<MovieReview> extractFeatureFromJson(String reviewJson) {
        List<MovieReview> movieReviews = new ArrayList<>();

        if (TextUtils.isEmpty(reviewJson)) {
            Log.v(LOG_TAG, "empty Json");
            return null;
        }

        try {
            JSONObject baseJsonResponse = new JSONObject(reviewJson);
            JSONArray resultArray = baseJsonResponse.optJSONArray("results");

            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject reviewData = resultArray.getJSONObject(i);
                String author = reviewData.getString("author");
                String content = reviewData.getString("content");

                MovieReview movieReview = new MovieReview(author, content);
                movieReviews.add(movieReview);
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Testing: Problem parsing the Json response", e);
        }

        return movieReviews;
    }
}
