package com.onarollapp.onaroll.Perks;

import com.onarollapp.onaroll.Logic.GameState;

/**
 * Created by Curt on 11/12/2017.
 */

public class RollOneExtraDie extends Perk{

    public RollOneExtraDie() {
        this.id = "7";
        this.dieModifier = 1; // This perk gives the User an increased chance to roll a 1
        this.title = "Extra Die";
    }

    public GameState onRoll(GameState gameState) {
        return gameState;
    }
}
