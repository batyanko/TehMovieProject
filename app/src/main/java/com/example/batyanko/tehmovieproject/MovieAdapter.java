package com.example.batyanko.tehmovieproject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Batyanko on 1.3.2016 г..
 */
public class MovieAdapter extends ArrayAdapter<Movie> {
    public MovieAdapter(Activity context, List<Movie> movies){
        super(context, 0, movies);
    };

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Movie movie = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.combined_movie_list_item, parent, false);
        }

        ImageView iconView = (ImageView) convertView.findViewById(R.id.movie_icon);
        iconView.setImageResource(movie.moviePic);

        TextView titleView = (TextView) convertView.findViewById(R.id.movie_title);
        titleView.setText(movie.movieName);

        TextView descView = (TextView) convertView.findViewById(R.id.movie_short_desc);
        descView.setText(movie.movieDesc);

        TextView ratingView = (TextView) convertView.findViewById(R.id.movie_rating);
        ratingView.setText(movie.movieRating);

        return convertView;
    }
}
