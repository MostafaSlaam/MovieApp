package com.example.mostafa.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mostafa on 10/30/2017.
 */

public class MoviesDB extends SQLiteOpenHelper {
    private final static String DATABASE_NAME="Movies.DB";
    private static final int DATABASE_VERSION = 2;
    public MoviesDB(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CreateMyTable="create table "+ DataBaseContract.DataBaseEntry.TABLE_name+"("+
                DataBaseContract.DataBaseEntry._ID +" integer primary key ,"+
                DataBaseContract.DataBaseEntry.COLUMN_MOVIEID +" text not null,"+
                DataBaseContract.DataBaseEntry.COLUMN_title +" text not null,"+
                DataBaseContract.DataBaseEntry.COLUMN_overview +" text not null,"+
                DataBaseContract.DataBaseEntry.COLUMN_date +" text not null"+
                "); ";
        sqLiteDatabase.execSQL(CreateMyTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ DataBaseContract.DataBaseEntry.TABLE_name);
        onCreate(sqLiteDatabase);
    }

}
