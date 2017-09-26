package com.example.davidutterman.androidmovieapp.model;

public class Movie {

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
}
