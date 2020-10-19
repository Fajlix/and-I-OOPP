package com.example.graymatter.Model.Game.TowerOfHanoi;


import java.util.ArrayList;

/**
 * The entire "board" for the Tower of Hanoi game, i.e three rods which hold disks
 */
public class HanoiBoard {

    private HanoiRod left;
    private HanoiRod middle;
    private HanoiRod right;
    private boolean won = false;

    public HanoiBoard(int level){
        left = new HanoiRod();
        middle = new HanoiRod();
        right = new HanoiRod();

        for(int i = level ; i > 0 ; i--){
            left.place(new HanoiDisk(i));
        }
    }

    /**
     * Method that handles moving disks between rods. returns true if move was successful, otherwise false.
     * move is unsuccessful if the moved disk is larger than the top disk on the rod it is being moved to.
     * @param fromEnum an enum value corresponding to which rod the disk should be moved from (left, right, middle).
     * @param toEnum an enum value corresponding to which rod the disk should be moved to (left, right, middle).
     */
    public boolean moveDisk(HanoiRodPosition fromEnum, HanoiRodPosition toEnum) {
        if (won) throw new RuntimeException("Attempt to move disks after game won");
        HanoiRod from;
        HanoiRod to;
        switch (fromEnum){
            case LEFT:
                from = left;
                break;
            case MIDDLE:
                from = middle;
                break;
            case RIGHT:
                from = right;
                break;
            default:
                throw new IllegalArgumentException("invalid method input");
        }
        switch (toEnum){
            case LEFT:
                to = left;
                break;
            case MIDDLE:
                to = middle;
                break;
            case RIGHT:
                to = right;
                break;
            default:
                throw new IllegalArgumentException("Invalid method input");
        }
        HanoiDisk toMove = from.grab();
        if (!to.place(toMove)){
            from.place(toMove);
            return false;
        }
        if (left.isEmpty() && middle.isEmpty()){
            won = true;
        }
        return true;
    }

    public boolean isWon(){
        return won;
    }

    /**
     * Makes a list of three lists, each of which represents the state of a rod. (Which disks it holds)
     * @return An arrayList of length 3, containing lists of integers.
     */
    public ArrayList<ArrayList<Integer>> getState(){
        ArrayList<ArrayList<Integer>> state = new ArrayList<>();

        state.add(left.getSizeList());
        state.add(middle.getSizeList());
        state.add(right.getSizeList());

        return state;
    }



}
