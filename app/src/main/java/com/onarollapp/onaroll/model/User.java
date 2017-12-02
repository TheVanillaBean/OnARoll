package com.onarollapp.onaroll.model;

/**
 * Created by Alex on 1/21/2017.
 */

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;
import com.onarollapp.onaroll.DataService.FBDataService;
import com.onarollapp.onaroll.POJO.UserCastEvent;
import com.onarollapp.onaroll.util.Constants;

import org.greenrobot.eventbus.EventBus;
import org.parceler.Parcel;

import java.util.HashMap;
import java.util.Map;

@Parcel /* Variables are not private because of the Parcel Dependency - Reflection */
@IgnoreExtraProperties
public class User {

    String uuid;
    String email;
    String password;
    String name;
    String activeGameID;

    public String getUUID() {
        return (uuid == null) ? "" : uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return (email == null) ? "" : email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return (password == null) ? "" : password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return (name == null) ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActiveGameID() {
        return (activeGameID == null) ? "" : activeGameID;
    }

    public void setActiveGameID(String activeGameID) {
        this.activeGameID = activeGameID;
    }

    public User() {
    }

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Exclude
    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put(Constants.UUID, uuid);
        result.put(Constants.EMAIL, email);
        result.put(Constants.PASSWORD, password);
        result.put(Constants.NAME, name);
        result.put(Constants.ACTIVE_GAME_ID, activeGameID);
        return result;
    }

    public static void castUser(String uuid){

        FBDataService.getInstance().usersRef().child(uuid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (!dataSnapshot.exists()) return;

                User user = dataSnapshot.getValue(User.class);

                EventBus.getDefault().post(new UserCastEvent(null, user));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                EventBus.getDefault().post(new UserCastEvent(databaseError.getMessage(), null));
            }
        });

    }

}
