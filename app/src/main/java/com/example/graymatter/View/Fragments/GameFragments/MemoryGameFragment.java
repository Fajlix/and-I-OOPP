package com.example.graymatter.View.Fragments.GameFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.graymatter.Model.Game.MemoryGame.MemoryGrid;
import com.example.graymatter.R;
import com.example.graymatter.View.Adapters.MemoryGridAdapter;
import com.example.graymatter.ViewModel.MemoryGameViewModel;

import java.util.ArrayList;

/**
 * @author Viktor
 * the class that represents the fragment for Memory Game
 */
public class MemoryGameFragment extends Fragment {
    private GridView gridView;
    private MemoryGridAdapter visualGameGridAdapter;
    private TextView visualGameDescription;
    private TextView livesText;
    private MemoryGameViewModel visualMemoryVM;
    private boolean visibility = true;
    private ScreenState screenState;

    int lastPos = -1;

    enum ScreenState
    {
        START_NEW, GAME_ONGOING
    }

    /**
     * Initializes the start screen, and the updates it depending on what the user does.
     * @return returns the view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visual_game, container, false);
        super.onCreate(savedInstanceState);
        screenState = ScreenState.START_NEW;

        visualMemoryVM = new ViewModelProvider(this).get(MemoryGameViewModel.class);
        visualMemoryVM.init();
        visualMemoryVM.getVisibility().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean _visibility) {
                visibility = _visibility;
                visualGameGridAdapter.setVisibility(_visibility);
                visualGameGridAdapter.notifyDataSetChanged();

            }
        });

        visualMemoryVM.getGameOver().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean gameOver) {
                if (gameOver)
                {
                    int levelQty = visualMemoryVM.getLevel();
                    if(levelQty >= 20)
                        showWonGame(levelQty);
                    else
                        showLostGame(levelQty);
                }
            }
        });
        visualMemoryVM.getGrid().observe(getViewLifecycleOwner(), new Observer<ArrayList<MemoryGrid.TileState>>() {
            @Override
            public void onChanged(ArrayList<MemoryGrid.TileState> grid) {
                visualGameGridAdapter = new MemoryGridAdapter(MemoryGameFragment.this, grid, lastPos);
                livesText.setText("You have " + visualMemoryVM.getLives() + " lives Remaining");
                visualGameGridAdapter.setVisibility(visibility);
                gridView.setAdapter(visualGameGridAdapter);
                gridView.setNumColumns(visualMemoryVM.getGridSize());
                gridView.setVerticalSpacing(20);
                gridView.setHorizontalSpacing(20);
            }
        });

        visualGameDescription = (TextView) view.findViewById(R.id.visualGameDescription);
        livesText = view.findViewById(R.id.livesText);
        livesText.setVisibility(View.INVISIBLE);
        visualGameDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (screenState == ScreenState.START_NEW)
                {
                    ClearScreen();
                    visualMemoryVM.startMemoryGame();
                    ShowBoard();
                    screenState = ScreenState.GAME_ONGOING;
                }
            }
        });

        gridView = (GridView) view.findViewById(R.id.visualGameGrid);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!visualMemoryVM.getVisibility().getValue())
                    visualMemoryVM.tileHasBeenClicked(position);
            }
        });

        return view;
    }

    /**
     * A method to clear the start screen to show the game screen
     */
    public void ClearScreen() {
        visualGameDescription.setText("");
    }

    /**
     * Shows the actual game screen
     */
    public void ShowBoard() {
        gridView.bringToFront();
        livesText.bringToFront();
        livesText.setVisibility(View.VISIBLE);
        livesText.setText("You have " + visualMemoryVM.getLives() + " lives Remaining");
        gridView.setNumColumns(visualMemoryVM.getGridSize());
        gridView.setVerticalSpacing(20);
        gridView.setHorizontalSpacing(20);
    }

    /**
     * When the game is lost this method is called to show the lost game screen
     * @param level is the score the user got
     */
    public void showLostGame (int level)
    {
        visualGameDescription.bringToFront();
        visualGameDescription.setText("Game over... Your score was: " + level + " \n \nPress to play again");
        screenState = ScreenState.START_NEW;
    }

    /**
     * When the game is won this method is called to show the won game screen
     * @param level is the score the user got
     */
    public void showWonGame (int level)
    {
        visualGameDescription.bringToFront();
        visualGameDescription.setText("Wow you completed the game! You got the max score of: " + level + " \n \nPress to play again");
        screenState = ScreenState.START_NEW;
    }

    /**
     * This method is called each time a tile han been clicked to notify the Viewmodel
     * @param position represents the position of the card that has been clicked
     */
    public void tileHasBeenClicked(int position) {
        lastPos = position;
        visualMemoryVM.tileHasBeenClicked(position);
    }
}