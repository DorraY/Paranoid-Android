package com.example.paranoidandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.paranoidandroid.Model.Dose;
import com.example.paranoidandroid.Model.Medicine;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Doses extends AppCompatActivity {

    Button addDose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doses);
        addDose = findViewById(R.id.newDose);

        FirebaseApp.initializeApp(this);
        final DatabaseReference doseRef =
                FirebaseDatabase.getInstance().getReference("Dose/");

        doseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> doseDescriptionList = new ArrayList<>();
                final List <Dose> doseList = new ArrayList<>();

                for (DataSnapshot doseSnapshot: dataSnapshot.getChildren()) {
                    Dose dose = doseSnapshot.getValue(Dose.class);
                    Integer doseId = dose.getDoseId();
                    String description = dose.getDescription();
                    doseDescriptionList.add(description);
                    doseList.add(dose);
                }
                ListView listView = findViewById(R.id.doses);
                ArrayAdapter arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                        android.R.layout.simple_list_item_1,doseDescriptionList);
                listView.setAdapter(arrayAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String selectedItem = (String) parent.getItemAtPosition(position);
                        Intent intent = new Intent(getApplicationContext(),DoseDetails.class);
                        int i=0;
                        while(!doseList.get(i).getDescription().equals(selectedItem)) {
                            i++;
                        }
                        intent.putExtra("selectedDose",doseList.get(i));
                        startActivity(intent);
                        finish();
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " );
                System.out.println(databaseError);
            }
        });
    }
}
