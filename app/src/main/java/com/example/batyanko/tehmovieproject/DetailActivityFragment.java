package com.example.batyanko.tehmovieproject;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Intent intent = getActivity().getIntent();
        String movieData[] = intent.getStringArrayExtra(Intent.EXTRA_TEXT);
        Log.v("IntentInFragment", movieData[2]);

        TextView titleText = (TextView) rootView.findViewById(R.id.title_textView);
        titleText.setText(movieData[2]);
        TextView ratingText = (TextView) rootView.findViewById(R.id.rating_textView);
        ratingText.setText(movieData[4]);
        TextView yearText = (TextView) rootView.findViewById(R.id.year_textView);
        yearText.setText(movieData[5]);
        TextView overviewText = (TextView) rootView.findViewById(R.id.overview_textView);
        overviewText.setText(movieData[3]);

        if (movieData[6] != null) {

            Uri.Builder imageUrlBuilder = new Uri.Builder();

            imageUrlBuilder.scheme("http")
                    .authority("image.tmdb.org")
                    .appendPath("t")
                    .appendPath("p")
                    .appendPath("w185")
                    .appendEncodedPath(movieData[1])
                    .build();

            String posterURLString = imageUrlBuilder.build().toString();

            Picasso.with(getContext()).load(posterURLString)
                    .into((ImageView) rootView.findViewById(R.id.poster_imageView));
        }

        return rootView;
    }
}





















