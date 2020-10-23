package com.example.graymatter.model.game.memoryGame;


public class MemoryTile {
    /*
    *
    *  Class represents a single tile in the visual memory game's grid.
    *
     */

    final public Boolean correct; //Describes whether the player should select this tile or not
    private boolean chosen = false;

    public MemoryTile(boolean correct){
        this.correct = correct;
    }

    /**
     * Creates a memoryTile with attributes copied from an existing MemoryTile
     * @param memorytile the tile to copy
     */
    public MemoryTile(MemoryTile memorytile){
        this.correct = memorytile.correct;
        this.chosen = memorytile.chosen;
    }

    public void markChosen(){
        chosen = true;
    }

    public boolean isChosen(){
        return chosen;
    }

    public boolean tileEquals(MemoryTile other){
        return (this.correct == other.correct && this.chosen == other.chosen);
    }

}
