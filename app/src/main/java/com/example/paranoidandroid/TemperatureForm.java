package com.example.paranoidandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.paranoidandroid.Model.Temperature;
import com.example.paranoidandroid.Model.Treatment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class TemperatureForm extends AppCompatActivity {

    EditText temperatureTag;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    final DatabaseReference tempRef = firebaseDatabase.getReference(
            "Temperature");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_form);

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
