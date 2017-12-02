package com.onarollapp.onaroll.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.onarollapp.onaroll.R;
import com.onarollapp.onaroll.model.Game;
import com.onarollapp.onaroll.model.User;
import com.onarollapp.onaroll.util.Constants;

import org.greenrobot.eventbus.EventBus;
import org.parceler.Parcels;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class GameActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar mToolbar;

    private Game mGame;
    private User mCurrentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("New Game");
        }

        if (Parcels.unwrap(getIntent().getExtras().getParcelable(Constants.EXTRA_GAME_PARCEL)) != null) {

            mGame = Parcels.unwrap(getIntent().getExtras().getParcelable(Constants.EXTRA_GAME_PARCEL));
            mCurrentUser = Parcels.unwrap(getIntent().getExtras().getParcelable(Constants.EXTRA_USER_PARCEL));

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
