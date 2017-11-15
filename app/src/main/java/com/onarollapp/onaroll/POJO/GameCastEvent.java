package com.onarollapp.onaroll.POJO;

import android.support.annotation.Nullable;

import com.onarollapp.onaroll.model.Game;


public class GameCastEvent {

    private final String error;

    private final Game game;

    public GameCastEvent(@Nullable String error, @Nullable Game game){
        this.error = error;
        this.game = game;
    }

    public String getError() {
        return error;
    }

    public Game getGame() {
        return game;
    }
}
