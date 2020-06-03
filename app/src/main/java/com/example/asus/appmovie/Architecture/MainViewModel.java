package com.example.asus.appmovie.Architecture;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.asus.appmovie.Model.DataMovie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainViewModel extends ViewModel {
    private static final String API_KEY = "96f4aea80d04494d2b1659b016f6f8f9";
    private MutableLiveData<ArrayList<DataMovie>> listMovie = new MutableLiveData<>();

    public void setListUpComing(final String movie){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<DataMovie> items = new ArrayList<>();

        String url = "https://api.themoviedb.org/3/"+movie+"/upcoming?api_key="+API_KEY+"&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    //lalu loading = false disini karena data sudah dimuat

                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        DataMovie modelMovie = new DataMovie(movie);
                        items.add(modelMovie);
                    }
                    listMovie.postValue(items);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public void setListNowPlaying(final String movie){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<DataMovie> items = new ArrayList<>();

        String url = "https://api.themoviedb.org/3/"+movie+"/now_playing?api_key="+API_KEY+"&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    //lalu loading = false disini karena data sudah dimuat

                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        DataMovie modelMovie = new DataMovie(movie);
                        items.add(modelMovie);
                    }
                    listMovie.postValue(items);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public void setListPopular(final String movie){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<DataMovie> items = new ArrayList<>();

        String url = "https://api.themoviedb.org/3/"+movie+"/popular?api_key="+API_KEY+"&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    //lalu loading = false disini karena data sudah dimuat

                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        DataMovie modelMovie = new DataMovie(movie);
                        items.add(modelMovie);
                    }
                    listMovie.postValue(items);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public void setSearchMovie(final String movie, String query){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<DataMovie> items = new ArrayList<>();

        String url = "https://api.themoviedb.org/3/search/"+movie+"?api_key="+API_KEY+"&language=en-US&query="+query+"";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    //lalu loading = false disini karena data sudah dimuat

                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        DataMovie modelMovie = new DataMovie(movie);
                        items.add(modelMovie);
                    }
                    listMovie.postValue(items);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public LiveData<ArrayList<DataMovie>> getListMovie() {
        return listMovie;
    }
}
