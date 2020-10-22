package com.example.graymatter.View.Fragments.GameFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.graymatter.Model.Game.TowerOfHanoi.HanoiRodPosition;
import com.example.graymatter.Model.dataAccess.DataAccess;
import com.example.graymatter.R;
import com.example.graymatter.ViewModel.TowerOfHanoiViewModel;

import java.util.ArrayList;

/**
 * @author Viktor Felix
 * the class that represents the fragment for Chimp Game
 */
public class ToHFragment extends Fragment implements View.OnClickListener {
    private TextView toHDescription;
    private Button level3;
    private Button level4;
    private Button level5;
    private Button level6;
    private Button level7;
    private Button level8;
    private ImageView leftRod;
    private ImageView middleRod;
    private ImageView rightRod;
    private TowerOfHanoiViewModel toHVM;
    private ArrayList<ImageView> disks = new ArrayList<>();
    private ArrayList<FrameLayout> rodsFL = new ArrayList<>();

    private HanoiRodPosition fromRod;
    private HanoiRodPosition toRod;

    boolean firstClick = true;

    /**
     * Initializes the start screen, and the updates it depending on what the user does.
     * @return returns the view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_toh, container, false);
        super.onCreate(savedInstanceState);

        toHVM = new ViewModelProvider(this).get(TowerOfHanoiViewModel.class);
        toHDescription = view.findViewById(R.id.toHDescription);
        toHVM.init(new DataAccess(getContext()));

        leftRod = view.findViewById(R.id.leftRod);
        middleRod = view.findViewById(R.id.middleRod);
        rightRod = view.findViewById(R.id.rightRod);

        level3 = view.findViewById(R.id.three);
        level4 = view.findViewById(R.id.four);
        level5 = view.findViewById(R.id.five);
        level6 = view.findViewById(R.id.six);
        level7 = view.findViewById(R.id.seven);
        level8 = view.findViewById(R.id.eight);

        showLevels();

        level3.setOnClickListener(this);
        level4.setOnClickListener(this);
        level5.setOnClickListener(this);
        level6.setOnClickListener(this);
        level7.setOnClickListener(this);
        level8.setOnClickListener(this);

        toHVM.getBoard().observe(getViewLifecycleOwner(), new Observer<ArrayList<ArrayList<Integer>>>() {
            @Override
            public void onChanged(ArrayList<ArrayList<Integer>> board) {
                drawDisks(board);
            }
        });
        toHVM.getGameOver().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (toHVM.getBoard().getValue() != null)
                    drawDisks(toHVM.getBoard().getValue());
                showWonGame(toHVM.getScore());
            }
        });
        leftRod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rodClick(HanoiRodPosition.LEFT);
            }
        });
        middleRod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rodClick(HanoiRodPosition.MIDDLE);
            }
        });
        rightRod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rodClick(HanoiRodPosition.RIGHT);
            }
        });

        rodsFL.add((FrameLayout) view.findViewById(R.id.leftRodFL));
        rodsFL.add((FrameLayout) view.findViewById(R.id.middleRodFL));
        rodsFL.add((FrameLayout) view.findViewById(R.id.rightRodFL));

        for (int i = 0; i < logos.length; i++) {
            ImageView view1 = new ImageView(rodsFL.get(0).getContext());
            view1.setImageResource(logos[i]);
            disks.add(view1);
        }

        return view;
    }

    /**
     * /**
     * This method is called each time a rod has been clicked to either get the rod to move from,
     * or the rod to move to
     * @param hanoiRodPosition is the rod that hac been clicked
     */
    public void rodClick(HanoiRodPosition hanoiRodPosition) {
        if (firstClick) {
            fromRod = hanoiRodPosition;
            firstClick = false;
        } else {
            toRod = hanoiRodPosition;
            diskMove(fromRod, toRod);

            firstClick = true;
        }
    }

