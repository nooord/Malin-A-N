package com.example.malin.contactapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class addContactActivity extends Activity {

//Definierar variablerna så de kan hämtas till flera medoder.

    public EditText name, phone, mail;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

// View hämtar via deras id.

        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        mail = (EditText) findViewById(R.id.mail);

        final Button button = (Button) findViewById(R.id.buttonAd);//hämtar kanpp via id och sätter en onClick lyssnare.
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // Creates a new Intent to insert a contact
                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                // Sets the MIME type to match the Contacts Provider
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                //för att spara ifylld text.

                intent.putExtra(ContactsContract.Intents.Insert.NAME, name.getText().toString())
                        .putExtra(ContactsContract.Intents.Insert.PHONE, phone.getText())
                        .putExtra(ContactsContract.Intents.Insert.EMAIL, mail.getText());

                startActivity(intent);

                //skriver ut en popupp fönster att kontakten är skapad.
                System.out.println(Toast.makeText(getApplicationContext(), "Your contact is created", Toast.LENGTH_SHORT));
            }
        });


        //används när text ändras
        name.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                button.setEnabled(!name.getText().toString().trim().isEmpty());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
