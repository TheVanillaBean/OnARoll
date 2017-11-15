package com.onarollapp.onaroll.POJO;

import android.support.annotation.Nullable;

import com.google.firebase.auth.FirebaseUser;

public class PasswordResetEvent {

    private final String error;

    public PasswordResetEvent(@Nullable String error){
        this.error = error;
    }

    public String getError() {
        return error;
    }

}
