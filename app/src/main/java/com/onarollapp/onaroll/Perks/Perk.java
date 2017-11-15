package com.onarollapp.onaroll.Perks;

import com.onarollapp.onaroll.Logic.GameState;

/**
 * Created by Curt on 11/12/2017.
 */

public abstract class Perk {

    public String id;
    public String title;
    public String description;

    public double oneModifier = 0;
    public double twoModifer = 0;
    public double threeModifer = 0;
    public double fourModifer = 0;
    public double fiveModifer = 0;
    public double sixModifer = 0;
    public double sevenModifer = 0;
    public double eightModifer = 0;

    public int dieModifier = 0;
    // If a perk needs to store data based on the
    public Object[] data;

    public abstract GameState onRoll(GameState gameState);

    public String toString() {
        return this.title;
    }


}
