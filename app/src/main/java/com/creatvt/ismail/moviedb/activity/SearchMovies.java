package com.creatvt.ismail.moviedb.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import retrofit2.Retrofit;

public class SearchMovies extends AppCompatActivity {

    private final String TAG = SearchMovies.class.getSimpleName();
    private final String API_KEY = "25611267ecba2d97519b04498964b070";
    private RecyclerView movieSearchList;
    private APIInterface apiService;
    private SearchView movieSearchView;
    ProgressBar progressBar;
    TextView noResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movies);

        Toolbar toolbar = findViewById(R.id.search_toolbar);
        setSupportActionBar(toolbar);

        progressBar = findViewById(R.id.progress_bar);
        noResult = findViewById(R.id.txt_no_result);

        movieSearchView = findViewById(R.id.movie_search_view);

        movieSearchView.setOnQueryTextListener(new SearchListener(this));

        movieSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movieSearchView.setIconified(false);
            }
        });

        apiService = APIClient.getClient().create(APIInterface.class);
    }

    public void showMovies(String name){
        Call<MoviesResponse> call = apiService.searchMovie(API_KEY,name);

        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                try{
                    List<Movie> movies = response.body().getResults();

                    if(movies.size()==0){
                        noResult.setVisibility(View.VISIBLE);
                        movieSearchList.setAdapter(null);
                        progressBar.setVisibility(View.GONE);
                    }
                    else {
                        MoviesAdapter adapter = new MoviesAdapter(movies, new OnItemClickListener() {
                            @Override
                            public void onItemClick(Movie item) {
                                showMovieDetails(item.getId());
                            }
                        });

                        movieSearchList = findViewById(R.id.search_movies_list);

                        movieSearchList.setAdapter(adapter);

                        movieSearchList.setLayoutManager(new LinearLayoutManager(SearchMovies.this));

                        movieSearchList.addItemDecoration(new DividerItemDecoration(SearchMovies.this, LinearLayout.VERTICAL));

                        progressBar.setVisibility(View.GONE);

                    }

                }catch (NullPointerException npe){
                    Log.i(TAG,"NULL POINTER EXCEPTION BY response.body().getResults() in SearchMovies.showMovies");
                }


            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {

            }
        });
    }

    public void showMovieDetails(int id){

        Call<Movie> call = apiService.getMovieDetails(id,API_KEY);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {

                if(response.body() != null) {
                    Intent intent = new Intent(SearchMovies.this, MovieDetailsActivity.class);

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
}
