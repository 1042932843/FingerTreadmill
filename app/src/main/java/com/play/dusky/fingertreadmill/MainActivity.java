package com.play.dusky.fingertreadmill;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.play.dusky.fingertreadmill.View.Treadmill;
import com.play.dusky.fingertreadmill.View.TreadmillSurfaceView;

public class MainActivity extends AppCompatActivity {
    Treadmill treadmill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TreadmillSurfaceView mySurfaceView = new TreadmillSurfaceView(this);
        setContentView(mySurfaceView);
    }
}
