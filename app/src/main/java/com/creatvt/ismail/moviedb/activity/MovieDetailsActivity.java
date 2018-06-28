package com.creatvt.ismail.moviedb.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.creatvt.ismail.moviedb.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MovieDetailsActivity extends AppCompatActivity {

    TextView txtMovieTitle,txtReleaseDate,txtAdult,txtLanguage,txtOverview,id;
    RatingBar rating;
    ImageView poster;
    ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        txtMovieTitle = findViewById(R.id.txt_movie_title);
        txtReleaseDate = findViewById(R.id.txt_release_date);
        txtAdult = findViewById(R.id.txt_adult);
        txtLanguage = findViewById(R.id.txt_language);
        txtOverview = findViewById(R.id.overview);

        rating = findViewById(R.id.rating);
        poster = findViewById(R.id.movie_poster);
        backBtn = findViewById(R.id.back_btn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent data = getIntent();

        String overview = data.getStringExtra("overview");
        String movieTitle = data.getStringExtra("movie_title");
        String language = data.getStringExtra("original_language");
        String adult = data.getStringExtra("adult");
        String releaseDate = data.getStringExtra("release_date");
        double ratingValue = data.getDoubleExtra("vote_average",0.0);
        String posterPath = data.getStringExtra("poster_path");

        txtOverview.setText(overview);
        txtMovieTitle.setText(movieTitle);
        txtLanguage.setText(language);
        txtAdult.setText(adult);
        txtReleaseDate.setText(releaseDate);
        rating.setRating((float) ratingValue);
        rating.setIsIndicator(true);
        rating.setClickable(false);

        Glide.with(this)
                .load ("http://image.tmdb.org/t/p/w500/" + posterPath)
                .thumbnail(0.5f)
                .into(poster);

    }

}
