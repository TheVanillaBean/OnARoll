package com.onarollapp.onaroll.Perks;

import com.onarollapp.onaroll.Logic.GameState;

/**
 * Created by Curt on 11/12/2017.
 */

public class IncreaseChanceToRollTwo extends Perk{

    public IncreaseChanceToRollTwo() {
        this.id = "2";
        this.twoModifer = 0.01; // This perk gives the User an increased chance to roll a 1
        this.title = "Two Die Increase";
        this.description = "Increases your chance to roll a 2 on each die.";
    }

    public GameState onRoll(GameState gameState) {
        return gameState;
    }
}
