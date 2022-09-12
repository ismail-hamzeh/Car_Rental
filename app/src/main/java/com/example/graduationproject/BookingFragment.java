package com.example.graduationproject;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.graduationproject.Adapters.Booking_Data_Adapter;
import com.example.graduationproject.Models.Booking_Data_Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;


public class BookingFragment extends Fragment {

    private LinearLayout noBooking_linear;
    private RecyclerView rec_booking;
    private Booking_Data_Adapter booking_data_adapter;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference;
    ArrayList<Booking_Data_Model> booking_Data_Models ;
    String userID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_booking, container, false);

        noBooking_linear = view.findViewById(R.id.noBooking_linear);
        rec_booking = view.findViewById(R.id.rec_booking);
        mAuth = FirebaseAuth.getInstance();

        rec_booking.setHasFixedSize(true);
        rec_booking.setLayoutManager(new LinearLayoutManager(getActivity()));

        booking_Data_Models = new ArrayList<>();

        getBookingData();

        return view;
    }

    public void getBookingData(){

        userID = mAuth.getCurrentUser().getUid();

        collectionReference = db.collection("Users").document(userID).collection("Bookings");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null){
                    return;
                }


                for (DocumentChange dc : value.getDocumentChanges()){
                    Booking_Data_Model bookingDataModel = new Booking_Data_Model();

                    DocumentSnapshot document = dc.getDocument();

                    bookingDataModel.setCar_image(document.getString("car image"));
                    bookingDataModel.setCar_name(document.getString("car name"));
                    bookingDataModel.setCar_seats(document.getString("car seats"));
                    bookingDataModel.setCar_doors(document.getString("car doors"));
                    bookingDataModel.setCar_bags(document.getString("car bags"));
                    bookingDataModel.setCar_gearShift(document.getString("car gearShift"));
                    bookingDataModel.setPickUp_location(document.getString("pickUp location"));
                    bookingDataModel.setReturn_location(document.getString("return location"));
                    bookingDataModel.setPickUp_date(document.getString("pickUp date"));
                    bookingDataModel.setReturn_date(document.getString("return date"));
                    bookingDataModel.setDriver(document.getString("driver"));
                    bookingDataModel.setCar_price_total(document.getString("total price"));

                    booking_Data_Models.add(bookingDataModel);

                }
                booking_data_adapter = new Booking_Data_Adapter(getContext(),booking_Data_Models);
                rec_booking.setAdapter(booking_data_adapter);
                booking_data_adapter.notifyDataSetChanged();

                if (booking_data_adapter.getItemCount() == 0){
                    noBooking_linear.setVisibility(View.VISIBLE);
                }

            }
        });
    }
}