package com.example.paranoidandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.paranoidandroid.Model.Treatment;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Treatments extends AppCompatActivity {

    ListView listView;
    Button addPatientButton;
    List<String> treatmentList = new ArrayList<>();
/*    ArrayAdapter<String> adapter= new ArrayAdapter<String>(this
            ,android.R.layout.simple_dropdown_item_1line, treatmentList);*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_treatments);
        addPatientButton = findViewById(R.id.addTreatment);

       listView = (ListView) findViewById(R.id.patients);

       treatmentList.add("test1");
       treatmentList.add("test2");
       treatmentList.add("test3");
       treatmentList.add("test4");
       treatmentList.add("test5");
       treatmentList.add("test6");
       treatmentList.add("test7");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                treatmentList );

        listView.setAdapter(arrayAdapter);



        }
    public void goToForm(View v) {
        Intent intent = new Intent(this, TreatmentForm.class);
        startActivity(intent);
    }


}
