package com.example.davidutterman.androidmovieapp;

import android.content.Intent;
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

import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    static final String EXTRA_MOVIE_OBJECT = "com.example.davidutterman.androidmovieapp.model.movie";

    @BindView(R.id.pb_loading_indicator) ProgressBar mLoadingIndicator;
    @BindView(R.id.tv_error_message_display) TextView mErrorMessageDisplay;
    @BindView(R.id.rv_movies) RecyclerView mRecyclerView;

    MovieAdapter mMovieAdapter;
    MovieDbListType listType = MovieDbListType.POPULAR;

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
        new FetchMovieTask(this).execute(url);
    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(this, MovieActivity.class);
        intent.putExtra(EXTRA_MOVIE_OBJECT, movie);
        startActivity(intent);
    }


    protected void showMovieDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    protected void showErrorMessage() {
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