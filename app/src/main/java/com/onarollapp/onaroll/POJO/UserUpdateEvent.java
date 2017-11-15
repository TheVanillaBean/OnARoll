package com.onarollapp.onaroll.POJO;

import android.support.annotation.Nullable;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Alex on 1/21/2017.
 */

public class UserUpdateEvent {

    private final String error;

    public UserUpdateEvent(@Nullable String error){
        this.error = error;
    }

    public String getError() {
        return error;
    }

}
