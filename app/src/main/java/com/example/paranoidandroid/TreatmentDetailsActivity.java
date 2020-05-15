package com.example.paranoidandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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


public class TreatmentDetailsActivity extends AppCompatActivity {

    private EditText sickness,startDate, endDate;
    final DatabaseReference treatmentRef = FirebaseDatabase.getInstance().getReference(
            "Programme");
    final DatabaseReference linetRef = FirebaseDatabase.getInstance().getReference(
            "Ligne_medicament");
    final DatabaseReference doseRef =
            FirebaseDatabase.getInstance().getReference("Dose/");

/*    private TextWatcher mTextWatcher = new TextWatcher() {
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
    } ;*/

/*    void checkFields() throws ParseException {
        Button b =    findViewById(R.id.save);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Date startDate = simpleDateFormat.parse(startDate.getText().toString());
        Date endDate = simpleDateFormat.parse(endDate.getText().toString());


        String s1 = startDate.getText().toString();
        String s2 = endDate.getText().toString();
        String s3 = sickness.getText().toString();


        if(s1.equals("") || startDate.after(endDate) ||s2.equals("") || s3.equals("") || !validateJavaDate(s1) || !validateJavaDate(s2)){
            b.setEnabled(false);
        } else {
            b.setEnabled(true);
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println(new Date().getHours());
        System.out.println(new Date().getMinutes());



        setContentView(R.layout.activity_treatment_details);
        Treatment treatment = (Treatment)
                getIntent().getSerializableExtra("selectedTreatment");

        String startDateString = new SimpleDateFormat("dd/MM/yyyy",
                Locale.getDefault()).format(treatment.getStart_date());
        String endDateString = new SimpleDateFormat("dd/MM/yyyy",
                Locale.getDefault()).format(treatment.getEnd_date());

        (linetRef.child(String.valueOf(treatment.getNum_p())).child("refMed").child("refMed")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println(" im here "  +dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        startDate =   findViewById(R.id.startDate);
        startDate.setText(startDateString);
        //startDate.addTextChangedListener(mTextWatcher);

        endDate =   findViewById(R.id.endDate);
        endDate.setText(endDateString);
        //endDate.addTextChangedListener(mTextWatcher);

        sickness =  findViewById(R.id.sickness);
        sickness.setText(treatment.getSickness());
        //sickness.addTextChangedListener(mTextWatcher);

    }

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



    public void updateTreatment(View view) throws ParseException {
        Treatment treatment = (Treatment)
                getIntent().getSerializableExtra("selectedTreatment");
        endDate =   findViewById(R.id.endDate);
        sickness =  findViewById(R.id.sickness);
        startDate =   findViewById(R.id.startDate);

        Button b =    findViewById(R.id.update);
        if (b.getText().equals("Update")) {
            sickness.setEnabled(true);
            startDate.setEnabled(true);
            endDate.setEnabled(true);
            b.setText("Save");
        }
        else {
            sickness.setEnabled(false);
            startDate.setEnabled(false);
            endDate.setEnabled(false);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

            treatment.setSickness(sickness.getText().toString());

            Date start = simpleDateFormat.parse(startDate.getText().toString());
            Date end = simpleDateFormat.parse(endDate.getText().toString());
            treatment.setStart_date(start);
            treatment.setEnd_date(end);
            treatmentRef.child(String.valueOf(treatment.getNum_p())).setValue(treatment);
            b.setText("Update");
        }

    }
    public void AbandonModifications(View view) {
        Treatment treatment = (Treatment)
                getIntent().getSerializableExtra("selectedTreatment");
        String startDateString = new SimpleDateFormat("dd/MM/yyyy",
                Locale.getDefault()).format(treatment.getStart_date());
        String endDateString = new SimpleDateFormat("dd/MM/yyyy",
                Locale.getDefault()).format(treatment.getEnd_date());

        startDate =   findViewById(R.id.startDate);
        startDate.setText(startDateString);
        //startDate.addTextChangedListener(mTextWatcher);

        endDate =   findViewById(R.id.endDate);
        endDate.setText(endDateString);
        //endDate.addTextChangedListener(mTextWatcher);

        sickness =  findViewById(R.id.sickness);
        sickness.setText(treatment.getSickness());

    }

    public void deleteTreatment(View view) {
        final Treatment treatment = (Treatment)
                getIntent().getSerializableExtra("selectedTreatment");

        linetRef.child(String.valueOf(treatment.getNum_p())).child("refMed").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String medicine = dataSnapshot.getValue(String.class);
                System.out.println(" selected medicine "+ dataSnapshot.getValue(String.class));
                doseRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot doseSnapshot: dataSnapshot.getChildren()) {
                            Dose dose = doseSnapshot.getValue(Dose.class);
                            System.out.println(dose.getRefMed().getRefMed().equals(medicine));
                            System.out.println(dose.getRefMed().getRefMed());

                            if (dose.getRefMed().getRefMed().equals(medicine)) {
                                doseRef.child(String.valueOf(dose.getDoseId())).removeValue();
                                System.out.println("deleted successfuly");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }) ;

        treatmentRef.child(String.valueOf(treatment.getNum_p())).removeValue();
        linetRef.child(String.valueOf(treatment.getNum_p())).removeValue();

        Intent intent =  new Intent(this,Treatments.class);
        startActivity(intent);
    }
}
