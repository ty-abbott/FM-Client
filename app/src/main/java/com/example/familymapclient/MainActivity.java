package com.example.familymapclient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    boolean loggedIn = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.frameLayout);
        if(fragment == null){
            fragment = new LoginFragment();
            fragmentManager.beginTransaction().add(R.id.frameLayout, fragment).commit();
        }else{
            MapFragment mapFragment = new MapFragment();
            fragmentManager
                    .beginTransaction()
                    .add(R.id.frameLayout, mapFragment)
                    .commit();
        }
    }
}