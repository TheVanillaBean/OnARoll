package com.onarollapp.onaroll.Logic;

import java.util.ArrayList;

/**
 * Created by Curt on 11/12/2017.
 */

public class RollResult {
    public ArrayList<Integer> die;

    public RollResult(int dieOne, int dieTwo) {
        die.add(dieOne);
        die.add(dieTwo);
    }

    public RollResult(int dieOne, int dieTwo, int dieThree) {
        die.add(dieOne);
        die.add(dieTwo);
        die.add(dieThree);
    }

    public RollResult(ArrayList<Integer> results) {
        this.die = results;
    }

    public int getRollTotal() {
        int total = 0;
        for(int i = 0; i < die.size(); i++) {
            total = total + die.get(i);
        }
        return total;
    }

    public boolean rollContains(int value) {
        for(int i = 0; i < die.size(); i++) {
            if(die.get(i) == value) {
                return true;
            }
        }
        return false;
    }

}
