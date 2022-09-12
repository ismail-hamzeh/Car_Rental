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


public class SuvFragment extends Fragment {

    RecyclerView RecView_suv;
    private CarItemAdapter carItemAdapter;
    private DatabaseReference reference;

    private ArrayList<CarItem> carItems ;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_suv, container, false);




        RecView_suv = view.findViewById(R.id.RecView_suv);
        RecView_suv.setHasFixedSize(true);
        RecView_suv.setLayoutManager(new LinearLayoutManager(getActivity()));

        reference = FirebaseDatabase.getInstance().getReference();

        // Arraylist
        carItems = new ArrayList<>();

        // Clear Arraylist:
        clearAll();

        // Get Data Method
        getDataFromFirebase();




//        FirebaseRecyclerOptions<CarItem> options =
//                new FirebaseRecyclerOptions.Builder<CarItem>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("suv_car"), CarItem.class)
//                        .build();




        return view;
    }

    private void getDataFromFirebase() {

        Query query = reference.child("suv");
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
                RecView_suv.setAdapter(carItemAdapter);
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