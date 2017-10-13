package com.example.davidutterman.androidmovieapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.davidutterman.androidmovieapp.model.Movie;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    @BindView(R.id.pb_loading_indicator) ProgressBar mLoadingIndicator;
    @BindView(R.id.tv_error_message_display) TextView mErrorMessageDisplay;
    @BindView(R.id.rv_movies) RecyclerView mRecyclerView;

    private MovieAdapter mMovieAdapter;
    private MovieDbListType listType = MovieDbListType.POPULAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mMovieAdapter = new MovieAdapter(this, this);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mMovieAdapter);

        makeMovieDbQuery(listType);
    }

    private void makeMovieDbQuery(MovieDbListType type) {
        showMovieDataView();
        Config config = new Config(getBaseContext());
        URL url = NetworkUtils.buildUrl(
                config.getProperty("movie_db_url"),
                config.getProperty("api_key"), type);
        new FetchMovieTask().execute(url);
    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(this, MovieActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, movie);
        startActivity(intent);
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
        inflater.inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_popular:
                listType = MovieDbListType.POPULAR;
                break;
            case R.id.action_top_rated:
                listType = MovieDbListType.TOP_RATED;
                break;
            default:
            case R.id.action_refresh:
                break;
        }
        makeMovieDbQuery(listType);
        return true;
    }
}