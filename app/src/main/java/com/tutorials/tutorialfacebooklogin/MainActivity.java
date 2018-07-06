package com.tutorials.tutorialfacebooklogin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.tutorials.tutorialfacebooklogin.fragment.LoginFragment;

public class MainActivity extends AppCompatActivity {

    public static MainActivity instance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instance = this;

        openFragment(LoginFragment.newInstance(), false);

    }

    public void openFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);

        if (addToBackStack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }
}
