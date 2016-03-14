package com.example.batyanko.tehmovieproject;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class GridMainFragment extends Fragment {


    public GridMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.grid_main, container, false);

        Thumb[] thumbArray = {
                new Thumb(R.drawable.blacks),
                new Thumb(R.drawable.whitesnake),
                new Thumb(R.drawable.lemmy),
                new Thumb(R.drawable.saxon),
                new Thumb(R.drawable.acdc)
        };

        ArrayList<Thumb> thumbList = new ArrayList<Thumb>(Arrays.asList(thumbArray));

        ThumbAdapter thumbAdapter = new ThumbAdapter(getActivity(), thumbList);



        GridView gridView = (GridView) rootView.findViewById(R.id.movie_gridview);
        gridView.setAdapter(thumbAdapter);

        /*        Picasso.with(getActivity()).load("http://i.imgur.com/DvpvklR.png")
                .into((ImageView) rootView.findViewById(R.id.AltMainImage));*/


        return rootView;
    }

}
