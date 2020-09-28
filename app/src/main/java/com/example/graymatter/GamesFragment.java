package com.example.graymatter;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class GamesFragment extends Fragment {

    //private BottomNavigationView bottomNavigationView;

    private FragmentChangeListener listener;



    public GamesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_games, container, false);

        listener = (FragmentChangeListener)getContext();


        //bottomNavigationView = (BottomNavigationView)getActivity().findViewById(R.id.bottomNavigationView);


        ImageButton imageButton = (ImageButton)view.findViewById(R.id.imageButton11);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //bottomNavigationView.setVisibility(View.GONE);


                listener.reactionTestClicked();


//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id. fragment,new ReactionTestActivity());
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();



//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.fragment, new ProfileFragment());
//                fragmentTransaction.commit();
//
            }
        });

        return view;
    }



}