package com.creatvt.ismail.moviedb.activity;

import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.WindowManager;

public class SearchListener implements SearchView.OnQueryTextListener {

    private SearchMovies context;

    public SearchListener(SearchMovies context){
        this.context = context;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        context.progressBar.setVisibility(View.VISIBLE);
        context.noResult.setVisibility(View.GONE);
        context.showMovies(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
