package com.example.mostafa.popularmovies;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Details extends AppCompatActivity {
    String id;
    final static String BASE_URL = "https://api.themoviedb.org/3/movie/";
    final static String QUERY_API = "api_key";
    final static String APIKey = "34fcf6769ef28fbf7d7834dbaa0ab430";
    final static String QUERY_LANGUAGE = "language";
    final static String LANGUAGE = "en-US";
    ArrayList<String>videos=new ArrayList<>();
    ArrayAdapter<String>arrayAdapter;
    ListView listView;
    ArrayList<String>review=new ArrayList<>();
    ArrayAdapter<String>arrayAdapter2;
    ListView listView2;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         i=getIntent();
        id=i.getStringExtra("ID");
        setContentView(R.layout.activity_details);
        TextView textView=(TextView)findViewById(R.id.tv_title);
        textView.setText(" "+i.getStringExtra("title"));
        TextView textView1=(TextView)findViewById(R.id.tv_date);
        textView1.setText(i.getStringExtra("date"));
        TextView textView2=(TextView)findViewById(R.id.tv_overview);
        textView2.setText(i.getStringExtra("overview"));
        TextView textView3=(TextView)findViewById(R.id.tv_vote);
        textView3.setText(i.getStringExtra("vote_average")+"/10");
        ImageView imageView=(ImageView)findViewById(R.id.iv_Details);
        Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w185"+i.getStringExtra("image")).into(imageView);

        listView=(ListView)findViewById(R.id.LV);
        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);

        listView2=(ListView)findViewById(R.id.LV2);
        arrayAdapter2=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);

        new getExtra().execute();
        ImageButton button=(ImageButton)findViewById(R.id.imageButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(DataBaseContract.DataBaseEntry.COLUMN_MOVIEID, id);
                contentValues.put(DataBaseContract.DataBaseEntry.COLUMN_title, i.getStringExtra("title"));
                contentValues.put(DataBaseContract.DataBaseEntry.COLUMN_date, i.getStringExtra("date"));
                contentValues.put(DataBaseContract.DataBaseEntry.COLUMN_overview, i.getStringExtra("overview"));
                String[] args = {id};
                Cursor c = getContentResolver().query(DataBaseContract.DataBaseEntry.MyUri, null, "movie_id=?", args, null);
                if (c.getCount()==0) {
                    Uri uri = getContentResolver().insert(DataBaseContract.DataBaseEntry.MyUri, contentValues);
                    if (uri != null) {
                        Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
                    }
                }else
                {
                    Toast.makeText(getBaseContext(), "this movie marked as favourit", Toast.LENGTH_LONG).show();
                }
            }
        });


    }


    public class getExtra extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listView.setAdapter(arrayAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.youtube.com/watch?v="+videos.get(i)));
                    startActivity(intent);
                }
            });
            listView2.setAdapter(arrayAdapter2);
            listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent1=new Intent(Intent.ACTION_VIEW,Uri.parse(review.get(i)));
                    startActivity(intent1);
                }
            });
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpURLConnection connection2 = null;

            Uri fetchVideos=Uri.parse(BASE_URL).buildUpon()
                    .appendPath(id)
                    .appendPath("videos")
                    .appendQueryParameter(QUERY_API,APIKey)
                    .appendQueryParameter(QUERY_LANGUAGE, LANGUAGE).build();


            URL url2=null;
            try {


                url2 = new URL(fetchVideos.toString());
                Log.d("ttt",fetchVideos.toString() );
                connection2 = (HttpURLConnection) url2.openConnection();
                connection2.connect();
                InputStream stream2 = connection2.getInputStream();
                Scanner scanner2 = new Scanner(stream2);
                scanner2.useDelimiter("\\A");
                boolean hasInput2 = scanner2.hasNext();
                if (hasInput2) {
                    JSONObject parentObject2 = new JSONObject(scanner2.next());
                    JSONArray parentArray2 = parentObject2.getJSONArray("results");

                    for (int j = 0; j< parentArray2.length(); j++) {

                        JSONObject finalObject2 = parentArray2.getJSONObject(j);
                        videos.add(finalObject2.getString("key"));
                        arrayAdapter.add("Trailer"+String.valueOf(j+1));
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                connection2.disconnect();
            }





            HttpURLConnection connection3 = null;

            Uri fetchReviews=Uri.parse(BASE_URL).buildUpon()
                    .appendPath(id)
                    .appendPath("reviews")
                    .appendQueryParameter(QUERY_API,APIKey)
                    .appendQueryParameter(QUERY_LANGUAGE, LANGUAGE).build();


            URL url3=null;
            try {


                url3 = new URL(fetchReviews.toString());
               // Log.d("ttt",fetchReviews.toString() );
                connection3 = (HttpURLConnection) url3.openConnection();
                connection3.connect();
                InputStream stream3 = connection3.getInputStream();
                Scanner scanner3 = new Scanner(stream3);
                scanner3.useDelimiter("\\A");
                boolean hasInput3 = scanner3.hasNext();
                if (hasInput3) {
                    JSONObject parentObject3 = new JSONObject(scanner3.next());
                    JSONArray parentArray3 = parentObject3.getJSONArray("results");

                    for (int j = 0; j< parentArray3.length(); j++) {
                        Log.d("ttt","3" );
                        JSONObject finalObject3 = parentArray3.getJSONObject(j);
                        review.add(finalObject3.getString("url"));
                        arrayAdapter2.add("Review"+String.valueOf(j+1));
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                connection3.disconnect();
            }





            return null;
        }
    }
}
