package com.example.asus.appmovie.Activity;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.asus.appmovie.Adapter.FavoriteAdapter;
import com.example.asus.appmovie.Adapter.MovieAdapter;
import com.example.asus.appmovie.Database.MovieHelper;
import com.example.asus.appmovie.Interface.LoadMoviesCallback;
import com.example.asus.appmovie.Model.DataMovie;
import com.example.asus.appmovie.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity implements LoadMoviesCallback {
    MovieAdapter movieAdapter;
    public static final String EXTRA_STATE = "extra_state";
    private MovieHelper movieHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        RecyclerView rvFavorite = findViewById(R.id.rv_favorite);
        rvFavorite.setHasFixedSize(true);

        rvFavorite.setLayoutManager(new GridLayoutManager(this, 2));
        movieAdapter = new MovieAdapter(this, "movie");
        movieAdapter.notifyDataSetChanged();
        rvFavorite.setAdapter(movieAdapter);

        movieHelper = MovieHelper.getInstance(this);
        movieHelper.open();

        if (savedInstanceState == null){
            new LoadMoviesAsync(movieHelper, this).execute();
        } else {
            ArrayList<DataMovie> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null){
                movieAdapter.setDataMovies(list);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, movieAdapter.getDataMovies());
    }

    @Override
    public void preExecute() {

    }

    @Override
    public void postExecute(ArrayList<DataMovie> dataMovies) {
        movieAdapter.setDataMovies(dataMovies);
    }

    public static class LoadMoviesAsync extends AsyncTask<Void, Void, ArrayList<DataMovie>> {
        private final WeakReference<MovieHelper> weakMovieHelper;
        private final WeakReference<LoadMoviesCallback> weakCallback;

        LoadMoviesAsync(MovieHelper movieHelper, LoadMoviesCallback callback) {
            weakMovieHelper = new WeakReference<>(movieHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<DataMovie> doInBackground(Void... voids) {
            return weakMovieHelper.get().getAllMovie();
        }

        @Override
        protected void onPostExecute(ArrayList<DataMovie> movies){
            super.onPostExecute(movies);
            weakCallback.get().postExecute(movies);
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        movieHelper.close();
    }
}
