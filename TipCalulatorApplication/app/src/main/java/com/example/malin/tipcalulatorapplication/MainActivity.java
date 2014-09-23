package com.example.malin.tipcalulatorapplication;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements View.OnClickListener, Chronometer.OnChronometerTickListener {


    //Initializes variables

    EditText billEditText, tipEditText, editFinalText;//EditText variavles

    SeekBar seekBar;//seekBar variables

    CheckBox boxFriendly, boxSpecial, boxOpinion;//checkBox varialels

    RadioGroup radioGroup;//RadioButton varialbles

    Spinner spinner;//set spinner

    Chronometer timer;//set chronometer

    int tick = -2;// Had to define the tick with minus 2 sek beacouse it tick every 8 sec.//

    Button buttonStart, buttonPause, buttonReset;//Set buttons

    long lastPause = 0;

    int ticktiploss = 0;//placeholder for minus the tip





        //To start the app, use the initialized variables.
        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set all variabels to th id created in the .xml file.

        billEditText = (EditText)findViewById(R.id.billEditText);

        tipEditText = (EditText)findViewById(R.id.tipEditText);

        editFinalText = (EditText)findViewById(R.id.editFinalText);

        seekBar = (SeekBar)findViewById(R.id.seekBar);

        boxFriendly = (CheckBox)findViewById(R.id.checkBox1);
        boxSpecial = (CheckBox)findViewById(R.id.checkBox2);
        boxOpinion = (CheckBox)findViewById(R.id.checkBox3);

        timer = (Chronometer)findViewById(R.id.chronometer);
        buttonStart = (Button)findViewById(R.id.buttonStart);
        buttonPause = (Button)findViewById(R.id.buttonPause);
        buttonReset = (Button)findViewById(R.id.buttonReset);


        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);

        spinner = (Spinner)findViewById(R.id.spinner);

        //Set timelisterner on tick
        timer.setOnChronometerTickListener(this);

        //Set up the arrayadapter, to control the spinner options.

        ArrayAdapter <CharSequence> adapter = ArrayAdapter.createFromResource
                (this,R.array.spinnerArray,android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

            //Adapter that define and calculate the spinner.
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                calc();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            //Method that define and calculate the seekbar.
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tipEditText.setText(i+"%");
                calc();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

            // This method will calculate the values,if checked a value will appear,
            // bad with minus och good with plus 1%.
            public void calc(){

                int boxFvalue;
                int boxSvalue;
                int boxOvalue;

                int radioValue;
                int spinnerValue;


                //Box values:
                Float billPrice = Float.parseFloat(billEditText.getText().toString());

                if (boxFriendly.isChecked())
                    boxFvalue = 1;
                else
                    boxFvalue = 0;

                if (boxSpecial.isChecked())
                    boxSvalue = 1;
                else
                    boxSvalue = 0;

                if (boxOpinion.isChecked())
                    boxOvalue = 1;
                else
                    boxOvalue = 0;

                //Spinner values:

                if (spinner.getSelectedItemPosition() == 0)
                    spinnerValue = -1;
                else if (spinner.getSelectedItemPosition()== 1)
                    spinnerValue = 1;
                else
                    spinnerValue = 0;


                //Radiobuttons value:

               if (radioGroup.findViewById(radioGroup.getCheckedRadioButtonId()) == findViewById(R.id.radioBad))
                   radioValue = -1;
               else  if (radioGroup.findViewById(radioGroup.getCheckedRadioButtonId()) == findViewById(R.id.radioGood))
                   radioValue = 1;
               else
                   radioValue = 0;

                //Values will change the bill price and divided it.
               Float value = billPrice*(Float.parseFloat(seekBar.getProgress()+"")/100);

               value = value+(billPrice*(boxFvalue+boxSvalue+boxOvalue)/100);

               value = value+(billPrice*radioValue/100);

               value = value+(billPrice*spinnerValue/100);

               value = value+billPrice;

               value = value+(billPrice*ticktiploss /100);

                //Total bill=text field with 2 decimals.
               editFinalText.setText(String.format("%.2f", value));




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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

    }

    public void boxClick(View view) {

        calc();
    }
    //Chronometer that in 30 sek interval will redraw 1 % tip from the bill.
    @Override
    public void onChronometerTick(Chronometer chronometer) {

        tick ++;

        if ((tick % 30) == 0 && tick > 1){
            ticktiploss --;
            calc();
            
        }





    }
    //Buttons that will start, pause and reset the chronometer.
    public void buttonClick(View view) {

        switch (view.getId()){
            case R.id.buttonStart:
                timer.start();
                timer.setBase(SystemClock.elapsedRealtime()+lastPause );
                break;
            case R.id.buttonPause:
                timer.stop();
                lastPause = timer.getBase() - SystemClock.elapsedRealtime();
                break;
            case R.id.buttonReset:
                timer.setBase(SystemClock.elapsedRealtime());
                lastPause = 0;
                break;
        }

    }


}
