package com.example.danielgoncalves.listafilmes.ui.detail;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.danielgoncalves.listafilmes.R;
import com.example.danielgoncalves.listafilmes.data.MovieReview;
import com.example.danielgoncalves.listafilmes.util.OnItemClickListener;

import java.util.ArrayList;


public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewViewHolder> {

    @Nullable
    private ArrayList<MovieReview> movieReviews;
    @Nullable
    private OnItemClickListener onItemClickListener;

    public MovieReviewsAdapter() {
        movieReviews = new ArrayList<>();
    }

    public void setMovieReviews(@Nullable ArrayList<MovieReview> movieReviews) {
        this.movieReviews = movieReviews;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Nullable
    public ArrayList<MovieReview> getMovieReviews() {
        return movieReviews;
    }

    @Override
    public MovieReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_movie_review, parent, false);
        return new MovieReviewViewHolder(itemView, onItemClickListener);
    }

    @Override
    @SuppressLint("PrivateResource")
    public void onBindViewHolder(MovieReviewViewHolder holder, int position) {
        if (movieReviews == null) {
            return;
        }
        MovieReview review = movieReviews.get(position);
        holder.content.setText(review.getContent());
        holder.author.setText(review.getAuthor());
    }

    @Override
    public int getItemCount() {
        if (movieReviews == null) {
            return 0;
        }
        return movieReviews.size();
    }

    public MovieReview getItem(int position) {
        if (movieReviews == null || position < 0 || position > movieReviews.size()) {
            return null;
        }
        return movieReviews.get(position);
    }
}
