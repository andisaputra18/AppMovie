package com.example.asus.appmovie.Database;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    static final String AUTHORITY = "com.example.asus.appmovie";
    private static final String SCHEME = "content";
    static String TABLE_MOVIE = "movie";

    public DatabaseContract() {
    }

    public static final class MovieColumns implements BaseColumns {
        public static String POSTER = "poster";
        public static String TITLE = "title";
        public static String VOTE_AVERAGE = "vote_average";
        public static String RELEASE_DATE = "release_date";
        public static String OVERVIEW = "overview";

        // untuk membuat URI content
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_MOVIE)
                .build();
    }
}
