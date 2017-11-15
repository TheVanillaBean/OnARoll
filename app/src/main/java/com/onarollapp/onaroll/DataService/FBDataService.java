package com.onarollapp.onaroll.DataService;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.onarollapp.onaroll.POJO.UserUpdateEvent;
import com.onarollapp.onaroll.model.User;
import com.onarollapp.onaroll.util.Constants;


import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FBDataService {


    private static final FBDataService _instance = new FBDataService();
    private static final FirebaseDatabase  mDatabase = FirebaseDatabase.getInstance();

    public static FBDataService getInstance() {
        return _instance;
    }

    //-----------------Database References------------------//

    public DatabaseReference mainRef() {
        return mDatabase.getReference();
    }

    public DatabaseReference usersRef() {
        return mDatabase.getReference(Constants.FIR_CHILD_USERS);
    }

    public DatabaseReference gamesRef() {
        return mDatabase.getReference(Constants.FIR_CHILD_GAMES);
    }

    public DatabaseReference userGamesRef() {
        return mDatabase.getReference(Constants.FIR_CHILD_USER_GAMES);
    }

    public DatabaseReference moviesRef() {
        return mDatabase.getReference(Constants.FIR_CHILD_MOVIES);
    }

    public DatabaseReference allTimeRankRef() {
        return mDatabase.getReference(Constants.FIR_CHILD_ALL_TIME_LEADERBOARD);
    }

    public DatabaseReference movieRatingsRef() {
        return mDatabase.getReference(Constants.FIR_CHILD_MOVIE_RATINGS);
    }

    //-----------------End Database References------------------//


    public void saveUser(final User user){
        Map<String, Object> properties = user.toMap();
        usersRef().child(user.getUUID()).setValue(properties, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null){
                    EventBus.getDefault().post(new UserUpdateEvent(null));
                }else{
                    EventBus.getDefault().post(new UserUpdateEvent(databaseError.getMessage()));
                }
            }
        });
    }

    public void updateUser(final User user){
        Map<String, Object> properties = user.toMap();
        usersRef().child(user.getUUID()).updateChildren(properties, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null){
                    EventBus.getDefault().post(new UserUpdateEvent(null));
                }else{
                    EventBus.getDefault().post(new UserUpdateEvent(databaseError.getMessage()));
                }
            }
        });
    }

    public FBDataService(){
    }

}
