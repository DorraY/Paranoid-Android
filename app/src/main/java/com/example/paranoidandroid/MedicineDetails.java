package com.example.paranoidandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.paranoidandroid.Model.Medicine;
import com.example.paranoidandroid.Model.Treatment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MedicineDetails extends AppCompatActivity {
    private EditText startDate, endDate,refMed;
    final DatabaseReference medRef =
            FirebaseDatabase.getInstance().getReference("Medicament");
    final DatabaseReference linetRef = FirebaseDatabase.getInstance().getReference(
            "Ligne_medicament");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_details);
        Medicine medicine  = (Medicine) getIntent().getSerializableExtra("selectedMedicine");
        String startDateString = new SimpleDateFormat("dd/MM/yyyy",
                Locale.getDefault()).format(medicine.getDateDebCons());
        String endDateString = new SimpleDateFormat("dd/MM/yyyy",
                Locale.getDefault()).format(medicine.getDateEnd());
        startDate =   findViewById(R.id.startDate);
        startDate.setText(startDateString);
        //startDate.addTextChangedListener(mTextWatcher);

        endDate =   findViewById(R.id.endDate);
        endDate.setText(endDateString);
        //endDate.addTextChangedListener(mTextWatcher);

        refMed = findViewById(R.id.refMed);
        refMed.setText(medicine.getRefMed());

    }

    public void AbandonModifications(View view) {
        Medicine medicine  = (Medicine) getIntent().getSerializableExtra("selectedMedicine");
        String startDateString = new SimpleDateFormat("dd/MM/yyyy",
                Locale.getDefault()).format(medicine.getDateDebCons());
        String endDateString = new SimpleDateFormat("dd/MM/yyyy",
                Locale.getDefault()).format(medicine.getDateEnd());
        startDate =   findViewById(R.id.startDate);
        startDate.setText(startDateString);
        //startDate.addTextChangedListener(mTextWatcher);

        endDate =   findViewById(R.id.endDate);
        endDate.setText(endDateString);
        //endDate.addTextChangedListener(mTextWatcher);

        refMed = findViewById(R.id.refMed);
        refMed.setText(medicine.getRefMed());

    }

    public void updateTreatment(View view) throws ParseException {
        Medicine medicine  = (Medicine) getIntent().getSerializableExtra("selectedMedicine");
        Treatment treatment = (Treatment) getIntent().getSerializableExtra("selectedTreatment");
        startDate =   findViewById(R.id.startDate);
        endDate =   findViewById(R.id.endDate);
        refMed = findViewById(R.id.refMed);

        Button b =    findViewById(R.id.update);
        if (b.getText().equals("Update")) {
            refMed.setEnabled(true);
            startDate.setEnabled(true);
            endDate.setEnabled(true);
            b.setText("Save");
        }
        else {
            refMed.setEnabled(false);
            startDate.setEnabled(false);
            endDate.setEnabled(false);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

            medicine.setRefMed(refMed.getText().toString());

            Date start = simpleDateFormat.parse(startDate.getText().toString());
            Date end = simpleDateFormat.parse(endDate.getText().toString());
            medicine.setDateDebCons(start);
            medicine.setDateEnd(end);

            medRef.child(String.valueOf(medicine.getRefMed())).setValue(medicine);

            linetRef.child(String.valueOf(treatment.getNum_p())).child("refMed").setValue(medicine);


            b.setText("Update");

        }





    }

    public void checkDoses(View view) {
        Intent intent = new Intent(this, Doses.class);
        startActivity(intent);
        finish();
    }
}
