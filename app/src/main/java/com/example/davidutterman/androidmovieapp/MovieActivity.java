package com.example.davidutterman.androidmovieapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.davidutterman.androidmovieapp.model.Movie;
import com.squareup.picasso.Picasso;

public class MovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        Intent intent = getIntent();
        Movie movie = (Movie) intent.getSerializableExtra(Intent.EXTRA_TEXT);

        TextView mTitle = (TextView) findViewById(R.id.tv_title);
        mTitle.setText(movie.getTitle());

        ImageView imageView = (ImageView) findViewById(R.id.iv_poster);
        Context context = getApplicationContext();
        Picasso.with(context)
                .load(movie.getPosterUrl(context))
                .into(imageView);

        TextView mOverview = (TextView) findViewById(R.id.tv_overview);
        mOverview.setText(movie.getOverview());

        TextView mRating = (TextView) findViewById(R.id.tv_rating);
        mRating.setText(movie.getVoteAverage());

        TextView mReleaseDate = (TextView) findViewById(R.id.tv_release_date);
        mReleaseDate.setText(movie.getReleaseDate());
    }
}
