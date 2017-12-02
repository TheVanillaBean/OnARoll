package com.onarollapp.onaroll.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;
import com.onarollapp.onaroll.DataService.FBDataService;
import com.onarollapp.onaroll.POJO.GameCastEvent;
import com.onarollapp.onaroll.util.Constants;

import org.greenrobot.eventbus.EventBus;
import org.parceler.Parcel;

import java.util.HashMap;
import java.util.Map;


@Parcel /* Variables are not private because of the Parcel Dependency - Reflection */
@IgnoreExtraProperties
public class Game {

    String uuid;
    String creatorID;
    String joinerID;
    String creatorName;
    String joinerName;
    int creatorScore;
    int joinerScore;
    String turn;
    String state;

    public String getCreatorID() {
        return (creatorID == null) ? "" : creatorID;
    }

    public void setCreatorID(String creatorID) {
        this.creatorID = creatorID;
    }

    public String getJoinerID() {
        return (joinerID == null) ? "" : joinerID;
    }

    public void setJoinerID(String id) {
        this.joinerID = id;
    }

    public String getUuid() {
        return (uuid == null) ? "" : uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getCreatorScore() {
        return creatorScore;
    }

    public void setCreatorScore(int score) {
        this.creatorScore = score;
    }

    public int getJoinerScore() {
        return joinerScore;
    }

    public void setJoinerScore(int score) {
        this.joinerScore = score;
    }

    public String getCreatorName() {
        return (creatorName == null) ? "" : creatorName;     }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getJoinerName() {
        return (joinerName == null) ? "" : joinerName;     }

    public void setJoinerName(String joinerName) {
        this.joinerName = joinerName;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }

    public String getTurn() {
        return (turn == null) ? "" : turn;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Game() {
    }


    public Game(String uuid, String creatorID, String creatorName, String turn, String state) {
        this.uuid = uuid;
        this.creatorID = creatorID;
        this.creatorName = creatorName;
        this.turn = turn;
        this.creatorScore = 0;
        this.joinerScore = 0;
        this.state = state;
    }

    @Exclude
    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put(Constants.UUID, getUuid());
        result.put(Constants.CREATOR_ID, getCreatorID());
        result.put(Constants.JOINER_ID, getJoinerID());
        result.put(Constants.CREATOR_SCORE, getCreatorScore());
        result.put(Constants.JOINER_SCORE, getJoinerScore());
        result.put(Constants.CREATOR_NAME, getCreatorName());
        result.put(Constants.JOINER_NAME, getJoinerName());
        result.put(Constants.TURN, getTurn());
        result.put(Constants.STATE, getState());
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
