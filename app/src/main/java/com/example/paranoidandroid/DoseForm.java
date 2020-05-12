package com.example.paranoidandroid;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DoseForm extends AppCompatActivity {

    private EditText description,startDate,time,quantity;

    final DatabaseReference doseRef = FirebaseDatabase.getInstance().getReference(
            "Dose");

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void afterTextChanged(Editable s) {
            checkFields();
        }
    } ;

    void checkFields()  {

        Button b1 = (Button) findViewById(R.id.newDose);
        Button b2 = (Button) findViewById(R.id.newMedicine);
        Button b3 = (Button) findViewById(R.id.endTreatment);

        String s1 = description.getText().toString();
        String s2 = startDate.getText().toString();
        String s3 = time.getText().toString();
        String s4 = quantity.getText().toString();

        if(s4.equals("")||s1.equals("")|| s2.equals("") ||s3.equals("")  || !validateJavaDate(s2) ){
            b1.setEnabled(false);
            b2.setEnabled(false);
            b3.setEnabled(false);
        } else {
            b1.setEnabled(true);
            b2.setEnabled(true);
            b3.setEnabled(true);

        }
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dose_form);
        description = (EditText) findViewById(R.id.description);
        startDate = (EditText) findViewById(R.id.startDate);
        time = (EditText) findViewById(R.id.time);
        quantity = (EditText) findViewById(R.id.quantity);


    }

    public void goToTreatments(View v) throws ParseException {

        Medicine medicine = (Medicine)
                getIntent().getSerializableExtra("myMedicine");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Dose dose = new Dose();
        dose.setDate(simpleDateFormat.parse(startDate.getText().toString()));
        dose.setDescriiption(description.getText().toString());
        dose.setHour(Integer.valueOf(time.getText().toString()));
        dose.setQte(Integer.valueOf(quantity.getText().toString()));
        dose.setRefMed(medicine);

        doseRef.child(String.valueOf(dose.getDoseId())).setValue(dose);







        Intent intent = new Intent(this, Treatments.class);
        startActivity(intent);
    }

    public void addMedicine(View view) {
        Intent intent = new Intent(this, MedsForm.class);
        startActivity(intent);
    }


    public void addDose(View view) {
        Intent intent = new Intent(this, DoseForm.class);
        startActivity(intent);
    }
}
