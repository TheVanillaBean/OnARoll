package com.onarollapp.onaroll.POJO;

import android.support.annotation.Nullable;

import com.google.firebase.auth.FirebaseUser;
import com.onarollapp.onaroll.model.User;


public class UserCastEvent {

    private final String error;

    private final User user;

    public UserCastEvent(@Nullable String error, @Nullable User user){
        this.error = error;
        this.user = user;
    }

    public String getError() {
        return error;
    }

    public User getUser() {
        return user;
    }
}
