package com.example.graymatter.View;

import java.math.BigDecimal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class ToHPlay extends Fragment {

    private int totalMoves, minPossibleMoves;

    public void onCreate(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics displaymetrics = getResources().getDisplayMetrics();
        int densityDpi = displaymetrics.densityDpi;

        View view = inflater.inflate(new ToHDraw(this, displaymetrics.widthPixels,
                displaymetrics.heightPixels, getActivity().getIntent().getExtras().getInt(
                "numofdisks")));

        // setContentView(new ToHDraw(this, displaymetrics.widthPixels,
        //        displaymetrics.heightPixels, getIntent().getExtras().getInt(
        //        "numofdisks")));

        // Possible min moves (2^n - 1); n number of disks
        minPossibleMoves = new BigDecimal(2).pow(
                getActivity().getIntent().getExtras().getInt("numofdisks")).intValue() - 1;
    }

    public void gameOver(int moves) {
        totalMoves = moves;
        showDialog();
    }

    public void showDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Game Over");

        if (totalMoves > minPossibleMoves)
            alert.setMessage("Least possible moves are " + minPossibleMoves
                    + ", you made " + totalMoves + ".");
        else
            alert.setMessage("You won, congrats!!!!");

        alert.setPositiveButton("Back", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                getActivity().finish();
            }
        });

        alert.create().show();
    }
}
