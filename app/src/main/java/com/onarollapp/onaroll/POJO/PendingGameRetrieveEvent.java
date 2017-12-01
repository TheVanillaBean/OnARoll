package com.onarollapp.onaroll.POJO;

import android.support.annotation.Nullable;

import com.onarollapp.onaroll.model.Game;


public class PendingGameRetrieveEvent {

    private final String error;
    private final Game game;

    public PendingGameRetrieveEvent(Game game, @Nullable String error){
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
