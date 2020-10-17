package com.example.graymatter.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.graymatter.R;

public class ToHLevels extends Fragment implements OnClickListener {

    public void onCreate(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.toh_levels, container, false);
        super.onCreate(savedInstanceState);

        view.findViewById(R.id.easy).setOnClickListener(this);
        view.findViewById(R.id.medium).setOnClickListener(this);
        view.findViewById(R.id.hard).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(ToHLevels.this, ToHPlay.class);
        switch (v.getId()) {
            case R.id.easy:
                intent.putExtra("numofdisks", 4);
                break;

            case R.id.medium:
                intent.putExtra("numofdisks", 5);
                break;

            case R.id.hard:
                intent.putExtra("numofdisks", 6);
                break;
        }
        startActivity(intent);
    }
}