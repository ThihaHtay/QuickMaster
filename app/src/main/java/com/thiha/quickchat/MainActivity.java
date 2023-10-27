package com.thiha.quickchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.thiha.quickchat.adapter.AdapterViewPager;
import com.thiha.quickchat.utils.FirebaseUtil;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNav;
    ImageButton searchButton;

    ViewPager2 pagerMain;
    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pagerMain=findViewById(R.id.pagerMain);


        bottomNav= findViewById(R.id.bottom_navigation);
        searchButton = findViewById(R.id.main_search_btn);

        fragmentArrayList.add(new ChatFragment());
        fragmentArrayList.add(new ProfileFragment());
        fragmentArrayList.add(new PostFragment());

        searchButton.setOnClickListener((v)->{
            startActivity(new Intent(MainActivity.this,SearchUserActivity.class));
        });

        AdapterViewPager adapterViewPager=new AdapterViewPager(this,fragmentArrayList);
        //set Adapter
        pagerMain.setAdapter(adapterViewPager);
        pagerMain.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottomNav.setSelectedItemId(R.id.menu_chat);
                        break;
                    case 1:
                        bottomNav.setSelectedItemId(R.id.menu_profile);
                        break;
                    case 2:
                        bottomNav.setSelectedItemId(R.id.menu_post);
                        break;
                }

                super.onPageSelected(position);
            }
        });
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu_chat) {
                    pagerMain.setCurrentItem(0);
                } else if (item.getItemId() == R.id.menu_profile) {
                    pagerMain.setCurrentItem(1);
                } else if (item.getItemId() == R.id.menu_post) {
                    pagerMain.setCurrentItem(2);
                }
                return true;
            }
        });

       getFCMToken();

    }

    void getFCMToken() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String token = task.getResult();
          //      Log.i("My token",token);
                FirebaseUtil.currentUserDetails().update("fcmToken", token);

            }
        });
    }
}