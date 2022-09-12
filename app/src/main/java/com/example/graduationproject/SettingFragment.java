package com.example.graduationproject;

import static android.content.ContentValues.TAG;
import static android.view.View.LAYOUT_DIRECTION_RTL;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.subhrajyoti.passwordview.PasswordView;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import io.github.muddz.styleabletoast.StyleableToast;

public class SettingFragment extends Fragment {

    private TextView language , changePassword_txt;
    private EditText feedback_txt;
    private TextView feedback_btn ,email_setting , fullName_setting , edit_setting;
    private RatingBar ratUs;
    private SwitchMaterial switch_mode;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences sharedPreferences = null;
    String userID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        loadLocale();
        View view = inflater.inflate(R.layout.fragment_setting, container, false);


        email_setting = view.findViewById(R.id.email_setting);
        fullName_setting = view.findViewById(R.id.fullname_setting);
        switch_mode = view.findViewById(R.id.switch_mode);
        language = view.findViewById(R.id.language);
        ratUs = view.findViewById(R.id.rateUs);
        feedback_txt = view.findViewById(R.id.feedback);
        feedback_btn = view.findViewById(R.id.feedback_btn);
        edit_setting = view.findViewById(R.id.edit_setting);
        changePassword_txt = view.findViewById(R.id.changePassword_txt);
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();

        showUserData();

        sharedPreferences = getActivity().getSharedPreferences("night",0);
        Boolean booleanValue = sharedPreferences.getBoolean("night_mode",true);

        if (!booleanValue){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            switch_mode.setChecked(false);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            switch_mode.setChecked(true);
        }

        switch_mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){

                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    switch_mode.setChecked(true);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("night_mode",true).apply();

                }else {

                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    switch_mode.setChecked(false);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("night_mode",false).apply();

                }
            }
        });

        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangeLanguageDialog();
            }
        });

        changePassword_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangePasswordDialog();
            }
        });

        ratUs.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {

                db.collection("Users").document(userID).update("app rate",String.valueOf(v));

            }
        });

        feedback_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db.collection("Users").document(userID).update("feedback", feedback_txt.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        StyleableToast.makeText(getContext(),"Sent successfully", R.style.Toast_style).show();
                    }
                });
            }
        });

        edit_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        new ProfileFragment()).commit();
            }
        });



        return view;
    }


    private void showUserData(){

        userID = mAuth.getCurrentUser().getUid();

        db.collection("Users").document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()){

                    DocumentSnapshot documentSnapshot = task.getResult();

                    email_setting.setText(documentSnapshot.getString("email"));
                    fullName_setting.setText(documentSnapshot.getString("full name"));
                    String rateDB = String.valueOf(Float.parseFloat(documentSnapshot.getString("app rate")));
                    if (rateDB != null) {
                        ratUs.setRating(Float.parseFloat(rateDB));
                    }
                }

            }
        });

    }

    private void showChangeLanguageDialog() {

        final String[] listItem= {"English" , "عربي"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
        mBuilder.setTitle("choose language");
        mBuilder.setSingleChoiceItems(listItem, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (i==0){

                    setLocale(getActivity(),"en");

                    getActivity().finish();
                    startActivity(getActivity().getIntent());

                }else
                if (i==1){

                    setLocale(getActivity(),"ar");

                    getActivity().finish();
                    startActivity(getActivity().getIntent());
                }

                dialogInterface.dismiss();
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();

    }

    private void showChangePasswordDialog(){
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.change_password_dialog);

        dialog.show();
        final PasswordView oldPassword_change = dialog.findViewById(R.id.oldPassword_change);
        final PasswordView newPassword_change = dialog.findViewById(R.id.newPassword_change);
        final PasswordView RePassword_change = dialog.findViewById(R.id.RePassword_change);
        final Button changePassword_btn = dialog.findViewById(R.id.changePassword_btn);
        final ImageView cancel_changePassword = dialog.findViewById(R.id.cancel_changePassword);

        changePassword_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String oldPassword = oldPassword_change.getText().toString();
                String newPassword = newPassword_change.getText().toString();
                String RePassword = RePassword_change.getText().toString();
                String userID = mAuth.getCurrentUser().getUid();

                if (oldPassword.isEmpty()){

                    oldPassword_change.setError("Required");

                }else if (newPassword.isEmpty()){

                    newPassword_change.setError("Required");

                }else if (!newPassword.equals(RePassword)){

                    RePassword_change.setError("Password doesn't match");

                } else if (RePassword.isEmpty()){

                    RePassword_change.setError("Required");

                } else if (newPassword.length() < 8){

                    newPassword_change.setError("At least 8 characters");

                } else {

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    String email = user.getEmail();

                    AuthCredential credential = EmailAuthProvider.getCredential(email,oldPassword);

                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                user.updatePassword(RePassword);
                                db.collection("Users").document(userID).update("password",RePassword);
                                dialog.cancel();
                                StyleableToast.makeText(getActivity(), "Updated successfully", R.style.Toast_style).show();
                            }else {
                                StyleableToast.makeText(getActivity(), "Old password is error", R.style.Toast_style).show();
                            }

                        }
                    });
                }



            }
        });


        cancel_changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

    }

    private void setLocale(Activity activity , String lang) {

         Locale locale = new Locale(lang);
         Locale.setDefault(locale);
         Resources resources = activity.getResources();
         Configuration config = resources.getConfiguration();
         config.setLocale(locale);
         resources.updateConfiguration(config, resources.getDisplayMetrics());

        SharedPreferences.Editor editor = getActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE).edit();
        editor.putString("My_Lang",lang);
        editor.apply();

    }

    private void loadLocale(){
        SharedPreferences preferences = getActivity().getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = preferences.getString("My_Lang","");
        setLocale(getActivity(),language);
    }


}