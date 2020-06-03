package com.example.asus.appmovie.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.asus.appmovie.Activity.DetailActivity;
import com.example.asus.appmovie.Model.DataMovie;
import com.example.asus.appmovie.R;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private ArrayList<DataMovie> dataMovies = new ArrayList<>();

    private Context mContext;
    public MovieAdapter(Context mContext, String movie) {
        this.mContext = mContext;
    }

    public void setDataMovies(ArrayList<DataMovie> items) {
        dataMovies.clear();
        dataMovies.addAll(items);
        notifyDataSetChanged();
    }

    public ArrayList<DataMovie> getDataMovies() {
        return dataMovies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_grid_movies, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        DataMovie modelMovie = dataMovies.get(position);

        Glide.with(holder.itemView.getContext())
                .load(modelMovie.getMvPoster())
                .apply(new RequestOptions().override(170,210))
                .into(holder.imgPoster);
//        holder.textTitle.setText(modelMovie.getMvTitle());
        holder.textScore.setText(modelMovie.getMvScore());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext.getApplicationContext(), DetailActivity.class).putExtra("detail", dataMovies.get(position)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataMovies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView textScore;
        ImageView imgPoster;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPoster = itemView.findViewById(R.id.img_item_photo);
//            textTitle = itemView.findViewById(R.id.tv_item_name);
            textScore = itemView.findViewById(R.id.tv_item_score);
        }
    }
}
