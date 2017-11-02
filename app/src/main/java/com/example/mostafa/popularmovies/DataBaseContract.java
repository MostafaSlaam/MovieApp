package com.example.mostafa.popularmovies;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by mostafa on 10/30/2017.
 */

public class DataBaseContract {

    public static final String AUTHORITY="com.example.mostafa.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_TASKS = "MyMovies";


    public static final class DataBaseEntry implements BaseColumns
    {
        public static final Uri MyUri=BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build();
        public static final String TABLE_name="MyMovies";
        public static final String COLUMN_MOVIEID="movie_id";
        public static final String COLUMN_title="title";
        public static final String COLUMN_overview="overview";
        public static final String COLUMN_date="date";
    }
}
