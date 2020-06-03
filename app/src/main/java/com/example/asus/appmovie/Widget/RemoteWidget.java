package com.example.asus.appmovie.Widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.asus.appmovie.Database.DatabaseContract;
import com.example.asus.appmovie.Model.DataMovie;
import com.example.asus.appmovie.R;

import java.util.concurrent.ExecutionException;

public class RemoteWidget implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private Cursor cursor;

    RemoteWidget(Context context, Intent intent) {
        mContext = context;
        int mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    private DataMovie getFav(int position){
        if (!cursor.moveToPosition(position)){
            throw new IllegalStateException("Position Invalid!");
        }

        return new DataMovie(
                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns._ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.POSTER)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.TITLE)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.VOTE_AVERAGE)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.RELEASE_DATE)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.OVERVIEW))
        );
    }

    @Override
    public void onCreate() {
        cursor = mContext.getContentResolver().query(
                DatabaseContract.MovieColumns.CONTENT_URI,
                null,
                null,
                null,
                null
        );
    }

    @Override
    public void onDataSetChanged() {
        if (cursor != null){
            cursor.close();
        }

        final long identityToken = Binder.clearCallingIdentity();
        cursor = mContext.getContentResolver().query(DatabaseContract.MovieColumns.CONTENT_URI, null, null, null, null);
        Log.d("Cursor", String.valueOf(cursor));
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {
        if (cursor != null){
            cursor.close();
        }
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        DataMovie dataMovie = getFav(position);
        RemoteViews rvFav = new RemoteViews(mContext.getPackageName(), R.layout.item_widget);

        Log.d("Poster Widget", dataMovie.getMvPoster());
        Log.d("Text Widget", dataMovie.getMvTitle());
        Bitmap bitmap;
        try {
            bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(dataMovie.getMvPoster())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
            rvFav.setImageViewBitmap(R.id.img_widget, bitmap);
            rvFav.setTextViewText(R.id.tv_movie_title, dataMovie.getMvTitle());

        } catch (InterruptedException | ExecutionException e){
            Log.d("Widget Load Error", "error"+e);
        }

        Bundle extras = new Bundle();
        extras.putInt(MovieWidget.EXTRA_ITEM, position);
        Intent fillIntent = new Intent();
        fillIntent.putExtras(extras);

        rvFav.setOnClickFillInIntent(R.id.img_widget, fillIntent);
        return rvFav;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return cursor.moveToPosition(position) ? cursor.getLong(0) : position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
