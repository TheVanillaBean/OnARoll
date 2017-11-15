package com.onarollapp.onaroll.util;


public class Constants {

    public static final int ERROR_DIALOG_REQUEST = 9001;

    //putExtra
    public static final String EXTRA_USER_ID = "userIDExtra";
    public static final String EXTRA_USER_PARCEL = "userParcel";
    public static final String EXTRA_GAME_PARCEL = "gameParcel";

    //Game Status
    public static final String STATUS_NEW = "noMovies";
    public static final String STATUS_EXISTING = "populated";
    public static final String STATUS_FINISHED = "finished";

    public static final int TOTAL_MOVIE_IDS = 60;

    //Model - User
    public static final String UUID = "uuid";
    public static final String EMAIL = "email";
    public static final String FULL_NAME = "fullName";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String PHONE_NUMBER_VERIFIED = "phoneNumberVerified";
    public static final String PASSWORD = "password";
    public static final String USER_PROFILE_PIC_LOC = "userProfilePicLocation";
    public static final String DEVICE_TOKEN = "deviceToken";
    public static final String ALL_TIME_RANK = "userAllTimeRank";

    //Model - Game
    public static final String MOVIE_LIST = "movieList";
    public static final String STATUS = "status";
    public static final String USER_ID = "userID";
    public static final String USER_NAME = "userName";
    public static final String SCORE = "userScore";
    public static final String TIMESTAMP = "timestamp";

    //CHILD NODES
    public static final String FIR_CHILD_USERS = "user";
    public static final String FIR_CHILD_GAMES = "games";
    public static final String FIR_CHILD_USER_GAMES = "user-games";
    public static final String FIR_CHILD_MOVIES = "movies";
    public static final String FIR_CHILD_ALL_TIME_LEADERBOARD = "allTimeRank";
    public static final String FIR_CHILD_MOVIE_RATINGS= "movieRatings";

}
