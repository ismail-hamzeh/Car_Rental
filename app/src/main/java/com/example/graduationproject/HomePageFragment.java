package com.example.graduationproject;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;


public class HomePageFragment extends Fragment {


    MeowBottomNavigation bottom_navigation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_page, container, false);


        bottom_navigation = view.findViewById(R.id.bottom_navigation);

        bottom_navigation.setBackgroundColor(Color.TRANSPARENT);

        bottom_navigation.add(new MeowBottomNavigation.Model(1 , R.drawable.suv_icon));
        bottom_navigation.add(new MeowBottomNavigation.Model(2, R.drawable.economy_icon));
        bottom_navigation.add(new MeowBottomNavigation.Model(3 , R.drawable.luxury_icon));

        bottom_navigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {

                Fragment fragment = null;

                switch (item.getId()){

                    case 1:
                        fragment = new SuvFragment();
                        break;

                    case 2:
                        fragment = new EconomyFragment();
                        break;

                    case 3:
                        fragment = new LuxuryFragment();
                        break;
                }

                loadFragment(fragment);
            }
        });


        bottom_navigation.show(2,true);

        bottom_navigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

                //   Toast.makeText(MainActivity.this, "clicked" + item.getId(), Toast.LENGTH_SHORT).show();
            }
        });

        bottom_navigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {

                //  Toast.makeText(MainActivity.this, "Reslected " + item.getId(), Toast.LENGTH_SHORT).show();
            }
        });





       return view;
    }

    private void loadFragment(Fragment fragment) {

        getParentFragmentManager().beginTransaction().replace(R.id.container2 ,
                fragment).commit();
    }
}