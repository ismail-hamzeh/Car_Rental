package com.example.graduationproject;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;


public class TyperTextView extends androidx.appcompat.widget.AppCompatTextView {
    private CharSequence sequence;
    private int myIndex;
    private long delay = 150;



    public TyperTextView(Context context) {
        super(context);
    }

    public TyperTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            setText(sequence.subSequence(0, myIndex++));
            if (myIndex <= sequence.length()){
                handler.postDelayed(runnable, delay );
            }

        }
    };


    public void displayTextWithAnimation(CharSequence txt) {
        sequence = txt;
        myIndex = 0;

        setText("");
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, delay);
    }
    public void setCharacterDelay(long m) {
        delay = m;
    }
}