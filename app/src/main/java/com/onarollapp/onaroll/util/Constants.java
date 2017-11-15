package com.onarollapp.onaroll.util;


public class Constants {

    public static final int ERROR_DIALOG_REQUEST = 9001;

    //putExtra
    public static final String EXTRA_USER_ID = "userIDExtra";
    public static final String EXTRA_USER_PARCEL = "userParcel";
    public static final String EXTRA_GAME_PARCEL = "gameParcel";

    //Model - User
    public static final String UUID = "uuid";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    //Model - Game
    public static final String PLAYER_ONE_ID = "playerOneID";
    public static final String PLAYER_TWO_ID = "playerTwoID";
    public static final String PLAYER_ONE_SCORE = "playerOneScore";
    public static final String PLAYER_TWO_SCORE = "playerTwoScore";
    public static final String TURN = "turn";

    //CHILD NODES
    public static final String FIR_CHILD_USERS = "user";
    public static final String FIR_CHILD_GAMES = "games";
    public static final String FIR_CHILD_ACTIVE_GAMES = "games-active";
    public static final String FIR_CHILD_PENDING_GAMES = "games-pending";

}
