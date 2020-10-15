package com.example.graymatter.View.Fragments.GameFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.graymatter.R;
import com.example.graymatter.View.Adapters.ChimpGridAdapter;
import com.example.graymatter.ViewModel.ChimpGameViewModel;

public class ChimpGameFragment extends Fragment{
    private GridView gridView;
    private ChimpGridAdapter chimpGameChimpGridAdapter;
    private TextView chimpTestDescription;
    //private ImageView chimpTestClose;
    private ChimpGameViewModel chimpGameVM;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chimp_game, container, false);
        super.onCreate(savedInstanceState);

        chimpGameVM = new ViewModelProvider(this).get(ChimpGameViewModel.class);
        gridView = view.findViewById(R.id.chimpTestGrid);
        chimpTestDescription = view.findViewById(R.id.chimpTestDescription);
        chimpGameVM.init();

        chimpGameVM.getGameOver().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean gameOver) {
                if (gameOver) {
                    int numberQty = chimpGameVM.getScore();
                    if(numberQty >= 25)
                        showWonGame(numberQty);
                    else
                        showLostGame(numberQty);
                }
            }
        });
        chimpGameVM.getGrid().observe(getViewLifecycleOwner(), new Observer<int[]>() {
            @Override
            public void onChanged(int[] grid) {
                chimpGameChimpGridAdapter = new ChimpGridAdapter(grid);
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
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    chimpGameVM.tileHasBeenClicked(position);
            }
        });
        chimpTestDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClearStartScreen();
                chimpGameVM.startChimpGame();
                ShowBoard();
            }
        });
        // clicking on this should take the user to the main page
        //ImageView reactionTestClose = (ImageView) view.findViewById(R.id.reactionTestClose);
        //chimpTestClose = (ImageView) view.findViewById(R.id.chimpGameClose);
        return view;
    }

    // clears the screen of all the text and images to show the test
    public void ClearStartScreen() {
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

    public void ShowBoard() {
        gridView.bringToFront();
        gridView.setNumColumns(4);
        gridView.setVerticalSpacing(10);
        gridView.setHorizontalSpacing(50);
    }

    public void showLostGame (int score) {
        chimpTestDescription.bringToFront();
        //chimpTestClose.bringToFront();
        chimpTestDescription.setText("Game over... Your score was: " + score + " \n \nPress to play again");
    }

    public void showWonGame (int score) {
        chimpTestDescription.bringToFront();
        //chimpTestClose.bringToFront();
        chimpTestDescription.setText("Wow you completed the game! You got the max score of: " + score + " \n \nPress to play again");
    }


}
