package com.example.graduationproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.graduationproject.R;

public class WalkthroughViewPagerAdapter extends PagerAdapter {

   Context context;

   int[] images = {
           R.drawable.porsche,
           R.drawable.lamborghini,
           R.drawable.classic
   };

   int[] headings = {
           R.string.title_porsche,
           R.string.title_lamborghini,
           R.string.title_classic,
   };

   int[] description = {
           R.string.desc_porsche,
           R.string.desc_lamborghini,
           R.string.desc_classic
   };

    public WalkthroughViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view  == (RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_layout,container,false);

        ImageView walkthrough_Image = (ImageView) view.findViewById(R.id.walkthroughImage);
        TextView walkthrough_Heading = (TextView) view.findViewById(R.id.walkthroughHeading);
        TextView walkthrough_Desc = (TextView) view.findViewById(R.id.walkthroughDesc);

        walkthrough_Image.setImageResource(images[position]);
        walkthrough_Heading.setText(headings[position]);
        walkthrough_Desc.setText(description[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((RelativeLayout)object);
    }
}
