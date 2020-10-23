package com.example.graymatter.View.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.graymatter.Model.dataAccess.dataMapper.DataMapperException;
import com.example.graymatter.Model.dataAccess.social.UserInfoException;
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
        listener = (FragmentChangeListener)getContext();

        profileViewModel = new ProfileViewModel();
        profileViewModel.init(listener.getDataAccess());

        final EditText editTextCurrentEmailPassword = (EditText)view.findViewById(R.id.editTextCurrentEmailPassword);
        final EditText editTextNewEmail = (EditText)view.findViewById(R.id.editTextNewEmail);
        final EditText editTextConfirmNewEmail = (EditText)view.findViewById(R.id.editTextConfirmNewEmail);

        final TextView textViewError = (TextView)view.findViewById(R.id.textViewError);

        Button buttonChangeEmail = (Button)view.findViewById(R.id.btnChangeEmail);

        buttonChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    profileViewModel.changeEmail(editTextNewEmail.getText().toString(), editTextConfirmNewEmail.getText().toString(), editTextCurrentEmailPassword.getText().toString());
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