package com.example.malin.lab2bapplication.Adapters;



import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.malin.lab2bapplication.R;
import com.squareup.picasso.Picasso;

import static com.example.malin.lab2bapplication.Data.Contracts.ContractGallery.Gallery;


public class PicassoAdapter extends CursorAdapter {

    //initializes variables
    LayoutInflater inflater;
    ViewHolder viewHolder;
    int count;


    public PicassoAdapter(Context context, Cursor cursor, boolean autoRequery) {
        super(context, cursor, autoRequery);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        count++;
        viewHolder = new ViewHolder();

        View view = inflater.inflate(R.layout.row_gallery_frame, parent, false);
        viewHolder.urlHolder = (ImageView)view.findViewById(R.id.urlIv);
        viewHolder.nameHolder = (TextView)view.findViewById(R.id.nameTv);


        view.setTag(viewHolder);
        Log.d("NewInstance", count+"");
        return view;
    }
    //Data sets in bind view
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        viewHolder = (ViewHolder)view.getTag();

        viewHolder.nameHolder.setText(cursor.getString((cursor.getColumnIndex(Gallery.COLUMN_NAME_NAME))));
        Picasso.with(context).load(cursor.getString(cursor.getColumnIndex(Gallery.COLUMN_NAME_URL))).placeholder(R.drawable.ic_launcher).resize(460, 325).into(viewHolder.urlHolder);
    }
    //Viewholder is a holder for data, so when you scroll it will only go to and render bindViev
    static class ViewHolder{
        public TextView nameHolder;
        public ImageView urlHolder;
    }
}
