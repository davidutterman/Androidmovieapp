package com.example.davidutterman.androidmovieapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.davidutterman.androidmovieapp.model.Movie;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private ProgressBar mLoadingIndicator;
    private TextView mErrorMessageDisplay;
    private MovieAdapter mMovieAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        mMovieAdapter = new MovieAdapter(this, this);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movies);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mMovieAdapter);

        makeMovieDbQuery(MovieDbListType.POPULAR);
    }

    private void makeMovieDbQuery(MovieDbListType type) {
        showMovieDataView();
        Props props = new Props(getAssets());
        URL url = NetworkUtils.buildUrl(
                props.getProperty("movie_db_url"),
                props.getProperty("api_key"), type);
        new FetchMovieTask().execute(url);
    }

    @Override
    public void onClick(Movie movie) {
        Log.d("click", movie.getTitle());
        Toast.makeText(getBaseContext(), "You clicked on: " + movie.getTitle(), Toast.LENGTH_LONG);
    }

    class FetchMovieTask extends AsyncTask<URL, Void, Movies> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected Movies doInBackground(URL... params) {
            try {
                return NetworkUtils.getMovies(params[0]);
            } catch (IOException | JSONException e) {
                showErrorMessage();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Movies movies) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movies != null) {
                showMovieDataView();
                mMovieAdapter.setMovies(movies);
            } else {
                showErrorMessage();
            }
        }
    }

    private void showMovieDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.refresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            makeMovieDbQuery(MovieDbListType.POPULAR);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}