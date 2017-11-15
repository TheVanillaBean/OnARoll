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
    String fullName;
    String phoneNumber;
    String phoneNumberVerified;
    String password;
    String userProfilePicLocation;
    String userAllTimeRank;

    String deviceToken;

    public String getUserAllTimeRank() {
        return (userAllTimeRank == null) ? "0" : userAllTimeRank;
    }

    public void setUserAllTimeRank(String userAllTimeRank) {
        this.userAllTimeRank = userAllTimeRank;
    }

    public String getDeviceToken() {
        return (deviceToken == null) ? "" : deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

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

    public String getFullName() {
        return (fullName == null) ? "" : fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return (phoneNumber == null) ? "" : phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumberVerified() {
        return (phoneNumberVerified == null) ? "" : phoneNumberVerified;
    }

    public void setPhoneNumberVerified(String phoneNumberVerified) {
        this.phoneNumberVerified = phoneNumberVerified;
    }

    public String getPassword() {
        return (password == null) ? "" : password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserProfilePicLocation() {
        return (userProfilePicLocation == null) ? "" : userProfilePicLocation;
    }

    public void setUserProfilePicLocation(String userProfilePicLocation) {
        this.userProfilePicLocation = userProfilePicLocation;
    }

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Exclude
    public Map<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put(Constants.UUID, uuid);
        result.put(Constants.EMAIL, email);
        result.put(Constants.FULL_NAME, fullName);
        result.put(Constants.PHONE_NUMBER, phoneNumber);
        result.put(Constants.PHONE_NUMBER_VERIFIED, phoneNumberVerified);
        result.put(Constants.PASSWORD, password);
        result.put(Constants.USER_PROFILE_PIC_LOC, userProfilePicLocation);
        result.put(Constants.DEVICE_TOKEN, deviceToken);
        result.put(Constants.ALL_TIME_RANK, userAllTimeRank);
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
