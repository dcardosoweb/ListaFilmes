package com.example.danielgoncalves.listafilmes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by daniel.goncalves on 12/01/2017.
 */

public class MovieAdapter extends ArrayAdapter<MovieEntity> {

    public MovieAdapter(Context context, ArrayList<MovieEntity> movies) {
        super(context, 0, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MovieEntity movie = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_movie, parent, false);
        }

        ImageView imvCover = (ImageView) convertView.findViewById(R.id.list_item_movie_cover);
        TextView txvDetail = (TextView) convertView.findViewById(R.id.list_item_movie_detail_path);
        TextView txvTitle = (TextView) convertView.findViewById(R.id.list_item_movie_title);

        Picasso.with(getContext()).load(movie.getCoverPath()).into(imvCover);
        txvDetail.setText(movie.getDetailURL());
        txvTitle.setText(movie.getTitle());
        return convertView;
    }
}
