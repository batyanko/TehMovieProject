package com.example.batyanko.tehmovieproject;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A placeholder fragment containing a simple view.
 */
public class ListMainFragment extends Fragment {

    private MovieAdapter movieAdapter;

    public ListMainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_main, container, false);

        Movie[] movieArray = {
                new Movie("AC/DC", "bla bla", "--rating--", R.drawable.acdc),
                new Movie("Black Sabbath", "bla bla", "--rating--", R.drawable.blacks),
                new Movie("Kiss", "bla bla", "--rating--", R.drawable.gene_simmons),
                new Movie("Motorhead", "bla bla", "--rating--", R.drawable.lemmy),
                new Movie("Saxon", "bla bla", "--rating--", R.drawable.saxon),
                new Movie("Michael Schenker's Group", "bla bla", "--rating--", R.drawable.schenker),
                new Movie("Whitesnake", "bla bla", "--rating--", R.drawable.whitesnake)
        };

        ArrayList<Movie> movieList = new ArrayList<Movie>(Arrays.asList(movieArray));

        movieAdapter = new MovieAdapter(getActivity(), movieList);

        ListView listView = (ListView) rootView.findViewById(R.id.movie_listview);
        listView.setAdapter(movieAdapter);


       /* Picasso.with(getActivity()).load("http://i.imgur.com/DvpvklR.png")
                .into((ImageView) rootView.findViewById(R.id.gene_simmons));
        */
        String s;
        
        return rootView;
        
    }
}
