package com.wlazly.customratingbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CustomRatingBar customRatingBar = (CustomRatingBar)findViewById(R.id.customRatingBar);

    }
}
