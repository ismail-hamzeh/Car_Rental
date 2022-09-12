package com.example.graduationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.subhrajyoti.passwordview.PasswordView;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;

public class SignUpActivity extends AppCompatActivity {

    private EditText fullName_signup , username_signup , email_signup , phone_signup , date_signup;
    private PasswordView password_signup , rePassword_signup;
    private View signUp_btn;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fullName_signup = findViewById(R.id.fullname_signup);
        username_signup = findViewById(R.id.username_signup);
        email_signup = findViewById(R.id.email_signup);
        phone_signup = findViewById(R.id.phone_signup);
        date_signup = findViewById(R.id.date_signup);
        password_signup = findViewById(R.id.password_signup);
        rePassword_signup = findViewById(R.id.RePassword_signup);
        signUp_btn = findViewById(R.id.ProgressButton_signup);
        mAuth = FirebaseAuth.getInstance();

        final ProgressButton2 progressButton2 = new ProgressButton2(SignUpActivity.this , signUp_btn);


        signUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressButton2.buttonActivated_signup();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                progressButton2.buttonFinished_signup();
                                registerUser();
                            }
                        },1000);
                    }
        });


    }

    public void registerUser(){

        String txt_fullName = fullName_signup.getText().toString().trim();
        String txt_username = username_signup.getText().toString().trim();
        String txt_email = email_signup.getText().toString().trim();
        String txt_phone = phone_signup.getText().toString().trim();
        String txt_date = date_signup.getText().toString().trim();
        String txt_password = password_signup.getText().toString().trim();
        String txt_RePassword = rePassword_signup.getText().toString().trim();

        final ProgressButton2 progressButton2 = new ProgressButton2(SignUpActivity.this , signUp_btn);


        if (txt_fullName.isEmpty()) {
            fullName_signup.setError("Full Name Required");
            progressButton2.buttonWrong_signup();
        }else if (txt_username.isEmpty()){
            username_signup.setError("Username Required");
            progressButton2.buttonWrong_signup();
        }else if (txt_username.contains(" ")){
            username_signup.setError("No Spaces Allowed");
            progressButton2.buttonWrong_signup();
        }else if (txt_email.isEmpty()) {
            email_signup.setError("Email Required");
            progressButton2.buttonWrong_signup();
        }else if (txt_phone.isEmpty()) {
            phone_signup.setError("Phone Number Required");
            progressButton2.buttonWrong_signup();
        }else if (txt_date.isEmpty()) {
            date_signup.setError("Date Required");
            progressButton2.buttonWrong_signup();
        }else if (txt_password.isEmpty()){
            password_signup.setError("Password Required");
            progressButton2.buttonWrong_signup();
        }else if (txt_password.length() < 8){
            password_signup.setError("At least 8 characters");
            progressButton2.buttonWrong_signup();
        }else if (txt_RePassword.isEmpty()) {
            rePassword_signup.setError("RePassword Required");
            progressButton2.buttonWrong_signup();
        }else if (!txt_password.equals(txt_RePassword)){
            rePassword_signup.setError("Password doesn't match");
            progressButton2.buttonWrong_signup();
        } else {

            mAuth.createUserWithEmailAndPassword(txt_email,txt_password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {

                    userID = mAuth.getCurrentUser().getUid();

                    Map<String, Object> user = new HashMap<>();
                    user.put("full name", txt_fullName);
                    user.put("username", txt_username);
                    user.put("email", txt_email);
                    user.put("phone", txt_phone);
                    user.put("DOB", txt_date);
                    user.put("password", txt_password);
                    user.put("app rate", "0.0");

                    db.collection("Users").document(userID).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(SignUpActivity.this,"Error"+task.getException(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            });


        }


    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {

        }
    }


}