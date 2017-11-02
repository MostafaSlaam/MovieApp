package com.example.mostafa.popularmovies;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity  {

    final static String BASE_URL = "https://api.themoviedb.org/3/movie/";
    String moviesCategory= "popular" ;
    private static final String MY_PARAMETER = "parameter";
    final static String QUERY_API = "api_key";
    final static String APIKey = "34fcf6769ef28fbf7d7834dbaa0ab430";
    final static String QUERY_LANGUAGE = "language";
    final static String LANGUAGE = "en-US";
    final static String QUERY_PAGE = "page";
    int pageNumber;



    Adapter mAdapter;
    RecyclerView MyRecyclerView;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.i_popular) {
            //Intent i =new Intent(MainActivity.this,MainActivity.class);
           // startActivity(i);
            moviesCategory="popular";
            new GetData().execute();

        }
        else if(item.getItemId()==R.id.i_top_rated)
        {
            //Intent i =new Intent(MainActivity.this,Main2Activity.class);
            //startActivity(i);
            moviesCategory="top_rated";
            new GetData().execute();

        }
        else if(item.getItemId()==R.id.i_favourit)
        {
            Intent intent=new Intent(MainActivity.this,FavouriteActivity.class);
            startActivity(intent);
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyRecyclerView = (RecyclerView) findViewById(R.id.rv);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            //Do some stuff
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
            MyRecyclerView.setLayoutManager(gridLayoutManager);
        }
        else {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
            MyRecyclerView.setLayoutManager(gridLayoutManager);
        }

        MyRecyclerView.setHasFixedSize(true);

        if (savedInstanceState != null) {
            moviesCategory = savedInstanceState.getString(MY_PARAMETER);
        }
        new GetData().execute();

    }




    public class GetData extends AsyncTask<Void, Void, ArrayList<Movie>> implements Adapter.ListItemClickListener {

        ArrayList<Movie>myImages=new ArrayList<Movie>();


        @Override
        protected void onPostExecute(ArrayList<Movie> strings) {
            super.onPostExecute(strings);
            mAdapter=new Adapter(getApplicationContext(),strings,this);
            Log.d("t123",String.valueOf( strings.size()));
            MyRecyclerView.setAdapter(mAdapter);


        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {

            HttpURLConnection connection = null;


            for (pageNumber=1;pageNumber<=5;pageNumber++ )
            {
                Uri uri1 = Uri.parse(BASE_URL).buildUpon()
                        .appendPath(moviesCategory)
                        .appendQueryParameter(QUERY_API, APIKey)
                        .appendQueryParameter(QUERY_LANGUAGE, LANGUAGE)
                        .appendQueryParameter(QUERY_PAGE, String.valueOf(pageNumber)).build();

                URL url1 = null;
                try {
                    url1 = new URL(uri1.toString());
                    connection = (HttpURLConnection) url1.openConnection();
                    connection.connect();
                    InputStream stream = connection.getInputStream();
                    Scanner scanner = new Scanner(stream);
                    scanner.useDelimiter("\\A");
                    boolean hasInput = scanner.hasNext();
                    if (hasInput) {
                        JSONObject parentObject = new JSONObject(scanner.next());
                        JSONArray parentArray = parentObject.getJSONArray("results");
                        for(int i=0;i<parentArray.length();i++)
                        {
                            Movie m=new Movie();
                            JSONObject finalObject = parentArray.getJSONObject(i);





                           m.setMovie(finalObject.getString("id"), finalObject.getString("title"),finalObject.getString("poster_path"),finalObject.getString("overview"),
                                   finalObject.getString("vote_average"),finalObject.getString("release_date"));
                            myImages.add(m);
                           // Log.d("m1","f4");

                        }
                    }







                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    connection.disconnect();
                }



            }
           return myImages;

        }


        @Override
        public void OnListItemClick(int index) {
           // Toast.makeText(getApplicationContext(),String.valueOf(index),Toast.LENGTH_LONG).show();
            Intent i=new Intent(MainActivity.this,Details.class);
            i.putExtra("ID",myImages.get(index).getMovie_ID());
            i.putExtra("title",myImages.get(index).getTitle());
            i.putExtra("image",myImages.get(index).getImage());
            i.putExtra("overview",myImages.get(index).getOverview());
            i.putExtra("vote_average",myImages.get(index).getVote_average());
            i.putExtra("date",myImages.get(index).getDate());
            startActivity(i);
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String myP = moviesCategory;
        outState.putString(MY_PARAMETER, myP);

    }
}
