package com.example.mostafa.popularmovies;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

public class FavouriteActivity extends AppCompatActivity implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor>
         {

    private static final int LOADER_ID = 19;

    Adapter2 adapter2;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        recyclerView=(RecyclerView)findViewById(R.id.rv2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Here is where you'll implement swipe to delete

                // COMPLETED (1) Construct the URI for the item to delete
                //[Hint] Use getTag (from the adapter code) to get the id of the swiped item
                // Retrieve the id of the task to delete
                int id = (int) viewHolder.itemView.getTag();

                // Build appropriate uri with String row id appended
                String stringId =String.valueOf( id);
                Uri uri = DataBaseContract.DataBaseEntry.MyUri;
                uri = uri.buildUpon().appendPath(stringId).build();

                // COMPLETED (2) Delete a single row of data using a ContentResolver
                getContentResolver().delete(uri, null, null);

                // COMPLETED (3) Restart the loader to re-query for all tasks after a deletion
                getSupportLoaderManager().restartLoader(LOADER_ID, null, FavouriteActivity.this);

            }
        }).attachToRecyclerView(recyclerView);



        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }


             @Override
             public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {

                 return new android.support.v4.content.AsyncTaskLoader<Cursor>(this) {

                     Cursor cursor=null;
                     @Override
                     protected void onStartLoading() {
                         if (cursor != null) {
                             // Delivers any previously loaded data immediately
                             deliverResult(cursor);
                         } else {
                             // Force a new load
                             forceLoad();
                         }
                     }
                     public Cursor loadInBackground() {
                         try {
                            // Log.d("jhj", "loadInBackground: ");
                             Cursor ff= getContentResolver().query(DataBaseContract.DataBaseEntry.MyUri,
                                     null,
                                     null,
                                     null,
                                     null);
                           //  Log.d("zzz",String.valueOf(ff.getCount()));
                             return ff;

                         } catch (Exception e) {
                             Log.d("oh", "Failed to asynchronously load data.");
                             e.printStackTrace();
                             return null;
                         }
                     }
                     public void deliverResult(Cursor data) {
                         cursor = data;
                         super.deliverResult(data);
                     }


                 };
             }

             @Override
             public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
                // Log.d("zzz",String.valueOf(data.getCount()));
                 adapter2=new Adapter2(this,data);
                 recyclerView.setAdapter(adapter2);
             }

             @Override
             public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {

             }


             @Override
             protected void onResume() {
                 super.onResume();

                 // re-queries for all tasks
                 getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
             }

}
