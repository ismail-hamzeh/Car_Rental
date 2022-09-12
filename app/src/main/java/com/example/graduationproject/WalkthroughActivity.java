package com.example.graduationproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.graduationproject.Adapters.WalkthroughViewPagerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.simple.transformslibrary.ZoomInTransform;

public class WalkthroughActivity extends AppCompatActivity {

    private ViewPager walkthrough_Pager;
    private LinearLayout indicator_Layout;
    private Button walkthrough_skipBtn, walkthrough_loginBtn , walkthrough_signUpBtn;

    TextView [] dots;
    WalkthroughViewPagerAdapter walkthroughViewPagerAdapter;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkthrough);



        walkthrough_skipBtn = findViewById(R.id.walkthrough_skipBtn);
        walkthrough_loginBtn = findViewById(R.id.walkthrough_loginbtn);
        walkthrough_signUpBtn = findViewById(R.id.walkthrough_signUpBtn);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null){
            startActivity(new Intent(WalkthroughActivity.this, MainActivity.class));
            finish();
        }

        walkthrough_skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(WalkthroughActivity.this, LoginActivity.class));
                finish();
            }
        });

        walkthrough_loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(WalkthroughActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });

        walkthrough_signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              Intent intent = new Intent(WalkthroughActivity.this , SignUpActivity.class);
              startActivity(intent);
              finish();
            }
        });

        walkthrough_Pager = (ViewPager) findViewById(R.id.walkthroughPager);
        indicator_Layout = (LinearLayout) findViewById(R.id.indicatorLayout);

        walkthroughViewPagerAdapter = new WalkthroughViewPagerAdapter(this);
        walkthrough_Pager.setPageTransformer(true,new ZoomInTransform());

        walkthrough_Pager.setAdapter(walkthroughViewPagerAdapter);

        setUpIndicator(0);

        walkthrough_Pager.addOnPageChangeListener(viewListener);

         }

        public void setUpIndicator ( int position){

            dots = new TextView[3];
            indicator_Layout.removeAllViews();

            for (int i = 0; i < dots.length; i++) {

                dots[i] = new TextView(this);
                dots[i].setText(Html.fromHtml("&#8226"));
                dots[i].setTextSize(35);
                dots[i].setGravity(View.TEXT_ALIGNMENT_CENTER);
                dots[i].setTextColor(getResources().getColor(R.color.white, getApplicationContext().getTheme()));
                indicator_Layout.addView(dots[i]);
            }

            dots[position].setTextColor(getResources().getColor(R.color.gold, getApplicationContext().getTheme()));


        }



         ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
              @Override
              public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

             }

             @Override
             public void onPageSelected(int position) {

                 setUpIndicator(position);

                if (position == 2){

                    walkthrough_signUpBtn.setVisibility(View.VISIBLE);
                    walkthrough_loginBtn.setVisibility(View.VISIBLE);
                }

             }

              @Override
              public void onPageScrollStateChanged(int state) {

               }
           };

    private int getitem(int i){

        return walkthrough_Pager.getCurrentItem() + 1;
    }
}