package com.onarollapp.onaroll.controller;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.onarollapp.onaroll.DataService.FBDataService;
import com.onarollapp.onaroll.R;
import com.onarollapp.onaroll.model.Game;
import com.onarollapp.onaroll.model.User;
import com.onarollapp.onaroll.util.Constants;
import com.onarollapp.onaroll.util.Dialog;
import com.onarollapp.onaroll.util.L;
import com.onarollapp.onaroll.view.CustomRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class GameLobbyActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.recycler_view) CustomRecyclerView mRecyclerView;
    @BindView(R.id.empty_list) TextView mEmptyList;
    @BindView(R.id.create_game_btn) Button mCreateGameBtn;

    private FirebaseRecyclerAdapter mAdapter;

    private MaterialDialog progressDialog;
    private User mCurrentUser;

    private Game mGame = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_lobby);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Game Lobby");
        }

        progressDialog = Dialog.showProgressIndeterminateDialog(GameLobbyActivity.this, "Loading...", "Joining Game", false);


        if (Parcels.unwrap(getIntent().getExtras().getParcelable(Constants.EXTRA_USER_PARCEL)) != null) {
            L.m("called");
            mCurrentUser = Parcels.unwrap(getIntent().getExtras().getParcelable(Constants.EXTRA_USER_PARCEL));
        }

        setupRecyclerView();

    }

    private void setupRecyclerView(){
        mRecyclerView.showIfEmpty(mEmptyList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Game> options =
                new FirebaseRecyclerOptions.Builder<Game>()
                        .setQuery(FBDataService.getInstance().gamesRef().orderByChild(Constants.STATE).equalTo(Constants.STATE_OPEN), Game.class)
                        .setLifecycleOwner(this)
                        .build();

        mAdapter = new FirebaseRecyclerAdapter<Game, GameHolder>(options) {

            @Override
            public GameHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.row_lobby, parent, false);

                return new GameHolder(view);
            }

            @Override
            protected void onBindViewHolder(GameHolder holder, int position, Game model) {
                holder.setName(model.getCreatorName());
                holder.updateProfileLetter(model.getCreatorName());
            }
        };

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.startListening();
    }

    @OnClick(R.id.create_game_btn)
    public void onCreateBtnPressed() {

        if(mGame == null){
            DatabaseReference ref = FBDataService.getInstance().gamesRef().push();
            String key = ref.getKey();
            ref.onDisconnect().removeValue();
            mGame = new Game(key, mCurrentUser.getUUID(), mCurrentUser.getName(), mCurrentUser.getUUID(), Constants.STATE_OPEN);
            FBDataService.getInstance().gamesRef().child(key).setValue(mGame);
            mCreateGameBtn.setText("Disconnect New Game");
            Toast.makeText(getApplicationContext(), "Waiting for connection.. Unable to join other games", Toast.LENGTH_LONG).show();
        }else{
            mGame.setState(Constants.STATE_DISCONNECT);
            FBDataService.getInstance().gamesRef().child(mGame.getUuid()).setValue(mGame);
            mCreateGameBtn.setText("New Game");
            mGame = null;
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mAdapter != null){
            mAdapter.stopListening();
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
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public static class GameHolder extends CustomRecyclerView.ViewHolder {
        private final TextView mNameField;
        private final ImageView mProfilePicImg;
        private View mView;
        private TextDrawable mTextDrawable;
        private ColorGenerator generator;
        private int randomColor;


        public GameHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            generator = ColorGenerator.MATERIAL;
            mNameField = (TextView) itemView.findViewById(R.id.name_label);
            mProfilePicImg = (ImageView) itemView.findViewById(R.id.profile_image);
            mView = itemView;
        }

        View getView(){
            return mView;
        }

        void updateProfileLetter(String name){
            randomColor = generator.getRandomColor();
            mTextDrawable = TextDrawable.builder()
                    .buildRound(name.substring(0,2), randomColor);
            mProfilePicImg.setImageDrawable(mTextDrawable);
        }

        void setName(String name) {
            mNameField.setText(name);
        }

    }


}
