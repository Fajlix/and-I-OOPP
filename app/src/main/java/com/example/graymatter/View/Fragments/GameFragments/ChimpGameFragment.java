package com.example.graymatter.View.Fragments.GameFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.graymatter.Model.Game.ChimpGame.ChimpEvent;
import com.example.graymatter.Model.Game.ChimpGame.ChimpGame;
import com.example.graymatter.Model.Game.Game;
import com.example.graymatter.Model.Game.GameObserver;
import com.example.graymatter.R;

import org.greenrobot.eventbus.EventBus;

public class ChimpGameFragment extends Fragment implements GameObserver {
    private Game game;
    private GridView gridView;
    private ChimpGameGridAdapter chimpGameGridAdapter;
    private TextView chimpTestDescription;
    private ImageView chimpTestClose;

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
        else {
            ShowBoard();
            chimpGameGridAdapter.notifyDataSetChanged();
            gridView.setAdapter(chimpGameGridAdapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chimp_game, container, false);
        super.onCreate(savedInstanceState);
        // changes gameState of game
        game = new Game();
        game.ChangeState(new ChimpGame(game));
        // adds this as a observer of the game
        game.addObserver(this);

        gridView = (GridView) view.findViewById(R.id.chimpTestGrid);

        chimpTestDescription = (TextView) view.findViewById(R.id.chimpTestDescription);
        chimpTestDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClearScreen();
                game.StartGame();
                ShowBoard();

            }
        });


        // clicking on this should take the user to the main page
        chimpTestClose = (ImageView) view.findViewById(R.id.chimpGameClose);



        return view;
    }

    private void tileHasBeenClicked(View view){
        TextView textView = view.findViewById(R.id.cardNumber);
        int n = 0;
        for (int i = 0; i < gridView.getChildCount(); i++) {
            if (gridView.getChildAt(i).equals(view))
            {
                n = i;
                break;
            }
        }
        int number = Integer.parseInt((String) textView.getText());
        EventBus.getDefault().post(new ChimpEvent(n));
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
        chimpGameGridAdapter = new ChimpGameGridAdapter();
        gridView.setNumColumns(4);
        gridView.setVerticalSpacing(10);
        gridView.setHorizontalSpacing(50);
    }

    private class ChimpGameGridAdapter extends BaseAdapter {
        ChimpGame chimpGame = ((ChimpGame) game.getGameState());
        // how many tiles on the board
        @Override
        public int getCount() {
            //TODO baaaad
            return chimpGame.getBoard().length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view1 = getLayoutInflater().inflate(R.layout.chimp_game_card, null);
            TextView numberText = view1.findViewById(R.id.cardNumber);
            ImageView imageView = view1.findViewById(R.id.whiteBackgroud);
            if (chimpGame.getBoard()[position] != 0){
                view1.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        tileHasBeenClicked(v);
                    }
                });
                if (chimpGame.getNumberVisibility())
                    numberText.setText(String.valueOf(chimpGame.getBoard()[position]));
                else
                    numberText.setVisibility(View.INVISIBLE);
            }
            else{
                numberText.setText("");
                imageView.setImageResource(R.mipmap.ic_black_square_foreground);
            }
            return view1;
        }

    }

    public void lostGame (int score)
    {
        chimpTestDescription.bringToFront();
        chimpTestClose.bringToFront();
        chimpTestDescription.setText("Game over... Your score was: " + score + " \n \nPress to play again");
    }

    public void completedGame (int score)
    {
        chimpTestDescription.bringToFront();
        chimpTestClose.bringToFront();
        chimpTestDescription.setText("Wow you completed the game! You got the max score of: " + score + " \n \nPress to play again");
    }
}
