package com.onarollapp.onaroll.controller;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.onarollapp.onaroll.DataService.AuthService;
import com.onarollapp.onaroll.POJO.UserCastEvent;
import com.onarollapp.onaroll.R;
import com.onarollapp.onaroll.model.User;
import com.onarollapp.onaroll.util.Dialog;
import com.onarollapp.onaroll.util.L;
import com.onarollapp.onaroll.util.Util;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.logo_imageview) ImageView background;

    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initBackgroundImage();
        if (Util.isGooglePlayServicesAvailable(MainActivity.this)){
            validateUserToken();
        }
    }

    private void initBackgroundImage() {
        Glide.with(this)
                .load(R.drawable.dice)
                .into(background);
    }

    private void validateUserToken() {

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    User.castUser(user.getUid());
                } else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

            }
        };

        AuthService.getInstance().getAuthInstance().addAuthStateListener(mAuthListener);

    }

    private void navigateToPlayerActivity(){

        EventBus.getDefault().unregister(this);
        if (mAuthListener != null) {
            AuthService.getInstance().getAuthInstance().removeAuthStateListener(mAuthListener);
        }

        Intent intent = null;
        intent = new Intent(MainActivity.this, PlayerMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserCastCallBack(UserCastEvent event) {
        if (event.getError() == null){
            this.navigateToPlayerActivity();
        }else{
            Dialog.showDialog(MainActivity.this, "Authentication Error", event.getError(), "Okay");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        if (mAuthListener != null) {
            AuthService.getInstance().getAuthInstance().removeAuthStateListener(mAuthListener);
        }
        super.onStop();
    }

}
