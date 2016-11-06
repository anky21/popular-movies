package me.anky.popularmovies.Trailer;

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
 * Created by anky_ on 4/11/2016.
 */

public final class TrailerQueryUtils {
    public static final String LOG_TAG = TrailerQueryUtils.class.getSimpleName();

    private TrailerQueryUtils() {
    }

    public static List<MovieTrailer> fetchTrailerData(String requestUrl) {
        URL url = QueryUtils.createUrl(requestUrl);

        String jsonResponse = null;

        try {
            jsonResponse = QueryUtils.makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        List<MovieTrailer> movieTrailers = extractFeatureFromJson(jsonResponse);
        return movieTrailers;
    }

    public static List<MovieTrailer> extractFeatureFromJson(String trailerJson) {

        List<MovieTrailer> movieTrailers = new ArrayList<>();

        if (TextUtils.isEmpty(trailerJson)) {
            Log.v(LOG_TAG, "empty Json");
            return null;
        }

        try {
            JSONObject baseJsonResponse = new JSONObject(trailerJson);
            JSONArray resultArray = baseJsonResponse.optJSONArray("results");

            int trailerNumber = 0;
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject trailerData = resultArray.getJSONObject(i);
                String type = trailerData.getString("type");
                if (type.contains("Trailer")) {
                    String key = trailerData.getString("key");
                    trailerNumber += 1;
                    MovieTrailer movieTrailer = new MovieTrailer(key, "Trailer " + trailerNumber);
                    movieTrailers.add(movieTrailer);
                }
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Testing: Problem parsing the Json response", e);
        }
        return movieTrailers;
    }
}
