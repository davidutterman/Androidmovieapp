package com.example.davidutterman.androidmovieapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.davidutterman.androidmovieapp.model.Movie;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieActivity extends AppCompatActivity {

    @BindView(R.id.tv_title) TextView mTitle;
    @BindView(R.id.iv_poster) ImageView imageView;
    @BindView(R.id.tv_overview) TextView mOverview;
    @BindView(R.id.tv_rating) TextView mRating;
    @BindView(R.id.tv_release_date) TextView mReleaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);

        Movie movie = getIntent().getParcelableExtra(MainActivity.EXTRA_MOVIE_OBJECT);

        mTitle.setText(movie.getTitle());

        Context context = getApplicationContext();
        Picasso.with(context)
                .load(movie.getPosterUrl(context))
                .into(imageView);

        mOverview.setText(movie.getOverview());
        mRating.setText(movie.getVoteAverage());
        mReleaseDate.setText(movie.getReleaseDate());
    }
}
