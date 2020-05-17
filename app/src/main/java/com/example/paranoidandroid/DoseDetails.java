package com.example.paranoidandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.paranoidandroid.Model.Dose;
import com.example.paranoidandroid.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DoseDetails extends AppCompatActivity {

    private EditText description, dateTag, timeTag, quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Dose dose = (Dose) getIntent().getSerializableExtra("selectedDose");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dose_details);

        Date dateAndTime = dose.getDateAndTime();
        String DateString = new SimpleDateFormat("dd/MM/yyyy",
                Locale.getDefault()).format(dateAndTime);
        String TimeString = new SimpleDateFormat("HH:mm",
                Locale.getDefault()).format(dateAndTime);

        dateTag =  findViewById(R.id.date);
        dateTag.setText(DateString);

        timeTag = findViewById(R.id.time);
        timeTag.setText(TimeString);

        System.out.println(dose.getQte());

        quantity = findViewById(R.id.quantity);
        quantity.setText(dose.getQte().toString());

        description = findViewById(R.id.description);
        description.setText(dose.getDescription());

    }
}
