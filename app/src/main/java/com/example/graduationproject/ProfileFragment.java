package com.example.graduationproject;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

import io.github.muddz.styleabletoast.StyleableToast;

public class ProfileFragment extends Fragment {

    private EditText fullName_profile, username_profile , email_profile, phone_profile, date_profile;
    private ImageView edit_img;
    private TextView edit_txt;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userID;
    boolean edit = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        fullName_profile = view.findViewById(R.id.fullname_profile);
        username_profile = view.findViewById(R.id.username_profile);
        email_profile = view.findViewById(R.id.email_profile);
        phone_profile = view.findViewById(R.id.phone_profile);
        date_profile = view.findViewById(R.id.date_profile);
        edit_txt = view.findViewById(R.id.edit_txt);
        edit_img = view.findViewById(R.id.edit_img);
        mAuth = FirebaseAuth.getInstance();

        getUserData();

        edit_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit == false) {

                    fullName_profile.setEnabled(true);
                    username_profile.setEnabled(true);
                    email_profile.setEnabled(true);
                    phone_profile.setEnabled(true);
                    date_profile.setEnabled(true);
                    edit_img.setImageResource(R.drawable.edit_green);
                    edit_txt.setText("Save");

                    edit = true;

                } else {

                    fullName_profile.setEnabled(false);
                    username_profile.setEnabled(false);
                    email_profile.setEnabled(false);
                    phone_profile.setEnabled(false);
                    date_profile.setEnabled(false);
                    edit_txt.setText("Edit");
                    edit_img.setImageResource(R.drawable.edit);

                    if (username_profile.getText().toString().contains(" ")){
                        username_profile.setError("No Spaces Allowed");
                    }else {
                        editData();
                    }

                    edit = false;

                }
            }
        });


        return view;
    }


        public void getUserData(){

        userID = mAuth.getCurrentUser().getUid();

        db.collection("Users").document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();

                    if (documentSnapshot.exists()) {
                        fullName_profile.setText(documentSnapshot.getString("full name"));
                        username_profile.setText(documentSnapshot.getString("username"));
                        email_profile.setText(documentSnapshot.getString("email"));
                        date_profile.setText(documentSnapshot.getString("DOB"));
                        phone_profile.setText(documentSnapshot.getString("phone"));
                    }else {
                        Log.d(TAG, "Error: No document attached");
                    }
                }

            }
        });

        }

        public void editData(){

            userID = mAuth.getCurrentUser().getUid();

            Map<String,Object> user = new HashMap<>();
            user.put("full name", fullName_profile.getText().toString());
            user.put("username", username_profile.getText().toString());
            user.put("phone", phone_profile.getText().toString());
            user.put("DOB", date_profile.getText().toString());

            db.collection("Users").document(userID).update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    StyleableToast.makeText(getContext(),"Updated Successfully",R.style.Toast_style).show();
                }
            });


    }



}