package com.onarollapp.onaroll.Perks;

import com.onarollapp.onaroll.Logic.GameState;
import com.onarollapp.onaroll.Logic.RollResult;

/**
 * Created by Curt on 11/12/2017.
 */

public class IncreaseChanceToRollOne extends Perk{

    public IncreaseChanceToRollOne() {
        this.id = "1";
        this.oneModifier = 0.01; // This perk gives the User an increased chance to roll a 1
        this.title = "One Die Increase";
        this.description = "Increases your chance to roll a 1 on each die.";
    }

    public GameState onRoll(GameState gameState) {
        return gameState;
    }
}
