package com.example.mapcustommarker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created on 8/9/17.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openMapsActivity(View v) {
        startActivity(new Intent(MainActivity.this, MapsActivity.class));
    }

    public void openCustomMapsActivity(View v) {
        startActivity(new Intent(MainActivity.this, CustomInfoWindowMapsActivity.class));
    }
}
