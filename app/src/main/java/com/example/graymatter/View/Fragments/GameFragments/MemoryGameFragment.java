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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.graymatter.Model.Game.MemoryGame.MemoryGrid;
import com.example.graymatter.R;
import com.example.graymatter.View.Adapters.MemoryGridAdapter;
import com.example.graymatter.ViewModel.MemoryGameViewModel;

import java.util.ArrayList;

public class MemoryGameFragment extends Fragment {
    private GridView gridView;
    private MemoryGridAdapter visualGameGridAdapter;
    private TextView visualGameDescription;
    private TextView livesText;
    private ImageView visualGameClose;
    private MemoryGameViewModel visualMemoryVM;
    private boolean visibility = true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visual_game, container, false);
        super.onCreate(savedInstanceState);
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
                livesText.setText("You have " + visualMemoryVM.getLives() + " lives Remaining");
                visualGameGridAdapter = new MemoryGridAdapter(grid);
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
                ClearScreen();
                visualMemoryVM.startMemoryGame();
                ShowBoard();

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

        // clicking on this should take the user to the main page
        //ImageView visualGameClose = (ImageView) view.findViewById(R.id.visualGameClose);

        return view;
    }

    public void ClearScreen() {
        visualGameDescription.setText("");
    }

    public void ShowBoard() {
        gridView.bringToFront();
        livesText.bringToFront();
        livesText.setVisibility(View.VISIBLE);
        livesText.setText("You have " + visualMemoryVM.getLives() + " lives Remaining");
        gridView.setNumColumns(visualMemoryVM.getGridSize());
        gridView.setVerticalSpacing(20);
        gridView.setHorizontalSpacing(20);
    }


    public void showLostGame (int level)
    {
        visualGameDescription.bringToFront();
        //visualGameClose.bringToFront();
        visualGameDescription.setText("Game over... Your score was: " + level + " \n \nPress to play again");
    }

    public void showWonGame (int level)
    {
        visualGameDescription.bringToFront();
        //visualGameClose.bringToFront();
        visualGameDescription.setText("Wow you completed the game! You got the max score of: " + level + " \n \nPress to play again");
    }
}