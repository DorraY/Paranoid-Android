package com.example.paranoidandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.paranoidandroid.Model.Dose;
import com.example.paranoidandroid.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DoseDetails extends AppCompatActivity {

    private EditText description, dateTag, timeTag, quantity;
    final DatabaseReference doseRef = FirebaseDatabase.getInstance().getReference(
            "Dose");


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

    public void updateDose(View view) throws ParseException {
        final Dose dose = (Dose) getIntent().getSerializableExtra("selectedDose");
        dateTag =  findViewById(R.id.date);
        timeTag = findViewById(R.id.time);
        quantity = findViewById(R.id.quantity);
        description = findViewById(R.id.description);
        Button b =    findViewById(R.id.update);

        if (b.getText().equals("Update")) {
            dateTag.setEnabled(true);
            timeTag.setEnabled(true);
            quantity.setEnabled(true);
            description.setEnabled(true);
        } else {
            dateTag.setEnabled(false);
            timeTag.setEnabled(false);
            quantity.setEnabled(false);
            description.setEnabled(false);
            b.setText("Save");

            dose.setDescription(description.getText().toString());
            dose.setQte(Integer.valueOf(quantity.getText().toString()));

            Date dateAndTime = dose.getDateAndTime();

            SimpleDateFormat simpleDateFormat = new
                    SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat simpleTimeFormat = new
                    SimpleDateFormat("HH:mm");

            dateAndTime.setYear(simpleDateFormat.parse(dateTag.getText().toString()).getYear());
            dateAndTime.setMonth(simpleDateFormat.parse(dateTag.getText().toString()).getMonth());
            dateAndTime.setDate(simpleDateFormat.parse(dateTag.getText().toString()).getDate());
            dateAndTime.setHours((simpleTimeFormat.parse(timeTag.getText().toString())).getHours());
            dateAndTime.setMinutes((simpleTimeFormat.parse(timeTag.getText().toString())).getMinutes());

            doseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    doseRef.child(dataSnapshot.getKey()).setValue(dose);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    public void deleteDose(View view) {
        final Dose dose = (Dose) getIntent().getSerializableExtra("selectedDose");
        final String doseId = (String) getIntent().getSerializableExtra("selectedDoseId");

        doseRef.child(doseId).removeValue();

        Intent intent = new Intent(getApplicationContext(),Treatments.class);
        startActivity(intent);

    }

    public void AbandonModifications(View view) {
        final Dose dose = (Dose) getIntent().getSerializableExtra("selectedDose");


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
