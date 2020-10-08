package com.example.graymatter.Model.Game.MemoryGame;

/**
 * Class to represent an event that the visual memory test model needs to account for.
 *
 * Holds information about the location of a selected tile.
 */
public class MemoryEvent {

    public int tileCoordinate;

    public MemoryEvent(int tileCoordinate){

        this.tileCoordinate = tileCoordinate;

    }
}
