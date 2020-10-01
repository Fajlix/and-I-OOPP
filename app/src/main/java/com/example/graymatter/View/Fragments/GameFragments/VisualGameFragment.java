package com.example.graymatter.View.Fragments.GameFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.graymatter.Game.MemoryGame.MemoryEvent;
import com.example.graymatter.Game.MemoryGame.MemoryGame;
import com.example.graymatter.Game.MemoryGame.MemoryGrid;
import com.example.graymatter.Model.Game.ChimpGame.ChimpEvent;
import com.example.graymatter.Model.Game.ChimpGame.ChimpGame;
import com.example.graymatter.Model.Game.Game;
import com.example.graymatter.Model.Game.GameObserver;
import com.example.graymatter.R;

import org.greenrobot.eventbus.EventBus;

public class VisualGameFragment extends Fragment implements GameObserver {
    private Game game;
    private GridView gridView;
    private VisualGameGridAdapter visualGameGridAdapter;
    private TextView visualGameDescription;
    private ImageView visualGameClose;

    @Override
    public void update() {
        MemoryGame memoryGame = ((MemoryGame) game.getGameState());

        boolean isGameOver = memoryGame.getGameOver();
        if (isGameOver == true)
        {
            int level = memoryGame.StopGame();
            if(level >= 20)
            {
                completedGame(level);
            }
            else
            {
                lostGame(level);
            }
        }
        else {
            ShowBoard();
            visualGameGridAdapter.notifyDataSetChanged();
            gridView.setAdapter(visualGameGridAdapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visual_game, container, false);
        super.onCreate(savedInstanceState);
        // changes gameState of game
        game = new Game();
        //game.ChangeState(new VisualGame(game));
        // adds this as a observer of the game
        //game.addObserver(this);

        gridView = (GridView) view.findViewById(R.id.visualGameGrid);

        visualGameDescription = (TextView) view.findViewById(R.id.visualGameDescription);
        visualGameDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClearScreen();
                game.StartGame();
                ShowBoard();

            }
        });


        // clicking on this should take the user to the main page
        ImageView visualGameClose = (ImageView) view.findViewById(R.id.visualGameClose);



        return view;
    }

    private void tileHasBeenClicked(View view){
        int n = 0;
        for (int i = 0; i < gridView.getChildCount(); i++) {
            if (gridView.getChildAt(i).equals(view))
            {
                n = i;
                break;
            }
        }
        EventBus.getDefault().post(new MemoryEvent(n));
    }

    // clears the screen of all teh text and images to show the test
    public void ClearScreen() {
        visualGameDescription.setText("");
    }

    public void ShowBoard() {
        gridView.bringToFront();
        visualGameGridAdapter = new VisualGameFragment.VisualGameGridAdapter();
        gridView.setNumColumns(4);
        gridView.setVerticalSpacing(10);
        gridView.setHorizontalSpacing(50);
    }

    private class VisualGameGridAdapter extends BaseAdapter {
        MemoryGame memoryGame = ((MemoryGame) game.getGameState());
        // how many tiles on the board
        @Override
        public int getCount() {
            //TODO baaaad
            return memoryGame.getGridAsArrayList().size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view1 = getLayoutInflater().inflate(R.layout.visual_game_card, null);
            ImageView imageView = view1.findViewById(R.id.whiteBackgroud);
            if (memoryGame.getGridAsArrayList().get(position).equals(MemoryGrid.TileState.CORRECTHIDDEN)) {
                imageView.setImageResource(R.mipmap.ic_gray_memory_foreground);
                view1.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        tileHasBeenClicked(v);
                    }
                });
            }
            else if (memoryGame.getGridAsArrayList().get(position).equals(MemoryGrid.TileState.CORRECTCHOSEN))
            {
                imageView.setImageResource(R.mipmap.ic_gray_memory_foreground);
            }
            else if (memoryGame.getGridAsArrayList().get(position).equals(MemoryGrid.TileState.INCORRECTHIDDEN))
            {
                imageView.setImageResource(R.mipmap.ic_gray_memory_foreground);
            }
            else if (memoryGame.getGridAsArrayList().get(position).equals(MemoryGrid.TileState.INCORRECTCHOSEN))
            {
                imageView.setImageResource(R.mipmap.ic_gray_memory_foreground);
            }
            return view1;
        }

    }

    public void lostGame (int level)
    {
        visualGameDescription.bringToFront();
        visualGameClose.bringToFront();
        visualGameDescription.setText("Game over... Your score was: " + level + " \n \nPress to play again");
    }

    public void completedGame (int level)
    {
        visualGameDescription.bringToFront();
        visualGameClose.bringToFront();
        visualGameDescription.setText("Wow you completed the game! You got the max score of: " + level + " \n \nPress to play again");
    }
}