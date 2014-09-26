package com.example.malin.lab2bapplication.Activities;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.malin.lab2bapplication.Data.GalleryDbHelper;
import com.example.malin.lab2bapplication.Adapters.PicassoAdapter;
import com.example.malin.lab2bapplication.R;


public class MainActivity extends ListActivity {


    PicassoAdapter picassoAdapter;
    GalleryDbHelper galleryDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create an instance of Gallery Database
        galleryDbHelper = new GalleryDbHelper(this);

        // Get latest data
        Cursor galleryCursor = galleryDbHelper.get();

        // Create an instance of adapter, pass context and galleryCursor from database
        picassoAdapter = new PicassoAdapter(this, galleryCursor, false);

        // Set the adapter on the listview
        setListAdapter(picassoAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent addIntent = new Intent(this, AddContactActivity.class);
            startActivityForResult(addIntent,1);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //when the item is clicked it will open in a new window.
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent profileIntent = new Intent(this, ContactProfile.class);

        //profileIntent.putExtra send the value of "id" to contactProfile activity .
        profileIntent.putExtra("id",(int) id);
        startActivity(profileIntent);
    }
    //Called when an activity you launched exits, giving you the requestCode you started it with,
    // the resultCode it returned, and any additional data from it.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Get latest data
        Cursor galleryCursor = galleryDbHelper.get();

        // Set new cursor data on the adapter.
        picassoAdapter.changeCursor(galleryCursor);

        // Notify data set changed
        picassoAdapter.notifyDataSetChanged();
    }

}