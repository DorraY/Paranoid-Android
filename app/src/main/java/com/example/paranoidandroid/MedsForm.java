package com.example.paranoidandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.example.paranoidandroid.Model.Medicine;
import com.example.paranoidandroid.Model.MedicineLine;
import com.example.paranoidandroid.Model.Treatment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MedsForm extends AppCompatActivity {
    private EditText Medicine_Ref,start,end;
    final DatabaseReference medRef = FirebaseDatabase.getInstance().getReference(
            "Medicament");
    final DatabaseReference linetRef = FirebaseDatabase.getInstance().getReference(
            "Ligne_medicament");

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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Date startDate = simpleDateFormat.parse(start.getText().toString());
        Date endDate = simpleDateFormat.parse(end.getText().toString());
        Button b = (Button) findViewById(R.id.saveMed);
        String s1 = Medicine_Ref.getText().toString();
        String s2 = start.getText().toString();
        String s3 = end.getText().toString();

        if( startDate.after(endDate)||s1.equals("")|| s2.equals("") ||s3.equals("")  || !validateJavaDate(s2) || !validateJavaDate(s3)){
            b.setEnabled(false);
        } else {
            b.setEnabled(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meds_form);

        Medicine_Ref = (EditText) findViewById(R.id.Medicine_Ref);
        start = (EditText) findViewById(R.id.startDate);
        end = (EditText) findViewById(R.id.endDate);


        Medicine_Ref.addTextChangedListener(mTextWatcher);
        start.addTextChangedListener(mTextWatcher);
        end.addTextChangedListener(mTextWatcher);

        try {
            checkFields();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String date_n = new SimpleDateFormat("dd/MM/yyyy",
                Locale.getDefault()).format(new Date());
        TextView date  = (TextView) findViewById(R.id.startDate);
        date.setText(date_n);
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


    public void addMedicine(View view) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Medicine medicine = new Medicine();

        Date startDate = simpleDateFormat.parse(start.getText().toString());
        Date endDate = simpleDateFormat.parse(end.getText().toString());

        medicine.setDateDebCons(simpleDateFormat.parse(start.getText().toString()));

        medicine.setDateEnd(simpleDateFormat.parse(end.getText().toString()));

        medicine.setRefMed(Medicine_Ref.getText().toString());

        medRef.child(String.valueOf(medicine.getRefMed())).setValue(medicine);

        Treatment treatment ;
        treatment = (Treatment)  getIntent().getSerializableExtra("myTreatment") ;
        treatment.setNumberOfTreatments(treatment.getNumberOfTreatments()-1);
        treatment.setNum_p(treatment.getNum_p());

        System.out.println("hello from the meds form " + treatment.getNum_p() );

        MedicineLine medicineLine = new MedicineLine();
        medicineLine.setNum_p(treatment);
        medicineLine.setRefMed(medicine);

        linetRef.child(String.valueOf(medicineLine.getNum_p().getNum_p())).setValue(medicineLine);

        Intent intent = new Intent(this, DoseForm.class);

        intent.putExtra("myMedicine", medicine);

        startActivity(intent);
        finish();





    }
}
