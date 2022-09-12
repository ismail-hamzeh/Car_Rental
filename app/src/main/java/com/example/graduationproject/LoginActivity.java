package com.example.graduationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private TextView signup_text , forget_pass  ;
    private EditText email_login , password_login;
    private View login_btn;
    private FirebaseAuth auth;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signup_text = findViewById(R.id.signupText);
        forget_pass = findViewById(R.id.forget_pass);
        login_btn = findViewById(R.id.ProgressButton_login);
        email_login = findViewById(R.id.email_login);
        password_login = findViewById(R.id.password_login);
        auth = FirebaseAuth.getInstance();

        signup_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this , SignUpActivity.class);
                startActivity(intent);
            }
        });

        forget_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showForgetPassDialog();
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressButton progressButton = new ProgressButton(LoginActivity.this , view);

                progressButton.buttonActivated();
                Handler handler  = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        final String txt_username = email_login.getText().toString();
                        final String txt_password = password_login.getText().toString();

                        if (txt_username.isEmpty()) {
                            email_login.setError("Username Required");
                            progressButton.buttonWrong();
                        } if (txt_password.isEmpty()){
                            password_login.setError("Password Required");
                            progressButton.buttonWrong();
                        } else {
                            Handler handler1 = new Handler();
                            handler1.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressButton.buttonFinished();
                                    loginUser();
                                }
                            },500);


                        }
                    }
                },1000);
            }

        });

    }

    private void loginUser(){

        String email = email_login.getText().toString();
        String password = password_login.getText().toString();

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            final ProgressButton progressButton = new ProgressButton(LoginActivity.this , login_btn);

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    progressButton.buttonFinished();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();

                } else {
                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    progressButton.buttonWrong();
                }

            }
        });
    }

    public void showForgetPassDialog(){
        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.forget_password_dialog);
        dialog.show();

        final EditText email_forget = dialog.findViewById(R.id.email_forget);
        final Button forget_btn = dialog.findViewById(R.id.forget_btn);
        forget_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!email_forget.getText().toString().isEmpty()){

                    auth.sendPasswordResetEmail(email_forget.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                dialog.cancel();
                                showCheckEmailDialog();
                            } else {
                                Toast.makeText(LoginActivity.this,"Email is not exist",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else {
                    Toast.makeText(LoginActivity.this , "Please Enter your email",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void showCheckEmailDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.check_email_dialog);
        dialog.setCancelable(true);
        dialog.show();

        TextView cancel_dialog = dialog.findViewById(R.id.cancel_dialog_checkEmail);
        cancel_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }

}