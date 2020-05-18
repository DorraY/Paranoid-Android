package com.example.paranoidandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.paranoidandroid.Model.Temperature;
import com.example.paranoidandroid.Model.Treatment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Temperatures extends AppCompatActivity {

    ListView temperaturesListView;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    final DatabaseReference tempRef = firebaseDatabase.getReference(
            "Temperature");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperatures);

        final Treatment selectedTreatment = (Treatment)
                getIntent().getSerializableExtra("selectedTreatment");

        tempRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Double> temperatureDegreeList = new ArrayList<>();
                List<Temperature> temperatureList = new ArrayList<>();

                for (DataSnapshot tempSnapshot: dataSnapshot.getChildren()) {
                    Temperature temperature = tempSnapshot.getValue(Temperature.class);
                    if (temperature.getNum_p().getNum_p().equals(selectedTreatment.getNum_p())) {
                        temperatureDegreeList.add(temperature.getDegree());
                        temperatureList.add(temperature);
                    }
                    temperaturesListView = findViewById(R.id.temperatures);
                    ArrayAdapter arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                            android.R.layout.simple_list_item_1,temperatureDegreeList);
                    temperaturesListView.setAdapter(arrayAdapter);






                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " );
                System.out.println(databaseError);
            }
        });


    }

    public void addTemperature(View view) {
        final Treatment selectedTreatment = (Treatment)
                getIntent().getSerializableExtra("selectedTreatment");

        Intent intent = new Intent(getApplicationContext(),TemperatureForm.class);
        intent.putExtra("selectedTreatment",selectedTreatment);
        startActivity(intent);
        finish();
    }
}
