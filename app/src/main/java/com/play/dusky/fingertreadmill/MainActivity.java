package com.play.dusky.fingertreadmill;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.play.dusky.fingertreadmill.View.Treadmill;

public class MainActivity extends AppCompatActivity {
    Treadmill treadmill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        treadmill=(Treadmill)findViewById(R.id.treadmill);
    }
}
