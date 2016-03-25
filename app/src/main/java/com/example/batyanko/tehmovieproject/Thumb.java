package com.example.batyanko.tehmovieproject;

/**
 * Created by Batyanko on 13.3.2016 г..
 */
public class Thumb {
    int movieThumb;
    String id;
    String posterPath;
    String originalTitle;
    String overview;
    String voteAverage;
    String releaseDate;
    String posterAdress;

    public Thumb (int thumb) {
        this.movieThumb = thumb;
    }

    public Thumb (String posterAdress) {
        this.posterAdress = posterAdress;
    }

    public Thumb (
            String id,
            String posterPath,
            String originalTitle,
            String overview,
            String voteAverage,
            String releaseDate,
            String posterAdress
    ) {
        this.id = id;
        this.posterPath = posterPath;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
        this.posterAdress = posterAdress;
    }
}





























