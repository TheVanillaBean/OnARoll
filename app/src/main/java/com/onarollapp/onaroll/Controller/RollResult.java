package com.onarollapp.onaroll.Controller;

/**
 * Created by Curt on 11/12/2017.
 */

public class RollResult {
    public int dieOne;
    public int dieTwo;
    public int dieThree;

    public RollResult(int dieOne, int dieTwo) {
        this.dieOne = dieOne;
        this.dieTwo = dieTwo;
        this.dieThree = 0;
    }

    public RollResult(int dieOne, int dieTwo, int dieThree) {
        this.dieOne = dieOne;
        this.dieTwo = dieTwo;
        this.dieThree = dieThree;
    }

}
