package com.example.graduationproject;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

public class ProgressButton2 {

    private CardView cardview_progress2;
    private RelativeLayout relative_progress2;
    private TextView signup;
    private ProgressBar progress_signup;

    Animation fade_in;

    public ProgressButton2(Context context, View view) {

        cardview_progress2 = view.findViewById(R.id.cardview_progress2);
        relative_progress2 = view.findViewById(R.id.relative_progress2);
        signup = view.findViewById(R.id.signup);
        progress_signup = view.findViewById(R.id.progress_signup);


    }

    void buttonActivated_signup(){
        progress_signup.setVisibility(View.VISIBLE);
        signup.setText("Please Wait...");
    }
    void buttonFinished_signup(){
        relative_progress2.setBackgroundColor(cardview_progress2.getResources().getColor(R.color.green));
        progress_signup.setVisibility(View.GONE);
        signup.setText("Done");
    }
    void buttonWrong_signup(){
        progress_signup.setVisibility(View.INVISIBLE);
        signup.setText("Sorry");
        relative_progress2.setBackgroundColor(cardview_progress2.getResources().getColor(R.color.red));
    }
}
