package com.example.paranoidandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MedsForm extends AppCompatActivity {
    private EditText Medicine_Ref,start,end;



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
        Button b = (Button) findViewById(R.id.saveMed);

        String s1 = Medicine_Ref.getText().toString();
        String s2 = start.getText().toString();
        String s3 = end.getText().toString();

        if(s1.equals("")|| s2.equals("") ||s3.equals("")  || !validateJavaDate(s2) || !validateJavaDate(s3)){
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

        checkFields();

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




}
