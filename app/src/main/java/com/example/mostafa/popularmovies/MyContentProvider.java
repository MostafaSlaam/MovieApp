package com.example.mostafa.popularmovies;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/**
 * Created by mostafa on 10/30/2017.
 */

public class MyContentProvider extends ContentProvider {
    private MoviesDB helper;
    public static final int MOVIES = 100;
    public static final int MOVIES_WITH_ID = 101;
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher()
    {
        UriMatcher uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(DataBaseContract.AUTHORITY,DataBaseContract.PATH_TASKS,MOVIES);

        uriMatcher.addURI(DataBaseContract.AUTHORITY,DataBaseContract.PATH_TASKS+"/#",MOVIES_WITH_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context=getContext();
        helper=new MoviesDB(context);
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = helper.getReadableDatabase();
        int match=sUriMatcher.match(uri);
        Cursor cursor;
        switch (match)
        {
            case(MOVIES):
                cursor=db.query(DataBaseContract.DataBaseEntry.TABLE_name,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder);
            break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        Log.d("xxx",String.valueOf( cursor.getCount()));
        Log.d("xxx",cursor.toString());
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {

        final SQLiteDatabase db=helper.getWritableDatabase();
        int match=sUriMatcher.match(uri);
        Uri returnUri;
        switch (match) {
            case MOVIES:
                long id = db.insert(DataBaseContract.DataBaseEntry.TABLE_name, null, contentValues);
                Log.d("ava",String.valueOf(id));
                if ( id > 0 ) {
                    returnUri = ContentUris.withAppendedId(DataBaseContract.DataBaseEntry.MyUri, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        final SQLiteDatabase db=helper.getWritableDatabase();
        int match=sUriMatcher.match(uri);
        int deleted;
        switch (match)
        {
            case MOVIES_WITH_ID:
                String id = uri.getPathSegments().get(1);
                Log.d("ffh",id);
                deleted = db.delete(DataBaseContract.DataBaseEntry.TABLE_name, "_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        Log.d("ffs",String.valueOf(deleted));
        if (deleted != 0) {
            // A task was deleted, set notification
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return deleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
