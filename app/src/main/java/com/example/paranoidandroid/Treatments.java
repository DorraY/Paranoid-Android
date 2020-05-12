package com.example.paranoidandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Date;
import java.util.List;

import static android.app.PendingIntent.getActivity;

public class Treatments extends AppCompatActivity {

    Button addPatientButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_treatments);
        addPatientButton = findViewById(R.id.addTreatment);

        FirebaseApp.initializeApp(this);
        final DatabaseReference myRef =
                FirebaseDatabase.getInstance().getReference("Programme/");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                List<String>  treatmentList = new ArrayList<>();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Treatment treatment = postSnapshot.getValue(Treatment.class);
                    Integer treamentId = treatment.getNum_p();
                    String maladie = treatment.getSickness();
                    Log.d("TAG",treamentId+" / "+maladie);
                    treatmentList.add(treatment.getSickness());

                    //System.out.println(postSnapshot.child("sickness").getValue().toString());
                }
                ListView listView = (ListView) findViewById(R.id.treatments);
                ArrayAdapter arrayAdapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1,treatmentList);
                listView.setAdapter(arrayAdapter);
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
