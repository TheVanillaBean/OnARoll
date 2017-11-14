package com.onarollapp.onaroll.Logic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Curt on 11/13/2017.
 */

public class GameState {
    ArrayList<RollResult> rollHistory;
    String playerOneId;
    String playerTwoId;
    boolean isPlayerOneTurn;

    int playerOneTotalScore;
    int playerTwoTotalScore;
    int playerOneLastRunningScore;
    int playerTwoLastRunningScore;

    int currentPlayerRunningScore;


    public RollResult getMostRecentRoll() {
        return rollHistory.get(rollHistory.size() - 1);
    }

    public void updateMostRecentRoll(RollResult newResult) {
        rollHistory.set(rollHistory.size() - 1, newResult);
    }

    public void addNewRoll(RollResult newRoll) {
        rollHistory.add(newRoll);
    }

}
