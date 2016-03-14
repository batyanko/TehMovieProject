package com.example.batyanko.tehmovieproject;

/**
 * Created by Batyanko on 1.3.2016 г..
 */
public class Movie {
    String movieName;
    String movieDesc;
    String movieRating;
    int moviePic;

    public Movie(String name, String desc, String movieRating, int pic) {
        this.movieName = name;
        this.movieDesc = desc;
        this.moviePic = pic;
        this.movieRating = movieRating;
    }
}
