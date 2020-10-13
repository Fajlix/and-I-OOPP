package com.example.graymatter.Model.Game.Hanoi;

import com.example.graymatter.Model.Game.TowerOfHanoi.HanoiDisk;
import com.example.graymatter.Model.Game.TowerOfHanoi.HanoiRod;

import org.junit.Before;
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
