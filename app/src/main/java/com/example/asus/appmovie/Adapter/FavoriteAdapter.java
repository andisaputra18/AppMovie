package com.example.asus.appmovie.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
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

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    private ArrayList<DataMovie> dataMovies = new ArrayList<>();
    private Context mContext;

    public FavoriteAdapter(Context mContext) {
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

    public void addItem(DataMovie dataMovie){
        this.dataMovies.add(dataMovie);
        notifyItemInserted(dataMovies.size() - 1);
    }

    public void updateItem(int position, DataMovie dataMovie){
        this.dataMovies.set(position, dataMovie);
        notifyItemChanged(position, dataMovie);
    }

    public void removeItem(int position){
        this.dataMovies.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, dataMovies.size());
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_grid_movies, viewGroup, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        DataMovie modelMovie = dataMovies.get(position);

        Glide.with(holder.itemView.getContext())
                .load(modelMovie.getMvPoster())
                .apply(new RequestOptions().override(170,210))
                .into(holder.imgPoster);
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

    class FavoriteViewHolder extends RecyclerView.ViewHolder {
        TextView textScore;
        ImageView imgPoster;

        FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_item_photo);
            textScore = itemView.findViewById(R.id.tv_item_score);
        }
    }
}
