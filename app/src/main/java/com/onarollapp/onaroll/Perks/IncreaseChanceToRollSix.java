package com.onarollapp.onaroll.Perks;

import com.onarollapp.onaroll.Logic.GameState;

/**
 * Created by Curt on 11/12/2017.
 */

public class IncreaseChanceToRollSix extends Perk{

    public IncreaseChanceToRollSix() {
        this.id = "6";
        this.oneModifier = 0.01; // This perk gives the User an increased chance to roll a 1
        this.title = "Six Die Increase";
        this.description = "Increases your chance to roll a 6 on each die.";
    }

    public GameState onRoll(GameState gameState) {
        return gameState;
    }
}
