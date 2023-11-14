package com.example.project101;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class Entry extends AppCompatActivity {


    //Variables
    private static int SPLASH_SCREEN = 5000;
    Animation topAnim,bottomAnim;
    ImageView image;
    TextView logo,slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_entry);


        //Animations

//        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
//
//        //Hooks
//
//        image = findViewById(R.id.imageView);
        logo = findViewById(R.id.textView);
//
//        //Set animation to elements
//
//        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        new Handler().postDelayed(new Runnable(){

            @Override

            public void run(){

                Intent intent=new Intent(Entry.this,onboarding.class);

                startActivity(intent);
                finish();


            }


//        Intent intent=new Intent(Entry.this,onboarding.class);
//
//        startActivity(intent);
//        finish();

        },SPLASH_SCREEN);


    }
}