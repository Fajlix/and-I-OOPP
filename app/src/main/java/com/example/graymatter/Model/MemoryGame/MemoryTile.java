package com.example.graymatter.Model.MemoryGame;

public class MemoryTile {

    /*
    *
    *  Class represents a single tile in the visual memory game's grid.
    *
     */

    final public Boolean correct;
    private boolean chosen = false;

    public MemoryTile(boolean correct){
        this.correct = correct;
    }

    public MemoryTile(MemoryTile memorytile){

        this.correct = memorytile.correct;
        this.chosen = memorytile.chosen;
    }

    public void markChosen(){
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