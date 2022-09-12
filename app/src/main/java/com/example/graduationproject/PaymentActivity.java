package com.example.graduationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import io.github.muddz.styleabletoast.StyleableToast;

public class PaymentActivity extends AppCompatActivity {

    private ImageView back_payment;
    private TextView price_payment;
    private EditText card_number, card_holder_name, expiry_date, cvv_code;
    private Button confirm;

    private Context context;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        card_number = findViewById(R.id.card_number);
        card_holder_name = findViewById(R.id.card_holder_name);
        expiry_date = findViewById(R.id.expiry_date);
        cvv_code = findViewById(R.id.cvv_code);
        confirm = findViewById(R.id.confirm);
        back_payment = findViewById(R.id.back_payment);
        price_payment = findViewById(R.id.price_payment);
        mAuth = FirebaseAuth.getInstance();

        price_payment.setText(getIntent().getExtras().getString("total_price"));


        card_number.addTextChangedListener(new FourDigitCardFormatWatcher());

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (card_number.getText().toString().isEmpty()){
                    card_number.setError("Required");
                } else if (card_holder_name.getText().toString().isEmpty()){
                    card_holder_name.setError("Required");
                } else if (expiry_date.getText().toString().isEmpty()){
                    expiry_date.setError("Required");
                } else if (cvv_code.getText().toString().isEmpty()){
                    cvv_code.setError("Required");
                } else {
                    Dialog dialog = new Dialog(PaymentActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.progress_bar_dialog);

                    dialog.show();

                    Timer timer = new Timer();
                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {

                            bookingDataToDatabase();
                            startActivity(new Intent(PaymentActivity.this, ConfirmationActivity.class));

                        }
                    };
                    timer.schedule(timerTask, 3000);


                }
            }
        });

        back_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void bookingDataToDatabase(){

        userID = mAuth.getCurrentUser().getUid();

        Map<String,Object> bookingDate = new HashMap<>();
        bookingDate.put("car image", getIntent().getExtras().getString("car image"));
        bookingDate.put("car name", getIntent().getExtras().getString("car_name_DB"));
        bookingDate.put("car seats", getIntent().getExtras().getString("seats_DB"));
        bookingDate.put("car doors", getIntent().getExtras().getString("doors_DB"));
        bookingDate.put("car bags", getIntent().getExtras().getString("bags_DB"));
        bookingDate.put("car gear shift", getIntent().getExtras().getString("gearShift_DB"));
        bookingDate.put("pickUp location", getIntent().getExtras().getString("pickup_location_DB"));
        bookingDate.put("return location", getIntent().getExtras().getString("return_location_DB"));
        bookingDate.put("pickUp date", getIntent().getExtras().getString("pickup_date_DB"));
        bookingDate.put("return date", getIntent().getExtras().getString("return_date_DB"));
        bookingDate.put("driver", getIntent().getExtras().getString("driver_DB"));
        bookingDate.put("total price", getIntent().getExtras().getString("total_price"));

         db.collection("Users").document(userID).collection("Bookings").add(bookingDate).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
             @Override
             public void onSuccess(DocumentReference documentReference) {
             }
         });


    }

}