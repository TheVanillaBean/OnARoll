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
    public static final String NAME = "name";
    public static final String ACTIVE_GAME_ID = "activeGameID";


    //Model - Game
    public static final String CREATOR_ID = "creatorID";
    public static final String JOINER_ID = "joinerID";
    public static final String CREATOR_SCORE = "creatorScore";
    public static final String JOINER_SCORE = "joinerScore";
    public static final String CREATOR_NAME = "creatorName";
    public static final String JOINER_NAME = "joinerName";
    public static final String TURN = "turn";
    public static final String STATE = "state";

    //CHILD NODES
    public static final String FIR_CHILD_USERS = "users";
    public static final String FIR_CHILD_GAMES = "games";

    //KEYS
    public static final String STATE_OPEN = "OPEN";
    public static final String STATE_JOIN = "JOIN";
    public static final String STATE_DONE = "DONE";
    public static final String STATE_DISCONNECT = "DISCONNECT";

    public static final String NO_ACTIVE_GAME = "NoActiveGame";


}
