package com.example.paranoidandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.paranoidandroid.Model.Treatment;
import com.firebase.ui.database.FirebaseListAdapter;
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

    Button addPatientButton;
    final List<String>  treatmentList = new ArrayList<>();
    //ListView listView  =  findViewById(R.id.treatments);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_treatments);
        addPatientButton = findViewById(R.id.addTreatment);

        FirebaseApp.initializeApp(this);
        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Programme/");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    treatmentList.add(postSnapshot.child("maladie").getValue().toString());
                    System.out.println(postSnapshot.child("maladie").getValue().toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " );
                System.out.println(databaseError);
            }
        });

        }
    public void goToForm(View v) {
        Intent intent = new Intent(this, TreatmentForm.class);
        startActivity(intent);
    }


}
