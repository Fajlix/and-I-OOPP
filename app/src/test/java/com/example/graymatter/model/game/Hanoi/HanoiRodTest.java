package com.example.graymatter.model.game.Hanoi;

import com.example.graymatter.model.game.towerOfHanoi.HanoiDisk;
import com.example.graymatter.model.game.towerOfHanoi.HanoiRod;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class HanoiRodTest {
    HanoiRod rod;



    @Test
    public void rodOrder(){
        rod = new HanoiRod();

        rod.place(new HanoiDisk(2));
        rod.place(new HanoiDisk(1));

        ArrayList<Integer> list = rod.getSizeList();

        assertTrue(list.get(0)>list.get(1));

    }

}
