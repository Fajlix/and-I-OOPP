package com.example.graymatter.model.game.towerOfHanoi;

import java.util.ArrayList;
import java.util.Stack;

/**
 * @author Hugo
 * One rod in the Tower of Hanoi game
 */
public class HanoiRod {

    private Stack<HanoiDisk> disks = new Stack<>();



    public HanoiDisk grab(){
        if (disks.empty())
            return null;
        return disks.pop();
    }


    public boolean place(HanoiDisk disk){
        if (disks.isEmpty() || disk.size < disks.peek().size){
            disks.push(disk);
            return true;
        }
        else return false;
    }

    /**
     * Makes a list of the sizes of the disks on the rod, ordered from largest to smallest.
     * @return a list of integers representing the sizes of the disks on the rod, ordered from largest to smallest.
     */
    public ArrayList<Integer> getSizeList(){
        ArrayList<Integer> list = new ArrayList<>();

        for (HanoiDisk disk : disks){
            list.add(disk.size);
        }

        return list;
    }

    public boolean isEmpty(){
        return disks.isEmpty();
    }


}
