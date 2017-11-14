package com.onarollapp.onaroll.Perks;

import com.onarollapp.onaroll.Logic.GameState;
import com.onarollapp.onaroll.Logic.RollResult;

/**
 * Created by Curt on 11/12/2017.
 */

public abstract class Perk {

    public String id;

    public double oneModifier = 0;
    public double twoModifer = 0;
    public double threeModifer = 0;
    public double fourModifer = 0;
    public double fiveModifer = 0;
    public double sixModifer = 0;
    public double sevenModifer = 0;
    public double eightModifer = 0;

    public int dieModifier = 0;
    public abstract GameState onRoll(GameState gameState);

    // If a perk needs to store data based on the
    public Object[] data;



}
