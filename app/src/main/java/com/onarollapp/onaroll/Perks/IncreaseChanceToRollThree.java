package com.onarollapp.onaroll.Perks;

import com.onarollapp.onaroll.Logic.GameState;

/**
 * Created by Curt on 11/12/2017.
 */

public class IncreaseChanceToRollThree extends Perk{

    public IncreaseChanceToRollThree() {
        this.id = "3";
        this.threeModifer = 0.01; // This perk gives the User an increased chance to roll a 1
        this.title = "Three Die Increase";
    }

    public GameState onRoll(GameState gameState) {
        return gameState;
    }
}
