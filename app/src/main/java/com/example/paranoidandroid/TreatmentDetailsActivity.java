package com.example.paranoidandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.paranoidandroid.Model.Treatment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class TreatmentDetailsActivity extends AppCompatActivity {

    TextView sickness,startDate, endDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment_details);
        Treatment treatment = (Treatment)
                getIntent().getSerializableExtra("selectedTreatment");


        System.out.println(treatment);
        System.out.println(treatment.getStart_date().getDate());
        System.out.println(treatment.getStart_date().getMonth());
        System.out.println(treatment.getStart_date().getYear());

        String startDateString = new SimpleDateFormat("dd/MM/yyyy",
                Locale.getDefault()).format(treatment.getStart_date());
        String endDateString = new SimpleDateFormat("dd/MM/yyyy",
                Locale.getDefault()).format(treatment.getEnd_date());
        startDate = (TextView) findViewById(R.id.startDate);
        startDate.setText(startDateString);

        endDate = (TextView) findViewById(R.id.endDate);
        endDate.setText(endDateString);

        sickness.setText(treatment.getSickness());

        Date date = new Date()



    }

    public void updateTreatment(View view) {
    }
}
