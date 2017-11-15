package com.onarollapp.onaroll.POJO;

import android.support.annotation.Nullable;

import com.google.firebase.auth.FirebaseUser;

public class AuthEvent {

    private final String error;

    private final FirebaseUser user;

    public AuthEvent(@Nullable String error, @Nullable FirebaseUser user){
        this.error = error;
        this.user = user;
    }

    public String getError() {
        return error;
    }

    public FirebaseUser getUser() {
        return user;
    }
}
