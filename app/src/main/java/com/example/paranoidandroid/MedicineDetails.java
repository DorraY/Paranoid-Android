package com.example.paranoidandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.paranoidandroid.Model.Dose;
import com.example.paranoidandroid.Model.Medicine;
import com.example.paranoidandroid.Model.Treatment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    final DatabaseReference doseRef =
            FirebaseDatabase.getInstance().getReference("Dose");
    final DatabaseReference treatmentRef = FirebaseDatabase.getInstance().getReference(
            "Programme");

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void afterTextChanged(Editable s) {
            try {
                checkFields();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    } ;

    public static boolean validateJavaDate(String strDate) {

        /*
         * Set preferred date format,
         * For example MM-dd-yyyy, MM.dd.yyyy,dd.MM.yyyy etc.*/
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        simpleDateFormat.setLenient(false);
        /* Create Date object
         * parse the string into date
         */
        try {
            Date javaDate = simpleDateFormat.parse(strDate);

            System.out.println(strDate + " is valid date format");
        }
        /* Date format is invalid */ catch (ParseException e) {
            System.out.println(strDate + " is Invalid Date format");
            return false;
        }
        /* Return true if date format is valid */
        return true;
    }
    void checkFields() throws ParseException {
        Button b =  findViewById(R.id.update);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        String s1 = startDate.getText().toString();
        String s2 = endDate.getText().toString();
        String s3 = refMed.getText().toString();

        Date start = simpleDateFormat.parse(startDate.getText().toString());
        Date end = simpleDateFormat.parse(endDate.getText().toString());


        if(s1.equals("") || start.after(end) ||s2.equals("") || s3.equals("") || !validateJavaDate(s1) || !validateJavaDate(s2)){
            b.setEnabled(false);
        } else {
            b.setEnabled(true);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("med details created");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_details);
        Medicine medicine  = (Medicine) getIntent().getSerializableExtra("selectedMedicine");

        startDate =   findViewById(R.id.startDate);
        endDate =   findViewById(R.id.endDate);
        refMed = findViewById(R.id.refMed);

        startDate.addTextChangedListener(mTextWatcher);
        endDate.addTextChangedListener(mTextWatcher);
        refMed.addTextChangedListener(mTextWatcher);

        try {
            checkFields();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (medicine!=null) {
            String startDateString = new SimpleDateFormat("dd/MM/yyyy",
                    Locale.getDefault()).format(medicine.getDateDebCons());
            String endDateString = new SimpleDateFormat("dd/MM/yyyy",
                    Locale.getDefault()).format(medicine.getDateEnd());
            startDate.setText(startDateString);
            //startDate.addTextChangedListener(mTextWatcher);

            endDate.setText(endDateString);
            //endDate.addTextChangedListener(mTextWatcher);

            refMed.setText(medicine.getRefMed());
        }


    }

    public void AbandonModifications(View view) {
        Medicine medicine  = (Medicine) getIntent().getSerializableExtra("selectedMedicine");
        String startDateString = new SimpleDateFormat("dd/MM/yyyy",
                Locale.getDefault()).format(medicine.getDateDebCons());
        String endDateString = new SimpleDateFormat("dd/MM/yyyy",
                Locale.getDefault()).format(medicine.getDateEnd());
        startDate =   findViewById(R.id.startDate);
        startDate.setText(startDateString);

        endDate =   findViewById(R.id.endDate);
        endDate.setText(endDateString);

        refMed = findViewById(R.id.refMed);
        refMed.setText(medicine.getRefMed());

    }

    public void updateMedicine(View view) throws ParseException {
        final Medicine medicine  = (Medicine) getIntent().getSerializableExtra("selectedMedicine");
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

            final Date start = simpleDateFormat.parse(startDate.getText().toString());
            final Date end = simpleDateFormat.parse(endDate.getText().toString());
            medicine.setDateDebCons(start);
            medicine.setDateEnd(end);


            b.setText("Update");
            doseRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot doseSnapshot: dataSnapshot.getChildren()) {
                        System.out.println("number of existing doses "+Dose.getNumberOfDoses());
                        Dose dose = doseSnapshot.getValue(Dose.class);
                        if (medicine!=null && dose!=null && dose.getRefMed()!=null) {
                            System.out.println(medicine.getRefMed());
                            System.out.println(dose.getRefMed().getRefMed());
                            if (dose.getRefMed().getRefMed().equals(medicine.getRefMed())) {
                                System.out.println(doseRef.child(dose.getDoseId()).child("refMed").setValue(medicine));;
                            }
                        }

                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        medRef.child(String.valueOf(medicine.getRefMed())).setValue(medicine);
        linetRef.child(String.valueOf(treatment.getNum_p())).child("refMed").setValue(medicine);





    }

    public void checkDoses(View view) {
        final Medicine medicine  = (Medicine)
                getIntent().getSerializableExtra("selectedMedicine");
        Intent intent = new Intent(this, Doses.class);
        intent.putExtra("selectedMedicine",medicine);

        startActivity(intent);
        finish();
    }

    public void deleteMedicineAndDoses(View view) {

        final Medicine medicine  = (Medicine)
                getIntent().getSerializableExtra("selectedMedicine");
        Treatment treatment = (Treatment) getIntent().getSerializableExtra(
                "selectedTreatment") ;

        doseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot doseSnapshot: dataSnapshot.getChildren()) {
                    System.out.println("number of existing doses "+Dose.getNumberOfDoses());
                    Dose dose = doseSnapshot.getValue(Dose.class);
                    if (medicine!=null && dose!=null && dose.getRefMed()!=null) {
                        System.out.println(medicine.getRefMed());
                        System.out.println(dose.getRefMed().getRefMed());
                        if (dose.getRefMed().getRefMed().equals(medicine.getRefMed())) {
                            doseRef.child(doseSnapshot.getKey()).removeValue();
                            System.out.println("deleted successfully");
                        }
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        treatmentRef.child(String.valueOf(treatment.getNum_p())).removeValue();
        linetRef.child(String.valueOf(treatment.getNum_p())).removeValue();

        medRef.child(medicine.getRefMed()).removeValue();


        Intent intent =  new Intent(this,Treatments.class);
        intent.putExtra("selectedMedicine",medicine);
        startActivity(intent);
        finish();


    }
}
