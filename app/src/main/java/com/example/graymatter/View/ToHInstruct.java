package com.example.graymatter.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.graymatter.R;

public class ToHInstruct extends Fragment {

    public void onCreate(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.toh_desc, container,false);
        super.onCreate(savedInstanceState);
    }
}
