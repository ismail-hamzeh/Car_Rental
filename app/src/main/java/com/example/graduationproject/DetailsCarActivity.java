package com.example.graduationproject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.grpc.internal.Stream;

@RequiresApi(api = Build.VERSION_CODES.N)
public class DetailsCarActivity extends AppCompatActivity {

    private ImageView car_img_details, expandable_arrow, back_details , enter_promo;
    private TextView car_name_details, seats_number_details, doors_number_details, suitcase_number_details,
            gear_shift_txt_details, car_price_details, more, pickup_location, return_location, address_pickup,
            address_return, pickup_date, return_date , txt_pickup_date , txt_return_date , textEncode;
    private RelativeLayout expandableView;
    private CardView cardview_details_car;
    private CheckBox checkbox_driver;
    private Button make_payment;
    private EditText promo_code;
    String url = "";
    Calendar pickup_calendar = Calendar.getInstance();
    Calendar return_calendar = Calendar.getInstance();
    FusedLocationProviderClient fusedLocationProviderClient;
    private Context context;

    String promo1 = "promo1";
    String promo2 = "promo2";
    String promo3 = "promo3";
    boolean isvalid = false;
    boolean first_click = false;
    String checkDriver = "No" ;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userID;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_car);

        back_details = findViewById(R.id.back_details);
        car_img_details = findViewById(R.id.car_img_details);
        car_name_details = findViewById(R.id.car_name_details);
        car_price_details = findViewById(R.id.car_price_details);
        seats_number_details = findViewById(R.id.seats_number_details);
        doors_number_details = findViewById(R.id.doors_number_details);
        suitcase_number_details = findViewById(R.id.suitcase_number_details);
        gear_shift_txt_details = findViewById(R.id.gear_shift_txt_details);
        more = findViewById(R.id.more);
        expandableView = findViewById(R.id.ExpandableView);
        cardview_details_car = findViewById(R.id.cardview_details_car);
        expandable_arrow = findViewById(R.id.Expandable_arrow);
        checkbox_driver = findViewById(R.id.checkbox_driver);
        pickup_location = findViewById(R.id.pickup_location);
        return_location = findViewById(R.id.return_location);
        address_pickup = findViewById(R.id.address_pickup);
        address_return = findViewById(R.id.address_return);
        make_payment = findViewById(R.id.make_payment);
        enter_promo = findViewById(R.id.enter_promo);
        promo_code = findViewById(R.id.promo_code);
        txt_pickup_date = findViewById(R.id.txt_pickup_date);
        txt_return_date = findViewById(R.id.txt_return_date);
        textEncode = findViewById(R.id.textEncode);
        pickup_date = findViewById(R.id.pickup_date);
        return_date = findViewById(R.id.return_date);
        mAuth = FirebaseAuth.getInstance();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        getCarDetails();
        getPickupLocation();


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        back_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        expandable_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (expandableView.getVisibility() == View.GONE) {
                    TransitionManager.beginDelayedTransition(cardview_details_car, new AutoTransition());
                    expandableView.setVisibility(View.VISIBLE);
                    expandable_arrow.setBackgroundResource(R.drawable.ic_arrow_up);
                    more.setVisibility(View.INVISIBLE);
                } else {
                    TransitionManager.beginDelayedTransition(cardview_details_car, new AutoTransition());
                    expandableView.setVisibility(View.GONE);
                    expandable_arrow.setBackgroundResource(R.drawable.ic_arrow_down);
                    more.setVisibility(View.VISIBLE);
                }
            }
        });

        pickup_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailsCarActivity.this,GoogleMapPickupActivity.class));
            }
        });

        return_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(DetailsCarActivity.this, GoogleMapReturnActivity.class);
                startActivityForResult(i, 1);
            }
        });

        SharedPreferences mySharedPreferences = this.getSharedPreferences("MYPREFERENCENAME", Context.MODE_PRIVATE);

        pickup_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {


                        pickup_calendar.set(Calendar.YEAR,year);
                        pickup_calendar.set(Calendar.MONTH,month);
                        pickup_calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);


                        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

                                pickup_calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                pickup_calendar.set(Calendar.MINUTE, minute);

                                txt_pickup_date.setText(simpleDateFormat.format(pickup_calendar.getTime()));

                                SharedPreferences.Editor editor = mySharedPreferences.edit();
                                editor.putString("details", String.valueOf(simpleDateFormat.format(pickup_calendar.getTime())));
                                editor.apply();

                            }
                        };

                        new TimePickerDialog(DetailsCarActivity.this,timeSetListener,
                                pickup_calendar.get(Calendar.HOUR_OF_DAY), pickup_calendar.get(Calendar.MINUTE), false).show();


                    }
                };

                new DatePickerDialog(DetailsCarActivity.this,dateSetListener,
                        pickup_calendar.get(Calendar.YEAR), pickup_calendar.get(Calendar.MONTH), pickup_calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        return_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txt_return_date = findViewById(R.id.txt_return_date);

                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {


                        return_calendar.set(Calendar.YEAR, year);
                        return_calendar.set(Calendar.MONTH, month);
                        return_calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

                                return_calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                return_calendar.set(Calendar.MINUTE, minute);

                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                                txt_return_date.setText(simpleDateFormat.format(return_calendar.getTime()));

                                    try {
                                        String d1 = txt_pickup_date.getText().toString();
                                        String d2 = txt_return_date.getText().toString();

                                        String total = car_price_details.getText().toString();
                                        int theTotal = Integer.parseInt(total);

                                        Date date1 = simpleDateFormat.parse(d1);
                                        Date date2 = simpleDateFormat.parse(d2);
                                        long difference = Math.abs(date1.getTime() - date2.getTime());

                                        long difftDays = difference / (24 * 60 * 60 * 1000);

                                        Log.i("Testing", "days" + difftDays);
                                        car_price_details.setText("" + difftDays * theTotal);


                                    } catch (Exception ex) {

                                        ex.printStackTrace();
                                    }

                            }
                        };

                        new TimePickerDialog(DetailsCarActivity.this, timeSetListener,
                                return_calendar.get(Calendar.HOUR_OF_DAY), return_calendar.get(Calendar.MINUTE), false).show();


                    }
                };
                new DatePickerDialog(DetailsCarActivity.this, dateSetListener,
                        return_calendar.get(Calendar.YEAR), return_calendar.get(Calendar.MONTH), return_calendar.get(Calendar.DAY_OF_MONTH)).show();

                try {
                    String d1 = txt_pickup_date.getText().toString();
                    String d2 = txt_return_date.getText().toString();
                    String total = car_price_details.getText().toString();
                    int theTotal = Integer.parseInt(total);

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                    Date date1 = simpleDateFormat.parse(d1);
                    Date date2 = simpleDateFormat.parse(d2);
                    long difference = Math.abs(date1.getTime() - date2.getTime());

                    long difftDays = difference / (24 * 60 * 60 * 1000);

                    Log.i("Testing", "days" + difftDays);
                    car_price_details.setText("" + theTotal / difftDays);


                } catch (Exception ex) {

                    ex.printStackTrace();
                }
            }


        });
        

        checkbox_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    String d1 = txt_pickup_date.getText ().toString ();
                    String d2 = txt_return_date.getText ().toString ();

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        Date date1 = simpleDateFormat.parse(d1);
                        Date date2 = simpleDateFormat.parse(d2);
                        long difference = Math.abs(date1.getTime() - date2.getTime());

                        long difftDays = difference / (24 * 60 * 60 * 1000);

                        Log.i("Testing", "days" + difftDays);

                        if (checkbox_driver.isChecked()) {

                                String total = car_price_details.getText().toString();
                                int theTotal = Integer.parseInt(total);

                                car_price_details.setText(String.valueOf(10 * difftDays + theTotal));
                                checkDriver = "Yes";

                        } else {

                            String total = car_price_details.getText().toString();
                            int theTotal = Integer.parseInt(total);
                            car_price_details.setText(String.valueOf(theTotal - 10 * difftDays));
                            checkDriver = "No";
                        }

                }
                catch(Exception ex)
                {

                    ex.printStackTrace();
                }

            }

        });

        enter_promo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String promoCode1 = promo_code.getText().toString();
                String promoCode2 = promo_code.getText().toString();
                String promoCode3 = promo_code.getText().toString();

                    isvalid = validate(promoCode1, promoCode2, promoCode3);

                    if (!isvalid) {

                        Toast.makeText(DetailsCarActivity.this,"not valid",Toast.LENGTH_SHORT).show();

                    }else {

                        if (first_click == false) {
                            String total = car_price_details.getText().toString();
                            int theTotal = Integer.parseInt(total);
                            car_price_details.setText(String.valueOf(theTotal - (theTotal * 0.20)));
                            first_click = true;
                        }else {
                            Toast.makeText(DetailsCarActivity.this,"Already exist",Toast.LENGTH_SHORT).show();
                        }
                    }

            }
        });

        make_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bookingData();
            }
        });

    }

    private void getCarDetails() {
        url = getIntent().getStringExtra("CarImage");

        Glide.with(this).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(car_img_details);

        car_name_details.setText(getIntent().getExtras().getString("CarName"));
        car_price_details.setText(getIntent().getExtras().getString("CarPrice"));
        seats_number_details.setText(getIntent().getExtras().getString("CarSeats"));
        doors_number_details.setText(getIntent().getExtras().getString("CarDoors"));
        suitcase_number_details.setText(getIntent().getExtras().getString("CarSuitcase"));
        gear_shift_txt_details.setText(getIntent().getExtras().getString("CarGearShift"));
    }

    private void getPickupLocation() {

       userID = mAuth.getCurrentUser().getUid();

       db.collection("Users").document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
           @Override
           public void onComplete(@NonNull Task<DocumentSnapshot> task) {

               if (task.isSuccessful()){

                   DocumentSnapshot snapshot = task.getResult();

                   if (snapshot.exists()) {
                       String address = snapshot.getString("address");
                       if (address == null) {
                           if (ActivityCompat.checkSelfPermission(DetailsCarActivity.this,
                                   Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

                               getCurrentLocation();
                           } else {
                               ActivityCompat.requestPermissions(DetailsCarActivity.this,
                                       new String[]{Manifest.permission.ACCESS_FINE_LOCATION},2);
                           }
                       } else {
                           address_pickup.setText(address);
                       }

                   }
               }
           }
       });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String map_return = data.getStringExtra("return");
                address_return.setText(map_return);

            }
        }
    }

    public void bookingData(){

        String pickup_date_to = simpleDateFormat.format(pickup_calendar.getTime());
        String return_date_to = simpleDateFormat.format(return_calendar.getTime());

        if (address_pickup.getText().toString().equals("Address")){
            address_pickup.setError("error");

        } else if (address_return.getText().toString().equals("Address")) {
            address_return.setError("");

        } else if (pickup_date_to.equals("date")){

        } else if (return_date_to.equals("")){

        } else {
            Intent intent = new Intent(DetailsCarActivity.this, PaymentActivity.class);

            url = getIntent().getStringExtra("CarImage");

            Glide.with(DetailsCarActivity.this).load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(car_img_details);

            intent.putExtra("car image", url);
            intent.putExtra("car_name_DB", car_name_details.getText().toString());
            intent.putExtra("pickup_location_DB", address_pickup.getText().toString());
            intent.putExtra("return_location_DB", address_return.getText().toString());
            intent.putExtra("pickup_date_DB", pickup_date_to);
            intent.putExtra("return_date_DB", return_date_to);
            intent.putExtra("driver_DB", checkDriver);
            intent.putExtra("total_price", car_price_details.getText().toString());
            intent.putExtra("seats_DB", seats_number_details.getText().toString());
            intent.putExtra("doors_DB", doors_number_details.getText().toString());
            intent.putExtra("bags_DB", suitcase_number_details.getText().toString());
            intent.putExtra("gearShift_DB", gear_shift_txt_details.getText().toString());

            startActivity(intent);
        }
    }

    private boolean validate(String promoCode1, String promoCode2 , String promoCode3) {
        if (promoCode1.equals(promo1) || promoCode2.equals(promo2) || promoCode3.equals(promo3))
        {

            return true;
        }
        return false;
    }

    @SuppressLint("MissingPermission")
    public void getCurrentLocation() {

        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

                Location location = task.getResult();
                if (location != null) {
                    Geocoder geocoder = new Geocoder(DetailsCarActivity.this, Locale.getDefault());
                    try {
                        List<Address> address = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),1);

                        address_pickup.setText(address.get(0).getAddressLine(0));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

}