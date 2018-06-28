package com.creatvt.ismail.moviedb.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.creatvt.ismail.moviedb.R;
import com.creatvt.ismail.moviedb.adapter.MoviesAdapter;
import com.creatvt.ismail.moviedb.model.Movie;
import com.creatvt.ismail.moviedb.model.MoviesResponse;
import com.creatvt.ismail.moviedb.rest.APIClient;
import com.creatvt.ismail.moviedb.rest.APIInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopRatedMovies extends AppCompatActivity {

    private final String TAG = TopRatedMovies.class.getSimpleName();
    final String API_KEY = "25611267ecba2d97519b04498964b070";
    private APIInterface apiService;
    private RecyclerView movieList;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_rated_movies);

        /*
        if(API_KEY.isEmpty()){
            Toast.makeText(this,"Obtain Your API key First!",Toast.LENGTH_LONG).show();
            return;
        }*/

        progressBar = findViewById(R.id.progress_bar);

        progressBar.setVisibility(View.VISIBLE);

        apiService= APIClient.getClient().create(APIInterface.class);

        Call<MoviesResponse> call = apiService.getTopRatedMovies(API_KEY);

        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    MoviesResponse mresponse = response.body();

                    if(mresponse!=null) {

                        List<Movie> movies = mresponse.getResults();

                        showRecyclerView(movies);
                    }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                Log.i(TAG,t.toString());
            }
        });


    }

    public void showMovieDetails(int id){

        Call<Movie> call = apiService.getMovieDetails(id,API_KEY);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {

                if(response.body() != null) {
                    Intent intent = new Intent(TopRatedMovies.this, MovieDetailsActivity.class);

                    Movie movie = response.body();

                    intent.putExtra("id", movie.getId());
                    intent.putExtra("movie_title", movie.getTitle());
                    intent.putExtra("release_date", movie.getReleaseDate());
                    intent.putExtra("vote_average", movie.getVoteAverage()/2.0);
                    intent.putExtra("adult", movie.getAdult().toString());
                    intent.putExtra("poster_path", movie.getPosterPath());
                    intent.putExtra("original_language", movie.getOriginalLanguage());
                    intent.putExtra("overview", movie.getOverview());

                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"OOps! Something Went Wrong...",Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void showRecyclerView(List<Movie> movies){
        if(movies!=null) {
            movieList = findViewById(R.id.movie_list);
            movieList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            movieList.addItemDecoration(new DividerItemDecoration(movieList.getContext(), LinearLayout.VERTICAL));
            movieList.setHasFixedSize(true);
            RecyclerView.Adapter adapter = new MoviesAdapter(movies, new OnItemClickListener() {
                @Override
                public void onItemClick(Movie item) {
                    showMovieDetails(item.getId());
                }
            });
            movieList.setAdapter(adapter);

            progressBar.setVisibility(View.GONE);

        }

    }
}
