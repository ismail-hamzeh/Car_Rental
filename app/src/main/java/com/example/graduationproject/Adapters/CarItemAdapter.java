package com.example.graduationproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.graduationproject.Models.CarItem;
import com.example.graduationproject.DetailsCarActivity;
import com.example.graduationproject.R;

import java.util.ArrayList;

public class CarItemAdapter extends RecyclerView.Adapter<CarItemAdapter.viewHolder> {

    private ArrayList<CarItem> detailsCar = new ArrayList<>();

    public CarItemAdapter() {

    }

    public void setDetailsCar(ArrayList<CarItem> detailsCar) {
        this.detailsCar = detailsCar;
        notifyDataSetChanged();
    }

    private Context context;

    public CarItemAdapter(ArrayList<CarItem> detailsCar, Context context) {
        this.detailsCar = detailsCar;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        final CarItem data_position = detailsCar.get(position);

        String uri = data_position.getCar_img();
        Glide.with(holder.itemView.getContext()).load(uri).into(holder.car_img);

        holder.car_name.setText(detailsCar.get(position).getCar_name());
        holder.car_price.setText(detailsCar.get(position).getCar_price());
        holder.seats_number.setText(detailsCar.get(position).getSeats_number());
        holder.doors_number.setText(detailsCar.get(position).getDoors_number());
        holder.suitcase_number.setText(detailsCar.get(position).getSuitcase_number());
        holder.gear_shift_txt.setText(detailsCar.get(position).getGear_shift_txt());


        holder.continue_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext() , DetailsCarActivity.class);
                intent.putExtra("CarImage" , data_position .getCar_img());
                intent.putExtra("CarName" , data_position .getCar_name());
                intent.putExtra("CarPrice" , data_position .getCar_price());
                intent.putExtra("CarSeats" , data_position .getSeats_number());
                intent.putExtra("CarDoors" , data_position .getDoors_number());
                intent.putExtra("CarSuitcase" , data_position .getSuitcase_number());
                intent.putExtra("CarGearShift" , data_position .getGear_shift_txt());

                view.getContext().startActivity(intent);


            }
        });
//        Glide.with(context)
//                .asBitmap()
//                .load(detailsCar.get(position).getCar_img())
//                .into(holder.car_img);


    }

    @Override
    public int getItemCount() {
        return detailsCar.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        private ImageView car_img;
        private TextView car_name , car_price , seats_number , doors_number , suitcase_number , gear_shift_txt;
        private Button continue_btn;



        public viewHolder(@NonNull View itemView) {
            super(itemView);

            car_img = itemView.findViewById(R.id.car_img);
            car_name = itemView.findViewById(R.id.car_name);
            car_price = itemView.findViewById(R.id.car_price);
            seats_number = itemView.findViewById(R.id.seats_number);
            doors_number = itemView.findViewById(R.id.doors_number);
            suitcase_number = itemView.findViewById(R.id.suitcase_number);
            gear_shift_txt = itemView.findViewById(R.id.gear_shift_txt);
            continue_btn = itemView.findViewById(R.id.continue_btn);


        }
    }
}
