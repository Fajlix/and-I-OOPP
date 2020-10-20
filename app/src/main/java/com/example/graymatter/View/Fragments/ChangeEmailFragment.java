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

public class ChangeEmailFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private FragmentChangeListener listener;


    public ChangeEmailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_email, container, false);

        profileViewModel = new ProfileViewModel();
        listener = (FragmentChangeListener)getContext();

        final EditText editTextCurrentEmail = (EditText)view.findViewById(R.id.editTextCurrentEmail);
        final EditText editTextNewEmail = (EditText)view.findViewById(R.id.editTextNewEmail);
        final EditText editTextConfirmNewEmail = (EditText)view.findViewById(R.id.editTextConfirmNewEmail);

        Button buttonChangeEmail = (Button)view.findViewById(R.id.btnChangeEmail);

        buttonChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileViewModel.changeEmail(editTextCurrentEmail.getText().toString(), editTextNewEmail.getText().toString(), editTextConfirmNewEmail.getText().toString());
                listener.logoutClicked();
            }
        });

        return view;
    }
}