package com.onarollapp.onaroll.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.onarollapp.onaroll.DataService.FBDataService;
import com.onarollapp.onaroll.POJO.GameUpdateEvent;
import com.onarollapp.onaroll.R;
import com.onarollapp.onaroll.model.Game;
import com.onarollapp.onaroll.model.User;
import com.onarollapp.onaroll.util.Constants;
import com.onarollapp.onaroll.util.Dialog;
import com.onarollapp.onaroll.util.L;
import com.onarollapp.onaroll.util.Util;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.parceler.Parcels;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class GameActivity extends AppCompatActivity {

    private static int MAX_SCORE = 15;

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.current_player_turn_label) TextView mCurrentPlayerTurnLabel;
    @BindView(R.id.current_player_score_textview) TextView mCurrentPlayerScoreTextView;
    @BindView(R.id.other_player_turn_label) TextView mOtherPlayerTurnLabel;
    @BindView(R.id.other_player_score_textview) TextView mOtherPlayerScoreTextView;

    @BindView(R.id.current_score_label) TextView mCurrentScoreLabel;
    @BindView(R.id.current_score_textview) TextView mCurrentScoreTextView;


    @BindView(R.id.roll_btn) Button mRollBtn;
    @BindView(R.id.hold_btn) Button mHoldBtn;


    private Game mGame;
    private User mCurrentUser;

    private String mOtherUserId;
    private int mRoundScore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Active Game");
        }

        if (Parcels.unwrap(getIntent().getExtras().getParcelable(Constants.EXTRA_GAME_PARCEL)) != null) {

            mGame = Parcels.unwrap(getIntent().getExtras().getParcelable(Constants.EXTRA_GAME_PARCEL));
            mCurrentUser = Parcels.unwrap(getIntent().getExtras().getParcelable(Constants.EXTRA_USER_PARCEL));

            mRollBtn.setEnabled(false);
            mHoldBtn.setEnabled(false);

            if(mGame.getCreatorID().equals(mCurrentUser.getUUID())){
                mOtherUserId = mGame.getJoinerID();
            }else{
                mOtherUserId = mGame.getCreatorID();
            }

            gameListener();

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGameUpdateEvent(GameUpdateEvent event) {
        if (event.getError() == null){
            mGame = event.getGame();
        }else{
            Dialog.showDialog(this, "Game Update Error", event.getError(), "Okay");
        }
    }

    private void gameListener(){

        FBDataService.getInstance().gamesRef().child(mGame.getUuid()).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Game game = dataSnapshot.getValue(Game.class);

                if(game == null){
                    Toast.makeText(getApplicationContext(), "Game Ended. Please Play Again...", Toast.LENGTH_LONG).show();
                }else{

                    mGame = game;
                    mRoundScore = 0;

                    if(mGame.getCreatorScore() >= MAX_SCORE || mGame.getJoinerScore() >= MAX_SCORE){

                        mGame.setState(Constants.STATE_DONE);
                        EventBus.getDefault().unregister(this);
                        FBDataService.getInstance().updateGame(mGame);
                        Toast.makeText(GameActivity.this, getWinnerName() + " is the winner!", Toast.LENGTH_LONG).show();
                        finish();

                    }

                    if(mGame.getTurn().equals(mCurrentUser.getUUID())){

                        mRollBtn.setEnabled(true);
                        mHoldBtn.setEnabled(true);

                        mRollBtn.setBackgroundColor(Util.getColor(GameActivity.this, android.R.color.holo_red_dark));
                        mHoldBtn.setBackgroundColor(Util.getColor(GameActivity.this, android.R.color.holo_blue_dark));
                        mCurrentPlayerTurnLabel.setTextColor(Util.getColor(GameActivity.this, R.color.colorGreyDark));
                        mCurrentPlayerScoreTextView.setTextColor(Util.getColor(GameActivity.this, R.color.colorGreyDark));
                        mOtherPlayerTurnLabel.setTextColor(Util.getColor(GameActivity.this, R.color.colorGreyLight));
                        mOtherPlayerScoreTextView.setTextColor(Util.getColor(GameActivity.this, R.color.colorGreyLight));
                    }
                    if(mGame.getCreatorID().equals(mCurrentUser.getUUID())){
                        mCurrentPlayerScoreTextView.setText(String.valueOf(mGame.getCreatorScore()));
                        mOtherPlayerScoreTextView.setText(String.valueOf(mGame.getJoinerScore()));
                    }else{
                        mOtherPlayerScoreTextView.setText(String.valueOf(mGame.getCreatorScore()));
                        mCurrentPlayerScoreTextView.setText(String.valueOf(mGame.getJoinerScore()));
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Error retrieving game...", Toast.LENGTH_LONG).show();
            }

        });

    }

    private String getWinnerName(){

        if (mGame.getCreatorID().equals(mCurrentUser.getUUID())){
            return mGame.getCreatorName();
        }else{
            return mGame.getJoinerName();
        }
    }

    private String getNextPlayerID(){

       if (mGame.getTurn().equals(mCurrentUser.getUUID())){
           return mOtherUserId;
       }else{
           return mCurrentUser.getUUID();
       }
    }

    @OnClick(R.id.roll_btn)
    public void onRollBtnPressed() {

        int diceNumber = (int) Math.ceil(Math.random() * 6);

        L.m(diceNumber + " dice");


        if(diceNumber != 1){

            mRoundScore = mRoundScore + diceNumber;
            L.m(mRoundScore + " round");
            mCurrentScoreTextView.setText(String.valueOf(mRoundScore));

        }else{

            Toast.makeText(this, "You rolled a one. You lose all your points for this turn :(", Toast.LENGTH_SHORT).show();

            mRollBtn.setEnabled(false);
            mHoldBtn.setEnabled(false);

            mRollBtn.setBackgroundColor(Util.getColor(GameActivity.this, android.R.color.holo_red_light));
            mHoldBtn.setBackgroundColor(Util.getColor(GameActivity.this, android.R.color.holo_blue_light));
            mCurrentPlayerTurnLabel.setTextColor(Util.getColor(GameActivity.this, R.color.colorGreyLight));
            mCurrentPlayerScoreTextView.setTextColor(Util.getColor(GameActivity.this, R.color.colorGreyLight));
            mOtherPlayerTurnLabel.setTextColor(Util.getColor(GameActivity.this, R.color.colorGreyDark));
            mOtherPlayerScoreTextView.setTextColor(Util.getColor(GameActivity.this, R.color.colorGreyDark));

            mCurrentScoreTextView.setText("0");
            mGame.setTurn(getNextPlayerID());
            FBDataService.getInstance().updateGame(mGame);
        }
    }

    @OnClick(R.id.hold_btn)
    public void onHoldBtnPressed() {

        mRollBtn.setEnabled(false);
        mHoldBtn.setEnabled(false);

        mRollBtn.setBackgroundColor(Util.getColor(GameActivity.this, android.R.color.holo_red_light));
        mHoldBtn.setBackgroundColor(Util.getColor(GameActivity.this, android.R.color.holo_blue_light));
        mCurrentPlayerTurnLabel.setTextColor(Util.getColor(GameActivity.this, R.color.colorGreyLight));
        mCurrentPlayerScoreTextView.setTextColor(Util.getColor(GameActivity.this, R.color.colorGreyLight));
        mOtherPlayerTurnLabel.setTextColor(Util.getColor(GameActivity.this, R.color.colorGreyDark));
        mOtherPlayerScoreTextView.setTextColor(Util.getColor(GameActivity.this, R.color.colorGreyDark));

        if(mGame.getCreatorID().equals(mCurrentUser.getUUID())){
            mGame.setCreatorScore(mGame.getCreatorScore() + mRoundScore);
        }else{
            mGame.setJoinerScore(mGame.getJoinerScore() + mRoundScore);
        }

        mCurrentScoreTextView.setText("0");

        mGame.setTurn(getNextPlayerID());
        FBDataService.getInstance().updateGame(mGame);


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
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
