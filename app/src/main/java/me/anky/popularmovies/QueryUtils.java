package me.anky.popularmovies;

/**
 * Created by anky_ on 3/10/2016.
 */

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving movie data from the Movie DB
 */
public final class QueryUtils {
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185/";

    private QueryUtils() {
        // An empty Query Utils constructure
    }

    /**
     * Query the Movie DB and return an {@link List} object to represent multiple movies
     *
     * @param requestUrl is a URL used to make an HTTP request
     * @return returns a list of movie data
     */
    public static List<PopularMovie> fetchMovieData(String requestUrl) {
        URL url = createUrl(requestUrl);

        // Perform HTTP request and receive a Json response
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Testing: error closing input stream", e);
        }

        // Extract relevant fields from the Json response and create an {@link PopularMovie} object
        List<PopularMovie> popularMovies = extractFeatureFromJson(jsonResponse);

        return popularMovies;
    }

    // Return new URL object from the given URL string
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Testing: Error when creating URL", e);
        }
        return url;
    }

    // Make an HTTP request and return a String as the response
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(8000); //in milliseconds
            urlConnection.setConnectTimeout(12000); // in milliseconds
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Testing: URL connection issue" + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Testing: Problem retrieving the JSON results", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String containing the whole Json response
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader =
                    new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link PopularMovie} objects built up from parsing a Json response
     */

    public static List<PopularMovie> extractFeatureFromJson(String movieJson) {
        // Create an empty ArrayList to add movie data into
        List<PopularMovie> popularMovies = new ArrayList<>();

        // If the Json string is empty or null, return early
        if (TextUtils.isEmpty(movieJson)) {
            return null;
        }

        // Try to parse the Json response.
        // Catch an exception so that app doesn't crash and print the error message
        try {
            JSONObject baseJsonResponse = new JSONObject(movieJson);
            JSONArray resultsArray = baseJsonResponse.optJSONArray("results");

            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject movieData = resultsArray.getJSONObject(i);

                // Extract parameter values
                String posterPath = movieData.getString("poster_path");
                String title = movieData.getString("original_title");
                String overview = movieData.getString("overview");
                double voteAverage = movieData.getDouble("vote_average");
                String releaseDate = movieData.getString("release_date");

                // popularmovies.add(new PopularMovie(posterPath, title, overview, voteAverage,
                // releaseDate)
                PopularMovie popularMovie = new PopularMovie(posterPath, title, overview,
                        voteAverage, releaseDate);
                /**
                 * Add the new {@link PopularMovie} object to the list of popularMovies
                 */
                popularMovies.add(popularMovie);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Testing: problem parsing the Json response", e);
        }
        return popularMovies;
    }

    /**
     * Create the URL string for images (posters, thumbnails)
     */
    public static String getImageUrl(String string) throws MalformedURLException {
        Uri builtUri = Uri.parse(IMAGE_BASE_URL).buildUpon().appendEncodedPath(string).build();
        String myUrl = builtUri.toString();
        return myUrl;
    }

}
