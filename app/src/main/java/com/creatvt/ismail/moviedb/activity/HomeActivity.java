package com.creatvt.ismail.moviedb.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.creatvt.ismail.moviedb.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        CardView topRatedMovies = findViewById(R.id.top_rated_movies);
        CardView searchMovies = findViewById(R.id.search_movie);

        topRatedMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,TopRatedMovies.class);

                startActivity(intent);
            }
        });

        searchMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,SearchMovies.class);

                startActivity(intent);
            }
        });


    }
}
