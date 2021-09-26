package com.ebookbus.ebusbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import android.widget.FrameLayout;


import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;


public class MainActivity extends AppCompatActivity {
    private final int ID_HOME = 3;
    private final int ID_SEARCH = 1;
    private final int ID_ACCOUNT = 4;
    private final int ID_SETTINGS = 5;
    private final int ID_NOTIFICATION = 2;



    final FragmentManager fm = getSupportFragmentManager();

    final Fragment fragment1 = new homeFragment();
    final Fragment fragment2 = new accountFragment();
    final Fragment fragment3 = new notificationsFragment();
    final Fragment fragment4 = new searchFragment();
    final Fragment fragment5 = new settingsFragment();

    Fragment active = fragment1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().hide();





        FrameLayout frameLayout = findViewById(R.id.framelayout);

        MeowBottomNavigation bottomNavigation = findViewById(R.id.bottomNavigation);

        bottomNavigation.add(new MeowBottomNavigation.Model(ID_HOME, R.drawable.ic_baseline_home_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_ACCOUNT, R.drawable.ic_baseline_account_circle_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_NOTIFICATION, R.drawable.ic_baseline_notifications_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_SEARCH, R.drawable.ic_baseline_search_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_SETTINGS, R.drawable.ic_baseline_settings_24));


        fm.beginTransaction().replace(R.id.framelayout,fragment1,"ID_HOME").commit();

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {

            public void onShowItem(MeowBottomNavigation.Model item) {

               Fragment fragment = null;
                switch (item.getId()) {
                    case ID_SEARCH:

                        if(active != fragment4)
                        {
                            fm.beginTransaction().hide(active).replace(R.id.framelayout,fragment4).commit();
                            active = fragment4;
                        }else{
                            fm.beginTransaction().replace(R.id.framelayout,fragment4).commit();
                        }

                        //fragment = new searchFragment();
                        break;
                    case ID_HOME:

                        //fragment = new homeFragment();
                        if(active != fragment1){
                            fm.beginTransaction().hide(active).replace(R.id.framelayout,fragment1).commit();
                            active = fragment1;
                        }else{
                            fm.beginTransaction().replace(R.id.framelayout,fragment1).commit();
                        }

                        break;

                    case ID_ACCOUNT:
                        //fragment = new accountFragment();
                        if(active != fragment2){
                            fm.beginTransaction().hide(active).replace(R.id.framelayout,fragment2).commit();
                            active = fragment2;
                        }else{
                            fm.beginTransaction().replace(R.id.framelayout,fragment2).commit();
                        }

                        break;
                    case ID_SETTINGS:
                        //fragment = new settingsFragment();
                        if(active != fragment5){
                            fm.beginTransaction().hide(active).replace(R.id.framelayout,fragment5).commit();
                            active = fragment5;
                        }else{
                            fm.beginTransaction().replace(R.id.framelayout,fragment5).commit();
                        }

                        break;

                    case ID_NOTIFICATION:
                        //fragment = new notificationsFragment();
                        if(active != fragment3){
                            fm.beginTransaction().hide(active).replace(R.id.framelayout,fragment3).commit();
                            active = fragment3;
                        }else{
                            fm.beginTransaction().replace(R.id.framelayout,fragment3).commit();
                        }

                        break;




                }
                //fm.beginTransaction().replace(R.id.framelayout, fragment).commit();
            }
        });



        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

            }
        });
    }
    }
