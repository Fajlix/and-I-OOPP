package com.example.graymatter.View.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.graymatter.Model.dataAccess.DataAccess;
import com.example.graymatter.Model.dataAccess.dataMapper.DataMapperException;
import com.example.graymatter.Model.dataAccess.social.UserInfoException;
import com.example.graymatter.R;
import com.example.graymatter.View.FragmentChangeListener;
import com.example.graymatter.ViewModel.ProfileViewModel;


public class RegisterFragment extends Fragment {
    FragmentChangeListener listener;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        listener = (FragmentChangeListener)getContext();

        final ProfileViewModel profileViewModel = new ProfileViewModel();
        profileViewModel.init(listener.getDataAccess());

        final EditText editTextUsername = (EditText)view.findViewById(R.id.editTextTextUsername);
        final EditText editTextEmail = (EditText)view.findViewById(R.id.editTextTextEmailAddress);
        final EditText editTextPassword = (EditText)view.findViewById(R.id.editTextTextPassword);

        Button buttonRegister = (Button)view.findViewById(R.id.btnRegister);

        final TextView textViewError = (TextView)view.findViewById(R.id.textViewError);


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    profileViewModel.register(editTextUsername.getText().toString(), editTextEmail.getText().toString(), editTextPassword.getText().toString());
                    textViewError.setVisibility(View.INVISIBLE);
                    listener.backToProfile();
                }catch(DataMapperException | UserInfoException e){
                    textViewError.setText(e.getMessage());
                    textViewError.setVisibility(View.VISIBLE);
                }
            }
        });


        return view;
    }
}