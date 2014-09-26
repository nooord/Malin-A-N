package com.example.malin.lab2bapplication.Activities;



import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.example.malin.lab2bapplication.Adapters.PicassoAdapter;
        import com.example.malin.lab2bapplication.Data.GalleryDbHelper;
        import com.example.malin.lab2bapplication.Models.GalleryFrame;
        import com.example.malin.lab2bapplication.R;
        import com.squareup.picasso.Picasso;

public class ContactProfile extends Activity {

    //initializes variables
    PicassoAdapter picassoAdapter;
    GalleryDbHelper galleryDbHelper;
    GalleryFrame galleryFrame;

    ImageView contactImage;
    TextView contactName;
    TextView contactAge;
    TextView contactDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_profile);

        // Create a new instans of the gallery database.
        galleryDbHelper = new GalleryDbHelper(this);

        // Find id from xml files.
        contactImage = (ImageView)findViewById(R.id.imageView);
        contactName = (TextView)findViewById(R.id.nameView);
        contactAge = (TextView)findViewById(R.id.ageView);
        contactDescription = (TextView)findViewById(R.id.descripView);

        //Return the intent that start the current activity.
        Intent profileIntent = getIntent();

        //Get the value of ID from previous MainActivity.
        int id = profileIntent.getIntExtra("id",0);

        //Get id from the database.
        galleryFrame = galleryDbHelper.getId(id);

        //PicassoAdaper get the value based on ID .
        Picasso.with(this).load(galleryFrame.getUrl()).placeholder(R.drawable.ic_launcher).into(contactImage);
        contactName.setText(galleryFrame.getName());
        contactAge.setText(galleryFrame.getAge());
        contactDescription.setText(galleryFrame.getDescription());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.contact_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_add) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
