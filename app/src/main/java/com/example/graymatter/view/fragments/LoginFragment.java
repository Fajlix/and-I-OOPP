package com.example.graymatter.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.graymatter.model.dataAccess.dataMapper.DataMapperException;
import com.example.graymatter.model.dataAccess.social.UserInfoException;
import com.example.graymatter.R;
import com.example.graymatter.view.FragmentChangeListener;
import com.example.graymatter.viewModel.ProfileViewModel;

import java.io.IOException;


public class LoginFragment extends Fragment {

    private FragmentChangeListener listener;
    private ProfileViewModel profileViewModel;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        listener = (FragmentChangeListener)getContext();

        profileViewModel = new ProfileViewModel();
        profileViewModel.init(listener.getDataAccess());

        final EditText editTextUsername = (EditText)view.findViewById(R.id.editTextUsername);
        final EditText editTextPassword = (EditText)view.findViewById(R.id.editTextTextPassword);

        Button buttonLogin = (Button)view.findViewById(R.id.btnLogin);
        Button buttonRegister = (Button)view.findViewById(R.id.btnRegister);

        final TextView textViewError = (TextView)view.findViewById(R.id.textViewError);


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    profileViewModel.login(editTextUsername.getText().toString(), editTextPassword.getText().toString());
                    textViewError.setVisibility(View.INVISIBLE);
                    listener.backToProfile();
                }catch(DataMapperException | UserInfoException | IOException e){
                    textViewError.setText(e.getMessage());
                    textViewError.setVisibility(View.VISIBLE);
                }
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.registerClicked();
            }
        });


        return view;
    }
}