package com.example.graymatter;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chimp_test);

        Intent intent = new Intent(this, ChimpTestActivity.class);
        startActivity(intent);
    }

}