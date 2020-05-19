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

/*        listView = (ListView) findViewById(R.id.patients);
        listView.setAdapter(adapter);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference = databaseReference.child("Paranoid Android");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    //Getting the data from snapshot
                    Treatment treatment =
                            postSnapshot.getValue(Treatment.class);
                    treatmentList.add(treatment.getSickness());
                    System.out.println(treatment.getSickness());
                    adapter = new ArrayAdapter<>(getApplicationContext(),
                            android.R.layout.simple_list_item_1,
                            treatmentList);
                    listView.setAdapter(adapter);


                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
*//*            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }*//*
        });
        */
        }
    public void goToForm(View v) {
        Intent intent = new Intent(this, TreatmentForm.class);
        startActivity(intent);
    }

    public void gotomap(View v) {
        Intent intent = new Intent(this , MapsActivity.class);
        startActivity(intent);
    }


}
