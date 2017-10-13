package com.example.davidutterman.androidmovieapp;


import android.os.AsyncTask;
import android.view.View;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

class FetchMovieTask extends AsyncTask<URL, Void, Movies> {

    private MainActivity mainActivity;

    FetchMovieTask(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mainActivity.mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    protected Movies doInBackground(URL... params) {
        try {
            return NetworkUtils.getMovies(params[0]);
        } catch (IOException | JSONException e) {
            mainActivity.showErrorMessage();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Movies movies) {
        mainActivity.mLoadingIndicator.setVisibility(View.INVISIBLE);
        if (movies != null) {
            mainActivity.showMovieDataView();
            mainActivity.mMovieAdapter.setMovies(movies);
        } else {
            mainActivity.showErrorMessage();
        }
    }
}
