package com.example.paranoidandroid;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.paranoidandroid.Model.Dose;
import com.example.paranoidandroid.Model.Medicine;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

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
            try {
                checkFields();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    } ;

    void checkFields() throws ParseException {

        Button b1 = (Button) findViewById(R.id.newDose);
        Button b2 = (Button) findViewById(R.id.endTreatment);

        String s1 = description.getText().toString();
        String s2 = startDate.getText().toString();
        String s3 = time.getText().toString();
        String s4 = quantity.getText().toString();

        if(!s3.matches("^([0-1][0-9]|2[0-3])$")|| s4.equals("")||s1.equals("")|| s2.equals("") ||s3.equals("")  || !validateJavaDate(s2) ){
            b1.setEnabled(false);
            b2.setEnabled(false);
        } else {
            b1.setEnabled(true);
            b2.setEnabled(true);

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dose_form);
        description = (EditText) findViewById(R.id.description);
        startDate = (EditText) findViewById(R.id.startDate);
        time = (EditText) findViewById(R.id.time);
        quantity = (EditText) findViewById(R.id.quantity);

        description.addTextChangedListener(mTextWatcher);
        startDate.addTextChangedListener(mTextWatcher);
        time.addTextChangedListener(mTextWatcher);
        quantity.addTextChangedListener(mTextWatcher);

        try {
            checkFields();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String date_n = new SimpleDateFormat("dd/MM/yyyy",
                Locale.getDefault()).format(new Date());
        TextView date  = (TextView) findViewById(R.id.startDate);
        date.setText(date_n);

        TextView time = (TextView) findViewById(R.id.time);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH", Locale.FRANCE);
        LocalTime currentTime = LocalTime.now();
        String timeString = formatter.format(currentTime);
        time.setText(timeString);


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void goToTreatments(View v) throws ParseException {

        Medicine medicine = (Medicine)
                getIntent().getSerializableExtra("myMedicine");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Dose dose = new Dose();
        dose.setDate(simpleDateFormat.parse(startDate.getText().toString()));
        dose.setDescription(description.getText().toString());
        //dose.setTime(Integer.valueOf(time.getText().toString()));

        dose.setQte(Integer.valueOf(quantity.getText().toString()));
        dose.setRefMed(medicine);

        Integer doseTime = Integer.valueOf(time.getText().toString());
        dose.setTime(doseTime);

        doseRef.child(String.valueOf(dose.getDoseId())).setValue(dose);






        Intent intent = new Intent(this, Treatments.class);
        startActivity(intent);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addDose(View view) throws ParseException {

        Medicine medicine = (Medicine)
                getIntent().getSerializableExtra("myMedicine");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Dose dose = new Dose();
        dose.setDate(simpleDateFormat.parse(startDate.getText().toString()));
        dose.setDescription(description.getText().toString());
        Integer doseTime = Integer.valueOf(time.getText().toString());
        dose.setTime(doseTime);
        dose.setQte(Integer.valueOf(quantity.getText().toString()));
        dose.setRefMed(medicine);

        doseRef.child(String.valueOf(dose.getDoseId())).setValue(dose);

        Intent intent = new Intent(this, DoseForm.class);

        intent.putExtra("myMedicine", medicine);
        startActivity(intent);
    }
}
