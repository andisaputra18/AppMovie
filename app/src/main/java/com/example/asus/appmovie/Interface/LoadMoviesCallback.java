package com.example.asus.appmovie.Interface;

import com.example.asus.appmovie.Model.DataMovie;

import java.util.ArrayList;

public interface LoadMoviesCallback {
    void preExecute();
    void postExecute(ArrayList<DataMovie> dataMovies);
}
