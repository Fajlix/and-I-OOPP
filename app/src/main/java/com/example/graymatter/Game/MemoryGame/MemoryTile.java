package com.example.graymatter.Game.MemoryGame;

public class MemoryTile {


    final public Boolean correct;
    private boolean chosen = false;

    public MemoryTile(boolean correct){
        this.correct = correct;
    }

    public MemoryTile(MemoryTile memorytile){

        this.correct = memorytile.correct;
        this.chosen = memorytile.chosen;
    }

    public void choose(){
        chosen = true;
    }

    public boolean isChosen(){
        boolean chosenCopy = chosen;
        return chosenCopy;
    }

    public boolean isCorrect(){
        boolean correctCopy = correct;
        return correctCopy;
    }

}
