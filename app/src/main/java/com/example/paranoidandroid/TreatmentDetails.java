package com.example.paranoidandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.paranoidandroid.Model.Temperature;
import com.example.paranoidandroid.Model.Treatment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class TreatmentDetails extends AppCompatActivity {

    private EditText sickness,startDate, endDate;

    final DatabaseReference treatmentRef = FirebaseDatabase.getInstance().getReference(
            "Programme");
    final DatabaseReference linetRef = FirebaseDatabase.getInstance().getReference(
            "Ligne_medicament");
    final DatabaseReference doseRef =
            FirebaseDatabase.getInstance().getReference("Dose");
    final DatabaseReference medRef =
            FirebaseDatabase.getInstance().getReference("Medicament");
    final DatabaseReference tempRef =
            FirebaseDatabase.getInstance().getReference("Temperature");
    String selectedMedicine;
    Medicine associatedMedicine;

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

    void checkFields() throws ParseException {
        Button b =  findViewById(R.id.update);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        String s1 = startDate.getText().toString();
        String s2 = endDate.getText().toString();
        String s3 = sickness.getText().toString();

        String currentDateString = new SimpleDateFormat("dd/MM/yyyy",
                Locale.getDefault()).format(new Date());

        Date currentDate;

        currentDate = simpleDateFormat.parse(currentDateString);

        Date start = simpleDateFormat.parse(startDate.getText().toString());
        Date end = simpleDateFormat.parse(endDate.getText().toString());

        if (b.getText().equals("Save")) {
            if(s1.equals("") || start.after(end)|| !currentDate.equals(start) ||currentDate.after(start) ||s2.equals("") || s3.equals("") || !validateJavaDate(s1) || !validateJavaDate(s2)){
                b.setEnabled(false);
            } else {
                b.setEnabled(true);
            }
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment_details);

        startDate =  findViewById(R.id.startDate);
        endDate =  findViewById(R.id.endDate);
        sickness = findViewById(R.id.sickness);

        startDate.addTextChangedListener(mTextWatcher);
        endDate.addTextChangedListener(mTextWatcher);
        sickness.addTextChangedListener(mTextWatcher);

        try {
            checkFields();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Treatment treatment = (Treatment)
                getIntent().getSerializableExtra("selectedTreatment");
        if (treatment!=null) {
            linetRef.child(treatment.getNum_p().toString()).child("refMed").child("refMed").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue()!=null) {
                        medRef.child((dataSnapshot.getValue()).toString()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.getValue() != null) {
                                    associatedMedicine = dataSnapshot.getValue(Medicine.class);
                                    System.out.println("associated medicine "+associatedMedicine);
                                    selectedMedicine = associatedMedicine.getRefMed();
                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        String startDateString = new SimpleDateFormat("dd/MM/yyyy",
                Locale.getDefault()).format(treatment.getStart_date());
        String endDateString = new SimpleDateFormat("dd/MM/yyyy",
                Locale.getDefault()).format(treatment.getEnd_date());

        startDate.setText(startDateString);

        endDate.setText(endDateString);

        sickness.setText(treatment.getSickness());
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
        linetRef.child(String.valueOf(treatment.getNum_p())).child("num_p").setValue(treatment);
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

        treatmentRef.child(String.valueOf(treatment.getNum_p())).removeValue();
        linetRef.child(String.valueOf(treatment.getNum_p())).removeValue();

        tempRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot tempSnapshot: dataSnapshot.getChildren()) {
                    Temperature temperature = tempSnapshot.getValue(Temperature.class) ;

                    if (temperature.getNum_p().getNum_p().equals(treatment.getNum_p())) {
                        tempRef.child(temperature.getNumTemp()).removeValue();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Intent intent =  new Intent(this,Treatments.class);
        startActivity(intent);
        finish();
    }

    public void goToMedicineDetails(View view) {
        final Treatment selectedTreatment = (Treatment)
                getIntent().getSerializableExtra("selectedTreatment");
        final Intent intent =  new Intent(this,MedicineDetails.class);
        intent.putExtra("selectedTreatment", selectedTreatment);
        intent.putExtra("selectedMedicine", associatedMedicine);
        /*System.out.println(associatedMedicine);
        System.out.println(selectedTreatment);*/

        startActivity(intent);
        finish();
    }

    public void goTemperature(View view) {
        final Treatment selectedTreatment = (Treatment)
                getIntent().getSerializableExtra("selectedTreatment");

        Intent intent = new Intent(getApplicationContext(),Temperatures.class);
        intent.putExtra("selectedTreatment",selectedTreatment);
        startActivity(intent);

        finish();

    }
}
