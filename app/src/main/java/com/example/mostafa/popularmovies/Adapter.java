package com.example.mostafa.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mostafa on 9/28/2017.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    private ArrayList<Movie> myImages;
    private Context context;

    public interface  ListItemClickListener
    {
        void OnListItemClick(int index);



    }

    final private ListItemClickListener mOnClickListener;

    public Adapter(Context context, ArrayList<Movie> Images,ListItemClickListener listener) {
        this.context=context;
        myImages=Images;
        mOnClickListener=listener;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       // Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Picasso.with(context).load("http://image.tmdb.org/t/p/w185"+myImages.get(position).getImage()).into(holder.imageView);



    }

    @Override
    public int getItemCount() {
        return myImages.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView =(ImageView)itemView.findViewById(R.id.iv);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            int ClickedPosition=getAdapterPosition();
            mOnClickListener.OnListItemClick(ClickedPosition);

        }
    }


}
