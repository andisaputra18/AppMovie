package com.example.asus.appmovie.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.asus.appmovie.Adapter.MovieAdapter;
import com.example.asus.appmovie.Database.MovieHelper;
import com.example.asus.appmovie.Model.DataMovie;
import com.example.asus.appmovie.R;

public class DetailActivity extends AppCompatActivity {
    public Context context;
    public ProgressBar progressBar;
    public MovieAdapter adapter;
    TextView tvTitle, tvRelease, tvOverview, tvScore;
    ImageView imgPoster;
    Button btnFavorite, btnDelete;

    private MovieHelper movieHelper;
    public static final int RESULT_ADD = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final DataMovie dataMovie = getIntent().getExtras().getParcelable("detail");

        adapter = new MovieAdapter(context, "movie");
        adapter.notifyDataSetChanged();

        context = DetailActivity.this;
        progressBar = findViewById(R.id.progressBar);

        imgPoster = findViewById(R.id.img_photo_received);
        tvTitle = findViewById(R.id.tv_name_received);
        tvScore = findViewById(R.id.tv_score_received);
        tvRelease = findViewById(R.id.tv_release_received);
        tvOverview = findViewById(R.id.tv_overview_received);
        btnFavorite = findViewById(R.id.btn_favorite);
        btnDelete = findViewById(R.id.btn_delete);

        Glide.with(this)
                .load(dataMovie.getMvPoster())
                .into(imgPoster);
        tvTitle.setText(dataMovie.getMvTitle());
        tvScore.setText(dataMovie.getMvScore());
        tvRelease.setText(dataMovie.getMvRelease());
        tvOverview.setText(dataMovie.getMvOverview());

        movieHelper = MovieHelper.getInstance(getApplicationContext());
        movieHelper.open();

        DataMovie fav = movieHelper.getFavoriteById(dataMovie.getId());
        if (fav.getId() != 0){
            btnFavorite.setVisibility(View.GONE);
            btnDelete.setVisibility(View.VISIBLE);
        } else {
            btnFavorite.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.GONE);
        }

        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long result = movieHelper.insertMovie(dataMovie);
                if (result > 0){
                    dataMovie.setId((int) result);
                    setResult(RESULT_ADD, getIntent());
                    btnFavorite.setVisibility(View.GONE);
                    btnDelete.setVisibility(View.VISIBLE);
                    Toast.makeText(DetailActivity.this, "Movie added to Favorites", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(DetailActivity.this, "Failed added to Favorites", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long result = movieHelper.deleteMovie(dataMovie.getId());
                if (result > 0){
                    btnFavorite.setVisibility(View.VISIBLE);
                    btnDelete.setVisibility(View.GONE);
                    Toast.makeText(DetailActivity.this, "Success delete from Favorites", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DetailActivity.this, "Failed delete from Favorites", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
