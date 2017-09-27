package com.example.davidutterman.androidmovieapp.model;

import android.content.Context;

import com.example.davidutterman.androidmovieapp.Config;

import java.io.Serializable;

public class Movie implements Serializable {

    private String title;
    private String thumbnail;
    private String overview;
    private String voteAverage;
    private String releaseDate;

    public String getTitle() {
        return title;
    }

    public Movie setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public Movie setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public String getOverview() {
        return overview;
    }

    public Movie setOverview(String overview) {
        this.overview = overview;
        return this;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public Movie setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
        return this;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Movie setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public String getPosterUrl(Context c) {
        Config p = new Config(c.getAssets());
        String posterUrl = p.getProperty("poster_url");
        return posterUrl + getThumbnail();
    }
}
