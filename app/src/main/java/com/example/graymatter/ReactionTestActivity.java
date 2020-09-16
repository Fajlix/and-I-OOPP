package com.example.graymatter;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.w3c.dom.Text;

public class ReactionTestActivity extends AppCompatActivity {
    private ReactionTime reactionTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reaction_test);
        reactionTime = new ReactionTime();
        final ReactionTestActivity rTA = this;

        final TextView reactionTestDescription = (TextView) findViewById(R.id.reactionTestDescription);
        reactionTestDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reactionTime.StartGame(rTA);
                //ändra färg till väntafärg
            }
        });

        ImageView reactionTestClose = (ImageView) findViewById(R.id.reactionTestClose);
    }
    public void setColorNow(){
        final TextView reactionTestDescription = (TextView) findViewById(R.id.reactionTestDescription);
        reactionTestDescription.setTextColor(0xff000000);
        reactionTestDescription.setText("NOW");
        reactionTestDescription.setBackgroundColor(0xffffffff);
    }


}
