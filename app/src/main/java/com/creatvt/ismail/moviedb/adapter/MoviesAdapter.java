package com.creatvt.ismail.moviedb.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.creatvt.ismail.moviedb.R;
import com.creatvt.ismail.moviedb.activity.OnItemClickListener;
import com.creatvt.ismail.moviedb.model.Movie;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<Movie> movies;
    private OnItemClickListener listener;

    public MoviesAdapter(List<Movie> movies, OnItemClickListener listener) {
        this.movies = movies;
        this.listener = listener;
    }

    @Override
    public MoviesAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_movie_item, parent, false);

        return new MoviesAdapter.MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesAdapter.MovieViewHolder holder, int position) {

        Movie movie = movies.get(position);
        holder.bind(movie, listener);


    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        LinearLayout movieLayout;
        TextView movieTitle, description, releaseDate, rating;
        View view;

        public MovieViewHolder(View view) {
            super(view);
            this.view = view;

            this.movieTitle = view.findViewById(R.id.movie_title);
            this.description = view.findViewById(R.id.description);
            this.releaseDate = view.findViewById(R.id.release_date);
            this.rating = view.findViewById(R.id.rating);

            movieLayout = view.findViewById(R.id.movie_layout);

        }

        public void bind(final Movie movie, final OnItemClickListener itemClickListener) {
            movieTitle.setText(movie.getTitle());
            description.setText(movie.getOverview());
            rating.setText(String.valueOf(movie.getVoteAverage()));
            releaseDate.setText(movie.getReleaseDate());
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClick(movie);
                }
            });
        }
    }


}
