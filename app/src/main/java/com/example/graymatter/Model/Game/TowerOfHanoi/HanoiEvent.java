package com.example.graymatter.Model.Game.TowerOfHanoi;

public class HanoiEvent {

    HanoiRodPosition moveFrom;
    HanoiRodPosition moveTo;

    public HanoiEvent(HanoiRodPosition moveFrom, HanoiRodPosition moveTo){
        this.moveFrom = moveFrom;
        this.moveTo = moveTo;
        
    }

}

