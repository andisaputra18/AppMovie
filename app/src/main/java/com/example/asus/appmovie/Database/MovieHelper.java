package com.example.asus.appmovie.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.asus.appmovie.Model.DataMovie;

import java.util.ArrayList;

public class MovieHelper {
    private static final String DATABASE_TABLE = DatabaseContract.TABLE_MOVIE;
    private static DatabaseHelper databaseHelper;
    private static MovieHelper INSTANCE;
    private static SQLiteDatabase database;

    private MovieHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static MovieHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new MovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close(){
        databaseHelper.close();

        if (database.isOpen())
            database.close();

    }

    public ArrayList<DataMovie> getAllMovie(){
        ArrayList<DataMovie> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                DatabaseContract.MovieColumns._ID + " ASC",
                null);
        cursor.moveToFirst();
        DataMovie dataMovie;
        if (cursor.getCount() > 0 ){
            do {
                dataMovie = new DataMovie();
                dataMovie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns._ID)));
                dataMovie.setMvPoster(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.POSTER)));
                dataMovie.setMvTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.TITLE)));
                dataMovie.setMvScore(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.VOTE_AVERAGE)));
                dataMovie.setMvRelease(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.RELEASE_DATE)));
                dataMovie.setMvOverview(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.OVERVIEW)));

                arrayList.add(dataMovie);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        Log.e("CHECK CURSOR", arrayList.toString());
        cursor.close();
        return arrayList;
    }

    public DataMovie getFavoriteById(int id){
        Cursor cursor = database.query(DATABASE_TABLE, null,
                DatabaseContract.MovieColumns._ID+ " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                DatabaseContract.MovieColumns._ID + " ASC",
                null);

        cursor.moveToFirst();
        DataMovie dataMovie = new DataMovie();
        if (cursor.getCount() > 0 ){
            do {
                dataMovie = new DataMovie();
                dataMovie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns._ID)));
                dataMovie.setMvPoster(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.POSTER)));
                dataMovie.setMvTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.TITLE)));
                dataMovie.setMvScore(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.VOTE_AVERAGE)));
                dataMovie.setMvRelease(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.RELEASE_DATE)));
                dataMovie.setMvOverview(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.MovieColumns.OVERVIEW)));
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return dataMovie;
    }

    public long insertMovie(DataMovie dataMovie){
        ContentValues args = new ContentValues();
        args.put(DatabaseContract.MovieColumns._ID, dataMovie.getId());
        args.put(DatabaseContract.MovieColumns.POSTER, dataMovie.getMvPoster());
        args.put(DatabaseContract.MovieColumns.TITLE, dataMovie.getMvTitle());
        args.put(DatabaseContract.MovieColumns.VOTE_AVERAGE, dataMovie.getMvScore());
        args.put(DatabaseContract.MovieColumns.RELEASE_DATE, dataMovie.getMvRelease());
        args.put(DatabaseContract.MovieColumns.OVERVIEW, dataMovie.getMvOverview());
        Log.e("RESULT INSERT MOVIE", String.valueOf(dataMovie));
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int deleteMovie(int id){
        return database.delete(DatabaseContract.TABLE_MOVIE, DatabaseContract.MovieColumns._ID + " = '" + id + "'", null);
    }
}
