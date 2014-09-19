package com.example.malin.contactapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class contactActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch (id){

            case R.id.action_contact:
                Toast.makeText(this, "Contact",Toast.LENGTH_SHORT).show();
                Intent contact = new Intent(this, addContactActivity.class);
                startActivity(contact);

                //kopplar meny till ny aktivitet - contact.
                break;

            case R.id.action_random:
                Intent newRandom = new Intent(this, newRandomActivity.class);
                startActivity(newRandom);
                Toast.makeText(this, "Random",Toast.LENGTH_SHORT).show();
                //kopplar meny till ny aktivitet random.
                break;

            case R.id.action_color:
                Intent color = new Intent(this, colorActivity.class);
                startActivity(color);
                Toast.makeText(this, "Color",Toast.LENGTH_SHORT).show();
                 //kopplar meny till ny aktivitet color.

                return true;
                //Startar aktivitet meny




        }


        return super.onOptionsItemSelected(item);
    }
}
