package com.example.paranoidandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.paranoidandroid.Model.Treatment;

import static com.example.paranoidandroid.R.id.details;

public class TreatmentDetailsActivity extends AppCompatActivity {

    TextView textView = (TextView) findViewById(details);

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        System.out.println(textView);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment_details);
        Treatment treatment = (Treatment)
                getIntent().getSerializableExtra("selectedTreatment");
        textView.setText(treatment.toString());
    }
}
