package com.example.graymatter.View.Fragments.GameFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.graymatter.R;
import com.example.graymatter.View.Adapters.GridAdapter;
import com.example.graymatter.ViewModel.ChimpGameViewModel;

public class ChimpGameFragment extends Fragment{
    private GridView gridView;
    private GridAdapter chimpGameGridAdapter;
    private TextView chimpTestDescription;
    private ImageView chimpTestClose;
    private ChimpGameViewModel chimpGameVM;

    @Override
    public void update() {
        ChimpGame chimpGame = ((ChimpGame) game.getGameState());

        boolean isGameOver = chimpGame.getGameOver();
        if (isGameOver == true)
        {
            int numberQty = chimpGame.StopGame();
            if(numberQty >= 25)
            {
                completedGame(numberQty);
            }
            else
            {
                lostGame(numberQty);
            }
        }

        ShowBoard();
        chimpGameGridAdapter.notifyDataSetChanged();
        gridView.setAdapter(chimpGameGridAdapter);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chimp_game, container, false);
        super.onCreate(savedInstanceState);
        chimpGameVM = new ViewModelProvider(this).get(ChimpGameViewModel.class);
        chimpGameVM.init();


        // changes gameState of game
        gridView = (GridView) view.findViewById(R.id.chimpTestGrid);

        chimpTestDescription = (TextView) view.findViewById(R.id.chimpTestDescription);
        chimpTestDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClearScreen();
                chimpGameVM.startChimpGame();
                ShowBoard();

            }

        });


        // clicking on this should take the user to the main page
        //ImageView reactionTestClose = (ImageView) view.findViewById(R.id.reactionTestClose);



        return view;
    }



    // clears the screen of all teh text and images to show the test
    public void ClearScreen() {

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
        chimpGameGridAdapter = new GridAdapter(chimpGameVM.getGrid());
        gridView.setNumColumns(4);
        gridView.setVerticalSpacing(10);
        gridView.setHorizontalSpacing(50);
    }
    public void lostGame (int score)
    {
        chimpTestDescription.bringToFront();
        chimpTestClose.bringToFront();
        chimpTestDescription.setText("Game over... Your score was: " + score);
    }

    public void completedGame (int score)
    {
        chimpTestDescription.bringToFront();
        chimpTestClose.bringToFront();
        chimpTestDescription.setText("Wow you completed the game! You got the max score of: " + score);
    }


}
