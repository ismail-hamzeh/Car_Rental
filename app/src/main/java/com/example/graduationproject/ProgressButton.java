package com.example.graduationproject;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

public class ProgressButton {

    private CardView cardview_progress;
    private RelativeLayout relative_progress;
    private TextView login;
    private ProgressBar progress_login;

    Animation fade_in;

    public ProgressButton(Context context, View view) {

        cardview_progress = view.findViewById(R.id.cardview_progress);
        relative_progress = view.findViewById(R.id.relative_progress);
        login = view.findViewById(R.id.login);
        progress_login = view.findViewById(R.id.progress_login);


    }

    void buttonActivated(){
        progress_login.setVisibility(View.VISIBLE);
        login.setText("Please Wait...");
    }
    void buttonFinished(){
        relative_progress.setBackgroundColor(cardview_progress.getResources().getColor(R.color.green));
        progress_login.setVisibility(View.GONE);
        login.setText("Done");
    }
    void buttonWrong(){
        progress_login.setVisibility(View.INVISIBLE);
        login.setText("Sorry");
        relative_progress.setBackgroundColor(cardview_progress.getResources().getColor(R.color.red));
    }
}
