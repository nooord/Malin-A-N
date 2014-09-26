package com.example.malin.lab2bapplication.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.malin.lab2bapplication.Data.GalleryDbHelper;
import com.example.malin.lab2bapplication.Models.GalleryFrame;
import com.example.malin.lab2bapplication.R;

public class AddContactActivity extends Activity {

    //initializes variables
    EditText name;
    EditText age;
    EditText description;
    EditText image;

    // Create a new instans of the gallery database.
    GalleryDbHelper galleryDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        // Find id from xml files.
        name = (EditText)findViewById(R.id.nameText);
        age = (EditText)findViewById(R.id.ageText);
        description = (EditText)findViewById(R.id.descripText);
        image = (EditText)findViewById(R.id.imgUrl);

        // Create a new instans of the gallery database.
        galleryDbHelper = new GalleryDbHelper(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_add) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void addGalleryFrame(View view) {
        // Create a new object of GalleryFrame
        GalleryFrame galleryFrame = new GalleryFrame(image.getText() + "", name.getText() +"", age.getText() + "", description.getText() + "");
        Log.d("AddContact", galleryFrame.getUrl());

        // Insert galleryFrame object
        galleryDbHelper.insert(galleryFrame);

        finish();
    }



}
