package com.example.graymatter.Game.MemoryGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

public class MemoryGrid {

    public enum Status{
        ACTIVE, WON, LOST
    }

    private Vector<Vector<MemoryTile>> grid;
    private int tries = 4;
    private Status status = Status.ACTIVE;
    private int correctTilesRemaining;
    private int size;

    public MemoryGrid(int level) {
        if (level > 30) throw new RuntimeException("Illegal superhuman visual memory");

        size = size(level);
        correctTilesRemaining = level+2;

        grid = new Vector<>();

        ArrayList<MemoryTile> sequence = tileSequence(size, correctTilesRemaining);

        for (int i=0 ; i<size ; i++){
            Vector<MemoryTile> row = new Vector<>();
            for ( int j = 0 ; j<size ; j++){
                row.add(sequence.get(i*size+j));
            }
            grid.add(row);
        }
    }

    public void choose(int tileCoordinate){

        // Method translates one dimensional tile coordinate to two dimensional coordinates
        int x = tileCoordinate / size;
        if (x >= size){
            throw new RuntimeException("Grid access input out of bounds");
        }
        int y = tileCoordinate % size;

        MemoryTile tile = grid.get(x).get(y);

        if (!tile.isChosen()){
            if (tile.isCorrect()){
                correctTilesRemaining -= 1;
                visibility = false;
                if (correctTilesRemaining == 0){ // Chosen tile was the last correct tile
                    status = Status.WON;
                }
            } else {
                tries -= 1;
                if (tries == 0){ // Player has chosen four incorrect tiles
                    status = Status.LOST;
                }
            }
        }

        tile.markChosen();
    }

    public MemoryTile get(int x, int y){

        return new MemoryTile(grid.get(x).get(y));
    }

    private int size(int level){

        switch(level){ // decides the size of (one side of) the square grid based on level
            case 1: case 2:
                return 3;

            case 3: case 4: case 5:
                return 4;

            case 6: case 7: case 8:
                return 5;

            case 9: case 10: case 11: case 12: case 13:
                return 6;

            case 14: case 15: case 16: case 17: case 18:
                return 7;

            case 19: case 20: case 21: case 22: case 23:
                return 8;

            default:
                return 9;
        }
    }

    private ArrayList<MemoryTile> tileSequence(int size, int correctQty){

        ArrayList<MemoryTile> sequence = new ArrayList<>();

        for (int i = 0; i < correctQty; i++){
            sequence.add(new MemoryTile(true));
        }
        for (int i = correctQty; i < size*size ; i++) {
            sequence.add(new MemoryTile(false));
        }

        Collections.shuffle(sequence);

        return sequence;
    } // Creates a random sequence of memory tiles for the grid

    public Status getStatus(){
        Status statusCopy = status;
        return statusCopy;
    }


    public enum TileState {
        CORRECTHIDDEN, CORRECTCHOSEN, INCORRECTHIDDEN, INCORRECTCHOSEN
    }

    public TileState getTileState(int tileCoordinate){
        int x = tileCoordinate / size;
        if (x >= size){
            throw new RuntimeException("Grid access input out of bounds");
        }
        int y = tileCoordinate % size;

        MemoryTile tile = this.get(x,y);
        return getState(tile);
    }

    public ArrayList<TileState> toArrayList(){

        ArrayList<TileState> gridArrayList = new ArrayList();

        for (Vector<MemoryTile> x : grid) {
            for (MemoryTile y : x) {
                gridArrayList.add(getState(y));
            }
        }

        return gridArrayList;
    }

    private TileState getState(MemoryTile tile) {

        if ( tile.isCorrect() ){
            if (tile.isChosen()){
                return TileState.CORRECTCHOSEN;
            } else {
                return TileState.CORRECTHIDDEN;
            }
        } else {
            if (tile.isChosen()){
                return TileState.INCORRECTCHOSEN;
            } else {
                return TileState.INCORRECTHIDDEN;
            }
        }

    }
}
