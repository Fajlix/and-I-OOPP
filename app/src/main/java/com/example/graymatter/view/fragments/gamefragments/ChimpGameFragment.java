package com.example.graymatter.view.fragments.gamefragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.graymatter.R;
import com.example.graymatter.view.adapters.ChimpGridAdapter;
import com.example.graymatter.view.adapters.GameFragment;
import com.example.graymatter.view.FragmentChangeListener;
import com.example.graymatter.viewModel.ChimpGameViewModel;

import java.util.ArrayList;

/**
 * @author Viktor Felix
 * the class that represents the fragment for Chimp Game
 */
public class ChimpGameFragment extends Fragment implements GameFragment {
    private GridView gridView;
    private ChimpGridAdapter chimpGameChimpGridAdapter;
    private TextView chimpTestDescription;
    private ChimpGameViewModel chimpGameVM;
    private FragmentChangeListener listener;

    int lastPos = -1;

    /**
     * Initializes the start screen, and the updates it depending on what the user does.
     *
     * @return returns the view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chimp_game, container, false);
        super.onCreate(savedInstanceState);
        listener = (FragmentChangeListener)getContext();

        chimpGameVM = new ViewModelProvider(this).get(ChimpGameViewModel.class);
        gridView = view.findViewById(R.id.chimpTestGrid);
        chimpTestDescription = view.findViewById(R.id.chimpTestDescription);
        chimpGameVM.init(listener.getDataAccess());

        chimpGameVM.getGameOver().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean gameOver) {
                if (gameOver) {
                    int numberQty = chimpGameVM.getScore();
                    if (numberQty >= 25)
                        showWonGame(numberQty);
                    else
                        showLostGame(numberQty);
                }
            }
        });
        chimpGameVM.getGrid().observe(getViewLifecycleOwner(), new Observer<int[]>() {
            @Override
            public void onChanged(int[] grid) {
                chimpGameChimpGridAdapter = new ChimpGridAdapter(ChimpGameFragment.this, arrayToArrayList(grid), lastPos);
                gridView.setAdapter(chimpGameChimpGridAdapter);
            }
        });
        chimpGameVM.getVisibility().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean visibility) {
                if (chimpGameChimpGridAdapter != null)
                    chimpGameChimpGridAdapter.setVisibility(visibility);
            }
        });
        chimpTestDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearStartScreen();
                chimpGameVM.startChimpGame();
                showBoard();
            }
        });

        return view;
    }

    /**
     * A method to clear the start screen to show the game screen
     */
    public void clearStartScreen() {
        chimpTestDescription.setText("");
        final ImageView iconNumber1 = this.getView().findViewById(R.id.iconNumber1);
        final ImageView iconNumber2 = this.getView().findViewById(R.id.iconNumber2);
        final ImageView iconNumber3 = this.getView().findViewById(R.id.iconNumber3);
        final ImageView iconNumber4 = this.getView().findViewById(R.id.iconNumber4);
        iconNumber1.setVisibility(View.GONE);
        iconNumber2.setVisibility(View.GONE);
        iconNumber3.setVisibility(View.GONE);
        iconNumber4.setVisibility(View.GONE);
    }

    /**
     * Shows the actual game screen
     */
    public void showBoard() {
        gridView.bringToFront();
        gridView.setNumColumns(4);
        gridView.setVerticalSpacing(10);
        gridView.setHorizontalSpacing(50);
    }

    /**
     * When the game is lost this method is called to show the lost game screen
     *
     * @param score is the score the user got
     */
    public void showLostGame(int score) {
        chimpTestDescription.bringToFront();
        chimpTestDescription.setText("Game over... Your score was: " + score + " \n \nPress to play again");
    }

    /**
     * When the game is won this method is called to show the won game screen
     *
     * @param score is the score the user got
     */
    public void showWonGame(int score) {
        chimpTestDescription.bringToFront();
        chimpTestDescription.setText("Wow you completed the game! You got the max score of: "
                + score + " \n \nPress to play again");
    }

    /**
     * This method is called each time a tile han been clicked to notify the Viewmodel
     *
     * @param position represents the position of the card that has been clicked
     */

    /**
     * Changes the array to an ArrayList, which makes it possible to abstract more
     *
     * @param arr is the array that should be converted
     * @return returns an ArrayList with the same data as the array
     */
    public ArrayList<Integer> arrayToArrayList(int[] arr) {
        ArrayList<Integer> arrayList = new ArrayList<>();

        // Using add() method to add elements in array_list
        for (int i = 0; i < arr.length; i++)
            arrayList.add(arr[i]);

        return arrayList;
    }

    @Override
    public void makeMove(int position) {
        lastPos = position;
        chimpGameVM.makeMove(position);

    }
}
