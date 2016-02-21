package com.example.batyanko.tehmovieproject;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        Picasso.with(getActivity()).load("http://i.imgur.com/DvpvklR.png")
                .into((ImageView) rootView.findViewById(R.id.teh_image));
        
        String s;
        
        return rootView;
        
    }
}
