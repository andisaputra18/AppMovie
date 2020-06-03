package com.example.asus.appmovie.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class DataMovie implements Parcelable {
    private int id;
    private String MvPoster;
    private String MvTitle;
    private String MvScore;
    private String MvRelease;
    private String MvOverview;

    public DataMovie(int mId, String poster, String title, String score, String release, String overview) {
        id = mId;
        MvPoster = poster;
        MvTitle = title;
        MvScore = score;
        MvRelease = release;
        MvOverview = overview;
    }

    public DataMovie(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMvPoster() {
        return MvPoster;
    }

    public void setMvPoster(String mvPoster) {
        MvPoster = mvPoster;
    }

    public String getMvTitle() {
        return MvTitle;
    }

    public void setMvTitle(String mvTitle) {
        MvTitle = mvTitle;
    }

    public String getMvScore() {
        return MvScore;
    }

    public void setMvScore(String mvScore) {
        MvScore = mvScore;
    }

    public String getMvRelease() {
        return MvRelease;
    }

    public void setMvRelease(String mvRelease) {
        MvRelease = mvRelease;
    }

    public String getMvOverview() {
        return MvOverview;
    }

    public void setMvOverview(String mvOverview) {
        MvOverview = mvOverview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(MvPoster);
        dest.writeString(MvTitle);
        dest.writeString(MvScore);
        dest.writeString(MvRelease);
        dest.writeString(MvOverview);
    }

    private DataMovie(Parcel in) {
        id = in.readInt();
        MvPoster = in.readString();
        MvTitle = in.readString();
        MvScore = in.readString();
        MvRelease = in.readString();
        MvOverview = in.readString();
    }

    public static final Creator<DataMovie> CREATOR = new Creator<DataMovie>() {
        @Override
        public DataMovie createFromParcel(Parcel in) {
            return new DataMovie(in);
        }

        @Override
        public DataMovie[] newArray(int size) {
            return new DataMovie[size];
        }
    };

    public DataMovie(JSONObject object){
        try{
            int id = object.getInt("id");
            String Poster = "https://image.tmdb.org/t/p/w342/"+object.getString("poster_path");
            String Title = object.getString("title");
            String Score = object.getString("vote_average");
            String Release = object.getString("release_date");
            String Overview = object.getString("overview");

            this.id = id;
            this.MvPoster = Poster;
            this.MvTitle = Title;
            this.MvScore = Score;
            this.MvRelease = Release;
            this.MvOverview = Overview;

        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "DataMovie{" +
                "id=" + id +
                ", MvPoster='" + MvPoster + '\'' +
                ", MvTitle='" + MvTitle + '\'' +
                ", MvScore='" + MvScore + '\'' +
                ", MvRelease='" + MvRelease + '\'' +
                ", MvOverview='" + MvOverview + '\'' +
                '}';
    }
}
