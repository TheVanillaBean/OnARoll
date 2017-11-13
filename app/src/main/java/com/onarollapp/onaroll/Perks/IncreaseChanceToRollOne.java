package com.onarollapp.onaroll.Perks;

import com.onarollapp.onaroll.Controller.RollResult;

/**
 * Created by Curt on 11/12/2017.
 */

public class IncreaseChanceToRollOne extends Perk{

    IncreaseChanceToRollOne() {
        this.oneModifier = 0.01; // This perk gives the User an increased chance to roll a 1
    }

    public RollResult onRoll(RollResult roll) {
        return roll;
    }
}
