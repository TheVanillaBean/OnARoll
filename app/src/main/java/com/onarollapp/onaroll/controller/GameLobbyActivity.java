package com.onarollapp.onaroll.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
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
            protected void onBindViewHolder(GameHolder holder, int position, final Game model) {
                holder.setName(model.getCreatorName());
                holder.updateProfileLetter(model.getCreatorName());
                holder.getView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(!model.getCreatorID().equals(mCurrentUser.getUUID())){

                            joinGame(model.getUuid());

                        }else{
                            Toast.makeText(getApplicationContext(), "You can't join a game with yourself silly goose:)", Toast.LENGTH_LONG).show();

                        }

                        L.m("OnCLick");
                    }
                });
            }
        };

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.startListening();
    }

    private void joinGame(String key){
        FBDataService.getInstance().gamesRef().child(key).runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {

                Game game = mutableData.getValue(Game.class);

                if(game == null){
                    return Transaction.success(mutableData);
                }

                L.m("joinerID" + game.getJoinerID());

                if(game.getJoinerID().equals("")){
                    L.m("made it in");
                    game.setState(Constants.STATE_JOIN);
                    game.setJoinerID(mCurrentUser.getUUID());
                    game.setJoinerName(mCurrentUser.getName());
                }

                mutableData.setValue(game);

                L.m("mut" + mutableData.getKey());

                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean commited, DataSnapshot dataSnapshot) {

                if(commited){

                    Game game = dataSnapshot.getValue(Game.class);

                    if(game != null){
                        if(game.getJoinerID().equals(mCurrentUser.getUUID())){
                            mCreateGameBtn.setEnabled(false);
                            watchGame(game.getUuid());
                            L.m("Success started game!");
                        }else{
                            L.m("Game already joined");
                        }
                    }else{
                        L.m("Invalid Game");
                    }


                }else{
                    L.m("Could not commit!");
                }

            }
        });
    }

    private void watchGame(String key){

        FBDataService.getInstance().gamesRef().child(key).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Game game = dataSnapshot.getValue(Game.class);

                if(game == null){
                    mCreateGameBtn.setEnabled(true);
                    Toast.makeText(getApplicationContext(), "Game Ended. Please Play Again...", Toast.LENGTH_LONG).show();
                }else{

                    if(game.getState().equals(Constants.STATE_JOIN)){
                        FBDataService.getInstance().usersRef().child(game.getCreatorID()).child(Constants.ACTIVE_GAME_ID).setValue(game.getUuid());
                        FBDataService.getInstance().usersRef().child(game.getJoinerID()).child(Constants.ACTIVE_GAME_ID).setValue(game.getUuid());
                        navigateToGameLobbyActivity(game);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
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
            watchGame(mGame.getUuid());
        }else{
            mGame.setState(Constants.STATE_DISCONNECT);
            FBDataService.getInstance().gamesRef().child(mGame.getUuid()).setValue(mGame);
            mCreateGameBtn.setText("New Game");
            mGame = null;
        }

    }

    public void navigateToGameLobbyActivity(Game game){

        Bundle bundle = new Bundle();
        Parcelable wrappedUser = Parcels.wrap(mCurrentUser);
        Parcelable wrappedGame = Parcels.wrap(game);
        bundle.putParcelable(Constants.EXTRA_USER_PARCEL, wrappedUser);
        bundle.putParcelable(Constants.EXTRA_GAME_PARCEL, wrappedGame);


        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

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
