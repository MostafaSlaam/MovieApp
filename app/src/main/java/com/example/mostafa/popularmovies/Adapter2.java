package com.example.mostafa.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by mostafa on 11/2/2017.
 */

public class Adapter2 extends RecyclerView.Adapter<Adapter2.TaskView> {
    private Cursor cursor;
    private Context context;

    public Adapter2(Context context,Cursor cursor)
    {
        this.context=context;
        this.cursor=cursor;
    }

    @Override
    public TaskView onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("ji", "onCreateViewHolder: ");
        int layoutIdForListItem = R.layout.favourite_movies_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        TaskView viewHolder = new TaskView(view);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(Adapter2.TaskView holder, int position) {

        int idIndex = cursor.getColumnIndex(DataBaseContract.DataBaseEntry._ID);
        int Index1 = cursor.getColumnIndex(DataBaseContract.DataBaseEntry.COLUMN_title);
        int Index2 = cursor.getColumnIndex(DataBaseContract.DataBaseEntry.COLUMN_MOVIEID);
        int Index3 = cursor.getColumnIndex(DataBaseContract.DataBaseEntry.COLUMN_date);
        int Index4 = cursor.getColumnIndex(DataBaseContract.DataBaseEntry.COLUMN_overview);
        cursor.moveToPosition(position);
        final int id = cursor.getInt(idIndex);
        holder.itemView.setTag(id);
        holder.textView1.setText(cursor.getString(Index1));
        holder.textView2.setText("ID: "+cursor.getString(Index2));
        holder.textView3.setText(cursor.getString(Index3));
        holder.textView4.setText(cursor.getString(Index4));
    }


    public Cursor MyCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)

        if (cursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = cursor;
        this.cursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }

    @Override
    public int getItemCount() {
        if (cursor == null) {
            return 0;
        }
        return cursor.getCount();
    }

    public class TaskView extends RecyclerView.ViewHolder  {

        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;

        public TaskView(View itemView) {
            super(itemView);
            textView1=(TextView)itemView.findViewById(R.id.textView_RV1);
            textView2=(TextView)itemView.findViewById(R.id.textView_RV2);
            textView3=(TextView)itemView.findViewById(R.id.textView_RV3);
            textView4=(TextView)itemView.findViewById(R.id.textView_RV4);
        }


    }
}
