package com.onarollapp.onaroll.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.onarollapp.onaroll.DataService.FBDataService;
import com.onarollapp.onaroll.POJO.GameCastEvent;
import com.onarollapp.onaroll.util.Constants;

import org.greenrobot.eventbus.EventBus;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Parcel /* Variables are not private because of the Parcel Dependency - Reflection */
@IgnoreExtraProperties
public class Game {

    String uuid;
    String playerOneID;
    String playerTwoID;
    int playerOneScore;
    int playerTwoScore;
    String turn;


    public String getPlayerOneID() {
        return (playerOneID == null) ? "" : playerOneID;
    }

    public void setPlayerOneID(String id) {
        this.playerOneID = id;
    }

    public String getPlayerTwoID() {
        return (playerTwoID == null) ? "" : playerTwoID;
    }

    public void setPlayerTwoID(String id) {
        this.playerTwoID = id;
    }

    public String getUuid() {
        return (uuid == null) ? "" : uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getPlayerOneScore() {
        return playerOneScore;
    }

    public void setPlayerOneScore(int score) {
        this.playerOneScore = score;
    }

    public int getPlayerTwoScore() {
        return playerTwoScore;
    }

    public void setPlayerTwoScore(int score) {
        this.playerTwoScore = score;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }

    public String getTurn() {
        return (turn == null) ? "" : turn;
    }

    public Game() {
    }


    public Game(String uuid, String playerOneID, String turn) {
        this.uuid = uuid;
        this.playerOneID = playerOneID;
        this.turn = turn;
        this.playerOneScore = 0;
        this.playerTwoScore = 0;
    }

    @Exclude
    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put(Constants.UUID, getUuid());
        result.put(Constants.PLAYER_ONE_ID, getPlayerOneID());
        result.put(Constants.PLAYER_TWO_ID, getPlayerTwoID());
        result.put(Constants.PLAYER_ONE_SCORE, getPlayerOneScore());
        result.put(Constants.PLAYER_TWO_SCORE, getPlayerTwoScore());
        result.put(Constants.TURN, getTurn());
        return result;
    }

    public static void castGame(String uuid) {

        FBDataService.getInstance().gamesRef().child(uuid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (!dataSnapshot.exists()) return;

                Game game = dataSnapshot.getValue(Game.class);

                EventBus.getDefault().post(new GameCastEvent(null, game));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                EventBus.getDefault().post(new GameCastEvent(databaseError.getMessage(), null));
            }
        });

    }
}
