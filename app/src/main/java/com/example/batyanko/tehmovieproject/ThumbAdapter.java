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
public class ThumbAdapter extends ArrayAdapter<Thumb> {
    public ThumbAdapter(Activity context, List<Thumb> thumbs){
        super(context, 0, thumbs);
    };

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Thumb thumb = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.thumb_movie_list_item, parent, false);
        }

        ImageView iconView = (ImageView) convertView.findViewById(R.id.thumb_item_imageview);
        iconView.setImageResource(thumb.movieThumb);

        return convertView;
    }
}
