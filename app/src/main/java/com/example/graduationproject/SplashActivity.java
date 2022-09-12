package com.example.graduationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Delayed;


public class SplashActivity extends AppCompatActivity  {

    private ImageView logo_Splash;
    private Animation zoom;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        RotateAnimation rotate = new RotateAnimation(360, 0, Animation.ZORDER_TOP, 0.5f, Animation.ZORDER_TOP, 0.5f);
//        rotate.setDuration(3000);
//        rotate.setInterpolator(new LinearInterpolator());
//
//        logo_Splash = findViewById(R.id.logoSplash);
//        logo_Splash.startAnimation(rotate);


        TyperTextView animationtext = findViewById(R.id.typertextSplash);
        animationtext.setText("");
        animationtext.setCharacterDelay(150);
        animationtext.displayTextWithAnimation("We Keep You Moving.");


        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                finish();
                Intent intent = new Intent(SplashActivity.this , WalkthroughActivity.class);
                startActivity(intent);
            }
        };timer.schedule(timerTask,4000);








    }

}