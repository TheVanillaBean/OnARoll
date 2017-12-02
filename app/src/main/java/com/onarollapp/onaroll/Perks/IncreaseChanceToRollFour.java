package com.onarollapp.onaroll.Perks;

import com.onarollapp.onaroll.Logic.GameState;

/**
 * Created by Curt on 11/12/2017.
 */

public class IncreaseChanceToRollFour extends Perk{

    public IncreaseChanceToRollFour() {
        this.id = "4";
        this.oneModifier = 0.01; // This perk gives the User an increased chance to roll a 1
        this.title = "Four Die Increase";
        this.description = "Increases your chance to roll a 4 on each die.";
    }

    public GameState onRoll(GameState gameState) {
        return gameState;
    }
}
