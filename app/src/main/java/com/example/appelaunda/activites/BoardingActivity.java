package com.example.appelaunda.activites;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.appelaunda.R;
import com.example.appelaunda.adapters.SlideAdapter;

import java.util.Objects;

public class BoardingActivity extends AppCompatActivity {


    ViewPager viewPager;
    LinearLayout dotsLayout;
    Button btn;
    Animation animation;

    SlideAdapter sliderAdapter;

    TextView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_boarding); // Set content view first

        // Now you can safely hide the ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewPager = findViewById(R.id.slider);
        dotsLayout = findViewById(R.id.dots);
        btn = findViewById(R.id.get_started_btn);

        addDots(0);
        viewPager.addOnPageChangeListener(changeListener);

        sliderAdapter = new SlideAdapter(this);
        viewPager.setAdapter(sliderAdapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BoardingActivity", "Get Started button clicked");
                Intent intent = new Intent(BoardingActivity.this, RegistrationActivity.class);
                startActivity(intent);
                Log.d("BoardingActivity", "Started RegistrationActivity");
                finish();
            }
        });
    }

    private void addDots(int position) {
        if (dots == null) { // Initialize dots array only once
            dots = new TextView[3];
            for (int i = 0; i < dots.length; i++) {
                dots[i] = new TextView(this);
                dots[i].setText(Html.fromHtml("&#8226;"));
                dots[i].setTextSize(35);
                dotsLayout.addView(dots[i]);
            }
        }

        // Reset colors and set the active dot
        for (TextView dot : dots) {
            dot.setTextColor(getResources().getColor(R.color.cyan)); // Replace with your inactive color
        }
        if (position >= 0 && position < dots.length) {
            dots[position].setTextColor(getResources().getColor(R.color.white));
        }
    }
    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addDots(position);

            if(position == 0){
                btn.setVisibility(View.INVISIBLE);
            } else if (position == 1) {
                btn.setVisibility(View.INVISIBLE);
            }else {
                btn.setAnimation(animation);
                animation = AnimationUtils.loadAnimation(BoardingActivity.this, R.anim.slide_animation);
                btn.setVisibility(View.VISIBLE);
            }

        }



        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


}