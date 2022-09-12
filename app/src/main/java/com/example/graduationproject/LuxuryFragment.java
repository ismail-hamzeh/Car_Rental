package com.example.graduationproject;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.graduationproject.Adapters.CarItemAdapter;
import com.example.graduationproject.Models.CarItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class LuxuryFragment extends Fragment {

    private RecyclerView RecView_luxury;
    private CarItemAdapter carItemAdapter;
    private DatabaseReference reference;

    private ArrayList<CarItem> carItems ;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_luxury, container, false);

        RecView_luxury = view.findViewById(R.id.RecView_luxury);
        RecView_luxury.setHasFixedSize(true);
        RecView_luxury.setLayoutManager(new LinearLayoutManager(getActivity()));

        reference = FirebaseDatabase.getInstance().getReference();

        // Arraylist
        carItems = new ArrayList<>();

        // Clear Arraylist:
        clearAll();

        // Get Data Method
        getDataFromFirebase();





        return view;
    }

    private void getDataFromFirebase() {

        Query query = reference.child("luxury");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clearAll();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    CarItem carItem = new CarItem();

                    carItem.setCar_img(snapshot.child("car_img").getValue().toString());
                    carItem.setCar_name(snapshot.child("car_name").getValue().toString());
                    carItem.setCar_price(snapshot.child("car_price").getValue().toString());
                    carItem.setDoors_number(snapshot.child("doors_number").getValue().toString());
                    carItem.setGear_shift_txt(snapshot.child("gear_shift_txt").getValue().toString());
                    carItem.setSeats_number(snapshot.child("seats_number").getValue().toString());
                    carItem.setSuitcase_number(snapshot.child("suitcase_number").getValue().toString());

                    carItems.add(carItem);
                }
                carItemAdapter = new CarItemAdapter(carItems,context);
                RecView_luxury.setAdapter(carItemAdapter);
                carItemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void clearAll(){
        if (carItems != null) {
            carItems.clear();

            if (carItemAdapter != null){
                carItemAdapter.notifyDataSetChanged();
            }
        }
        carItems = new ArrayList<>();
    }

}