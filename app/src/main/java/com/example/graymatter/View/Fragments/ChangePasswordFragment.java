package com.example.graymatter.View.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.graymatter.R;
import com.example.graymatter.View.FragmentChangeListener;
import com.example.graymatter.ViewModel.ProfileViewModel;


public class ChangePasswordFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private FragmentChangeListener listener;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        profileViewModel = new ProfileViewModel();
        listener = (FragmentChangeListener)getContext();

        final EditText editTextCurrentPassword = (EditText)view.findViewById(R.id.editTextCurrentPassword);
        final EditText editTextNewPassword = (EditText)view.findViewById(R.id.editTextNewPassword);
        final EditText editTextConfirmNewPassword = (EditText)view.findViewById(R.id.editTextConfirmNewPassword);

        Button buttonChangePassword = (Button)view.findViewById(R.id.btnChangePassword);

        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileViewModel.changePassword(editTextCurrentPassword.getText().toString(), editTextNewPassword.getText().toString(), editTextConfirmNewPassword.getText().toString());
                listener.logoutClicked();
            }
        });

        return view;
    }
}