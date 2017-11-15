package com.onarollapp.onaroll.Perks;

import com.onarollapp.onaroll.Logic.GameState;

/**
 * Created by Curt on 11/14/2017.
 */

public class EmptyPerk extends Perk {

    public EmptyPerk() {
        this.title = "Empty Perk";
        this.description = "";
        this.id = "2";
    }

    @Override
    public GameState onRoll(GameState gameState) {
        return null;
    }
}
