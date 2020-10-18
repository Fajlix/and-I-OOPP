package com.example.graymatter.View.Fragments.GameFragments;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.graymatter.Model.Game.TowerOfHanoi.HanoiRodPosition;
import com.example.graymatter.R;
import com.example.graymatter.ViewModel.TowerOfHanoiViewModel;

import java.util.ArrayList;

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
    private FrameLayout leftRodFL;
    private FrameLayout middleRodFL;
    private FrameLayout rightRodFL;

    private HanoiRodPosition fromRod;
    private HanoiRodPosition toRod;

    float x;
    float xRatio, yRatio;
    boolean isValidTouch = true;
    float bottomLimit, topLimit, leftLimitMiddleRod, rightLimitMiddleRod;

    private int numberOfDisks;
    private int disksOnRightRod = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_toh, container, false);
        super.onCreate(savedInstanceState);

        toHVM = new ViewModelProvider(this).get(TowerOfHanoiViewModel.class);
        toHDescription = view.findViewById(R.id.toHDescription);
        toHVM.init();

        leftRod = view.findViewById(R.id.leftRod);
        middleRod = view.findViewById(R.id.middleRod);
        rightRod = view.findViewById(R.id.rightRod);

        leftRod.setVisibility(View.GONE);
        middleRod.setVisibility(View.GONE);
        rightRod.setVisibility(View.GONE);

        level3 = view.findViewById(R.id.three);
        level4 = view.findViewById(R.id.four);
        level5 = view.findViewById(R.id.five);
        level6 = view.findViewById(R.id.six);
        level7 = view.findViewById(R.id.seven);
        level8 = view.findViewById(R.id.eight);

        level3.setOnClickListener(this);
        level4.setOnClickListener(this);
        level5.setOnClickListener(this);
        level6.setOnClickListener(this);
        level7.setOnClickListener(this);
        level8.setOnClickListener(this);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        xRatio = displayMetrics.widthPixels / 480;

        bottomLimit = 20 * yRatio;
        topLimit = 250 * yRatio;
        leftLimitMiddleRod = 165 * xRatio;
        rightLimitMiddleRod = 315 * xRatio;

        leftRod.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                onTouchEvent(event);
                return false;
            }
        });
        middleRod.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                onTouchEvent(event);
                return false;
            }
        });
        rightRod.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                onTouchEvent(event);
                return false;
            }
        });


        leftRodFL = view.findViewById(R.id.leftRodFL);

        for (int i = 0; i < logos.length; i++) {
            ImageView view1 = new ImageView(leftRodFL.getContext());
            view1.setImageResource(logos[i]);
            disks.add(view1);
        }

        return view;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            x = event.getX();

            if (x < leftLimitMiddleRod)
                fromRod = HanoiRodPosition.LEFT;
            else if (x >= leftLimitMiddleRod && x <= rightLimitMiddleRod)
                fromRod = HanoiRodPosition.MIDDLE;
            else
                fromRod = HanoiRodPosition.RIGHT;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            x = event.getX();
            if (x < leftLimitMiddleRod) {
                diskMove(fromRod, HanoiRodPosition.LEFT);
            } else if (x >= leftLimitMiddleRod
                    && x <= rightLimitMiddleRod) {
                diskMove(fromRod, HanoiRodPosition.MIDDLE);
            } else
                diskMove(fromRod, HanoiRodPosition.RIGHT);
        }
        return true;
    }

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
        drawStartDisks(toHVM.getLevel());
        toHVM.startToHGame();

        if (toHVM.getGameOver().getValue()) {
            showWonGame(toHVM.getScore());
        }
    }

    // clears the screen of all the text and images to show the test
    public void clearStartScreen() {
        toHDescription.setText("");

        level3.setVisibility(View.GONE);
        level4.setVisibility(View.GONE);
        level5.setVisibility(View.GONE);
        level6.setVisibility(View.GONE);
        level7.setVisibility(View.GONE);
        level8.setVisibility(View.GONE);
    }

    public void showGameScreen() {
        leftRod.setVisibility(View.VISIBLE);
        middleRod.setVisibility(View.VISIBLE);
        rightRod.setVisibility(View.VISIBLE);
        // show disks on left rod
    }

    public void showWonGame(int score) {
        toHDescription.bringToFront();
        toHDescription.setText("Wow you completed the game! Your score was: "
                + score + " \n \nPress to play again");
    }

    private void drawStartDisks(int numberOfDisks) {
        for (int i = numberOfDisks - 1; i >= 0; i--) {
            int pos = 145 - 20 * (numberOfDisks - i);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(50 + 20 * i, 50 + 20 * i);
            layoutParams.setMargins(118 - 10 * i, pos, 0, 0);

            disks.get(i).setLayoutParams(layoutParams);

            leftRodFL.addView(disks.get(i));
        }
    }

    public void diskMove(HanoiRodPosition from, HanoiRodPosition to) {
        toHVM.tileHasBeenClicked(from, to);
    }

    // on touch -> current = from
    // on letgo -> current = to

    int[] logos = {R.mipmap.ic_toh_disk1_foreground,
            R.mipmap.ic_toh_disk2_foreground,
            R.mipmap.ic_toh_disk3_foreground,
            R.mipmap.ic_toh_disk4_foreground,
            R.mipmap.ic_toh_disk5_foreground,
            R.mipmap.ic_toh_disk6_foreground,
            R.mipmap.ic_toh_disk7_foreground,
            R.mipmap.ic_toh_disk8_foreground};
}
