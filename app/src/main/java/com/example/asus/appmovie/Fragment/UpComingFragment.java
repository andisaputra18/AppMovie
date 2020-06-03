package com.example.asus.appmovie.Fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.asus.appmovie.Adapter.MovieAdapter;
import com.example.asus.appmovie.Architecture.MainViewModel;
import com.example.asus.appmovie.Model.DataMovie;
import com.example.asus.appmovie.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpComingFragment extends Fragment {
    private ProgressBar progressBar;
    MovieAdapter movieAdapter;
//    public MainViewModel mainViewModel;

    public UpComingFragment() {
        // Required empty public constructor
    }

//    @Override
//    public void setArguments(@Nullable Bundle args) {
//        super.setArguments(args);
//        if(args.getString("query").equals("")){
//            mainViewModel.setListUpComing("movie");
//        } else {
//            mainViewModel.setSearchMovie("movie", args.getString("query"));
//            Log.d("Query", args.getString("query"));
//        }
//    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_up_coming, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.progressBar);

        RecyclerView rvMovie = view.findViewById(R.id.rv_upcoming);
        rvMovie.setHasFixedSize(true);

        rvMovie.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        movieAdapter = new MovieAdapter(getContext(), "movie");
        movieAdapter.notifyDataSetChanged();
        rvMovie.setAdapter(movieAdapter);

        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getListMovie().observe(this, getMovie);
        mainViewModel.setListUpComing("movie");
        showLoading(true);
    }

    private Observer<ArrayList<DataMovie>> getMovie = new Observer<ArrayList<DataMovie>>() {
        @Override
        public void onChanged(@Nullable ArrayList<DataMovie> movieItems) {
            if (movieItems != null){
                movieAdapter.setDataMovies(movieItems);
                showLoading(false);
            }
        }
    };

    private void showLoading(boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
