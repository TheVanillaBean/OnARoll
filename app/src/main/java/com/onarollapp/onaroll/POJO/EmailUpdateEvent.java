package com.onarollapp.onaroll.POJO;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Alex on 1/21/2017.
 */

public class EmailUpdateEvent {

    private final String error;
    private final String email;

    public EmailUpdateEvent(@Nullable String error, @NonNull String email){
        this.error = error;
        this.email = email;
    }

    public String getError() {
        return error;
    }
    public String getEmail() {
        return email;
    }

}
