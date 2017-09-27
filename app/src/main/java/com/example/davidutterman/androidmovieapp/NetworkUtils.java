package com.example.davidutterman.androidmovieapp;

import android.net.Uri;

import com.example.davidutterman.androidmovieapp.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

class NetworkUtils {

    private final static String API_KEY_PARAM = "api_key";
    private final static String JSON_RESULT_KEY = "results";

    static URL buildUrl(String url, String apiKey, MovieDbListType movieDbListType) {

        Uri builtUri = Uri.parse(url).buildUpon()
                .appendPath(movieDbListType.toString().toLowerCase(Locale.getDefault()))
                .appendQueryParameter(API_KEY_PARAM, apiKey)
                .build();

        URL finalUrl = null;
        try {
            finalUrl = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return finalUrl;
    }

    static Movies getMovies(URL url) throws IOException, JSONException {
        String responseFromHttpUrl = getResponseFromHttpUrl(url);
        JSONArray list = (JSONArray) new JSONObject(responseFromHttpUrl).get(JSON_RESULT_KEY);

        if (list == null || list.length() < 1) {
            return null;
        }

        Movies movies = new Movies();
        movies.list = new ArrayList<>();

        for (int i = 0; i < list.length(); i++) {
            JSONObject m = list.getJSONObject(i);
            Movie movie = new Movie()
                    .setTitle(m.getString("title"))
                    .setThumbnail(m.getString("poster_path"))
                    .setOverview(m.getString("overview"))
                    .setVoteAverage(m.getString("vote_average"))
                    .setReleaseDate(m.getString("release_date"));
            movies.list.add(movie);
        }
        return movies;
    }

    private static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}