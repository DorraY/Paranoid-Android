package com.example.paranoidandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.paranoidandroid.Model.Treatment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TreatmentForm extends AppCompatActivity {
    private EditText start, end, sickness;
    final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference(
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

    void checkFields() throws ParseException {
        Button b = (Button) findViewById(R.id.save);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");


        Date startDate = simpleDateFormat.parse(start.getText().toString());
        Date endDate = simpleDateFormat.parse(end.getText().toString());

        String s1 = start.getText().toString();
        String s2 = end.getText().toString();
        String s3 = sickness.getText().toString();


        if(s1.equals("") || startDate.after(endDate) ||s2.equals("") || s3.equals("") || !validateJavaDate(s1) || !validateJavaDate(s2)){
            b.setEnabled(false);
        } else {
            b.setEnabled(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_treatment_form);
        start = (EditText) findViewById(R.id.startDate);
        end = (EditText) findViewById(R.id.endDate);
        sickness = (EditText) findViewById(R.id.Sickness);

        start.addTextChangedListener(mTextWatcher);
        end.addTextChangedListener(mTextWatcher);
        sickness.addTextChangedListener(mTextWatcher);

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

    public void goToMedecines(View v) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Treatment treatment = new Treatment();
        treatment.setSickness(sickness.getText().toString());

        Date startDate = simpleDateFormat.parse(start.getText().toString());

        Date endDate = simpleDateFormat.parse(end.getText().toString());

        treatment.setStart_date(startDate);
        treatment.setEnd_date(endDate);

        myRef.child(String.valueOf(treatment.getNum_p())).setValue(treatment);

        System.out.println(treatment);

        Intent intent = new Intent(this, MedsForm.class);
        intent.putExtra("myTreatment", treatment);

        startActivity(intent);
    }

}
