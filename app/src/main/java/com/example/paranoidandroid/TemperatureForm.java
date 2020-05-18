package com.example.paranoidandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.paranoidandroid.Model.Temperature;
import com.example.paranoidandroid.Model.Treatment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TemperatureForm extends AppCompatActivity {

    EditText temperatureTag;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    final DatabaseReference tempRef = firebaseDatabase.getReference(
            "Temperature");

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            checkFields();
        }
    } ;

    void checkFields(){
        Button b =  findViewById(R.id.addButton);

        String temperature = temperatureTag.getText().toString();


        if(temperature.equals("")){
            b.setEnabled(false);
        } else {
            b.setEnabled(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_form);

        temperatureTag =  findViewById(R.id.temperature);
        temperatureTag.addTextChangedListener(mTextWatcher);



    }

    public void addTemperature(View view) {
        Treatment treatment = (Treatment)
                getIntent().getSerializableExtra("selectedTreatment");

        Temperature temperature = new Temperature();

        temperatureTag =  findViewById(R.id.temperature);

        temperature.setNum_p(treatment);
        temperature.setDegree(Double.parseDouble(String.valueOf(temperatureTag.getText())));

        tempRef.child(String.valueOf(temperature.getNumTemp())).setValue(temperature);

        System.out.println(temperature);

        Intent intent = new Intent(this,Treatments.class);
        startActivity(intent);
        finish();

    }
}
