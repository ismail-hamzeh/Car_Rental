package com.example.graduationproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.graduationproject.Models.Booking_Data_Model;
import com.example.graduationproject.R;

import java.util.ArrayList;

public class Booking_Data_Adapter extends RecyclerView.Adapter<Booking_Data_Adapter.viewHolder>{

    private Context context;
    ArrayList<Booking_Data_Model> booking_data_models = new ArrayList<>();

    public Booking_Data_Adapter(Context context, ArrayList<Booking_Data_Model> booking_data_models) {
        this.context = context;
        this.booking_data_models = booking_data_models;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookings_items,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        String uri = booking_data_models.get(holder.getAdapterPosition()).getCar_image();
        Glide.with(holder.itemView.getContext()).load(uri).into(holder.car_img);
        holder.car_name.setText(booking_data_models.get(holder.getAdapterPosition()).getCar_name());
        holder.car_seats.setText(booking_data_models.get(holder.getAdapterPosition()).getCar_seats());
        holder.car_doors.setText(booking_data_models.get(holder.getAdapterPosition()).getCar_doors());
        holder.car_bags.setText(booking_data_models.get(holder.getAdapterPosition()).getCar_bags());
        holder.car_gearShift.setText(booking_data_models.get(holder.getAdapterPosition()).getCar_gearShift());
        holder.pickUp_location.setText(booking_data_models.get(holder.getAdapterPosition()).getPickUp_location());
        holder.return_location.setText(booking_data_models.get(holder.getAdapterPosition()).getReturn_location());
        holder.pickUp_date.setText(booking_data_models.get(holder.getAdapterPosition()).getPickUp_date());
        holder.return_date.setText(booking_data_models.get(holder.getAdapterPosition()).getReturn_date());
        holder.driver.setText(booking_data_models.get(holder.getAdapterPosition()).getDriver());
        holder.car_price_total.setText(booking_data_models.get(holder.getAdapterPosition()).getCar_price_total());

    }

    @Override
    public int getItemCount() {
        return booking_data_models.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        private ImageView car_img;
        private TextView car_name, car_seats, car_doors, car_bags, car_gearShift, pickUp_location
                , return_location, pickUp_date, return_date, driver, car_price_total;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            car_img = itemView.findViewById(R.id.car_img_booking);
            car_name = itemView.findViewById(R.id.car_name_booking);
            car_seats = itemView.findViewById(R.id.seats_number_booking);
            car_doors = itemView.findViewById(R.id.doors_number_booking);
            car_bags = itemView.findViewById(R.id.suitcase_number_booking);
            car_gearShift = itemView.findViewById(R.id.gear_shift_txt_booking);
            pickUp_location = itemView.findViewById(R.id.pickup_location_booking);
            return_location = itemView.findViewById(R.id.return_location_booking);
            pickUp_date = itemView.findViewById(R.id.pickup_date_booking);
            return_date = itemView.findViewById(R.id.return_date_booking);
            driver = itemView.findViewById(R.id.driver_booking);
            car_price_total = itemView.findViewById(R.id.total_booking);
        }
    }
}
