package com.example.api.data.source.local.favorite;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class FavoriteModel implements Serializable {
    @PrimaryKey
    private int movieId;
    @ColumnInfo(defaultValue = "0")
    private double movieRate ;
    private String movieName;
    private String posterPath;

    public FavoriteModel(int movieId, double movieRate, String movieName , String posterPath) {
        this.movieId = movieId;
        this.movieRate = movieRate;
        this.movieName = movieName;
        this.posterPath = posterPath;
    }

    public FavoriteModel() {

    }

    public FavoriteModel(int movieId) {
        this.movieId = movieId;

    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public double getMovieRate() {
        return movieRate;
    }

    public void setMovieRate(double movieRate) {
        this.movieRate = movieRate;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
}
