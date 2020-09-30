package com.example.graymatter.ViewModel;

import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;

import com.example.graymatter.Model.Game.ChimpGame.ChimpGame;
import com.example.graymatter.R;

import org.greenrobot.eventbus.EventBus;

public class ChimpGameViewModel extends ViewModel {
    private ChimpGame chimpGame;
    private int score = 0;

    public void init(){
        chimpGame = new ChimpGame();
    }
    public void startChimpGame(){
        chimpGame.startGame();
    }
    public int[] getGrid(){
        return chimpGame.getBoard();
    }
    public void tileHasBeenClicked(View view){
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

}
