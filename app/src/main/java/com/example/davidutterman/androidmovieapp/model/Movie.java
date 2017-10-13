package com.example.davidutterman.androidmovieapp.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.davidutterman.androidmovieapp.Config;

public class Movie implements Parcelable {

    private String title;
    private String thumbnail;
    private String overview;
    private String voteAverage;
    private String releaseDate;

    private Movie(Parcel in) {
        title = in.readString();
        thumbnail = in.readString();
        overview = in.readString();
        voteAverage = in.readString();
        releaseDate = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public Movie() {
    }

    public String getTitle() {
        return title;
    }

    public Movie setTitle(String title) {
        this.title = title;
        return this;
    }

    private String getThumbnail() {
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

    public String getPosterUrl(Context context) {
        Config p = new Config(context);
        return p.getProperty("poster_url") + getThumbnail();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(thumbnail);
        dest.writeString(overview);
        dest.writeString(voteAverage);
        dest.writeString(releaseDate);
    }
}