    /**
     * OnClick method for the level select
     * @param v is the parameter id to determine which view that has been clicked
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.three:
                toHVM.setLevel(3);
                break;
            case R.id.four:
                toHVM.setLevel(4);
                break;
            case R.id.five:
                toHVM.setLevel(5);
                break;
            case R.id.six:
                toHVM.setLevel(6);
                break;
            case R.id.seven:
                toHVM.setLevel(7);
                break;
            case R.id.eight:
                toHVM.setLevel(8);
                break;
        }

        clearStartScreen();
        showGameScreen();
        toHVM.startToHGame();
    }

    /**
     * Showing the level select menu
     */
    public void showLevels ()
    {
        toHDescription.setText("Welcome to Tower of Hanoi, select a level to play");

        removeViews();

        level3.setVisibility(View.VISIBLE);
        level4.setVisibility(View.VISIBLE);
        level5.setVisibility(View.VISIBLE);
        level6.setVisibility(View.VISIBLE);
        level7.setVisibility(View.VISIBLE);
        level8.setVisibility(View.VISIBLE);

        leftRod.setVisibility(View.GONE);
        middleRod.setVisibility(View.GONE);
        rightRod.setVisibility(View.GONE);
    }

    /**
     * clears the screen from the level select buttons to show the game
     */
    public void clearStartScreen() {
        toHDescription.setText("");

        level3.setVisibility(View.GONE);
        level4.setVisibility(View.GONE);
        level5.setVisibility(View.GONE);
        level6.setVisibility(View.GONE);
        level7.setVisibility(View.GONE);
        level8.setVisibility(View.GONE);
    }

    /**
     * Displays the rods
     */
    public void showGameScreen() {
        leftRod.setVisibility(View.VISIBLE);
        middleRod.setVisibility(View.VISIBLE);
        rightRod.setVisibility(View.VISIBLE);
    }

    /**
     * When the game is won this method is called to show the won game screen
     * @param score is the score the user got
     */
    public void showWonGame(int score) {
        toHDescription.bringToFront();
        toHDescription.setText("Wow you completed the game! Your score was: "
                + score + " \n \nPress to play again");
        toHDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLevels();
            }
        });
    }

    /**
     * draws the disk on the right position on the rods
     * @param board is the board that should be drawn
     */
    private void drawDisks(ArrayList<ArrayList<Integer>> board) {
        removeViews();
        int rodWidth = 284;
        for (int i = 0; i < board.size(); i++) {
            int ch = 0;
            for (int j = 0; j < board.get(i).size(); j++) {
                int pos = 285;
                int width = 60 + 20*(board.get(i).get(j)-1);
                int height = 60 + 20*(board.get(i).get(j)-1);
                int fixedHeight = 25;
                ch += fixedHeight;
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams
                        (width, height);
                layoutParams.setMargins((rodWidth -width)/2, pos -ch -height/2 -fixedHeight/2, 0, 0);

                disks.get(board.get(i).get(j) - 1).setLayoutParams(layoutParams);

                rodsFL.get(i).addView(disks.get(board.get(i).get(j) - 1));
            }
        }
    }

    /**
     * removes the previous views
     */
    public void removeViews ()
    {
        for (int i = 0; i < disks.size(); i++) {
            if (disks.get(i).getParent() != null)
                ((ViewGroup) disks.get(i).getParent()).removeView(disks.get(i));
        }
    }

    /**
     * Communicates with the viewmodel about where the disk should move from, and to where
     * @param from from which rod to move
     * @param to to which rod to move
     */
    public void diskMove(HanoiRodPosition from, HanoiRodPosition to) {
        toHVM.tileHasBeenClicked(from, to);
    }

    int[] logos = {R.mipmap.ic_toh_disk1_foreground,
            R.mipmap.ic_toh_disk2_foreground,
            R.mipmap.ic_toh_disk3_foreground,
            R.mipmap.ic_toh_disk4_foreground,
            R.mipmap.ic_toh_disk5_foreground,
            R.mipmap.ic_toh_disk6_foreground,
            R.mipmap.ic_toh_disk7_foreground,
            R.mipmap.ic_toh_disk8_foreground};
}
