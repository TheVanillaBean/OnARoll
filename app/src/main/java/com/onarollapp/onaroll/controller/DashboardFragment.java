package com.onarollapp.onaroll.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.afollestad.materialdialogs.MaterialDialog;
import com.onarollapp.onaroll.DataService.AuthService;
import com.onarollapp.onaroll.DataService.FBDataService;
import com.onarollapp.onaroll.POJO.PendingGameRetrieveEvent;
import com.onarollapp.onaroll.POJO.UserCastEvent;
import com.onarollapp.onaroll.R;
import com.onarollapp.onaroll.model.Game;
import com.onarollapp.onaroll.model.User;
import com.onarollapp.onaroll.util.Constants;
import com.onarollapp.onaroll.util.Dialog;
import com.onarollapp.onaroll.util.L;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DashboardFragment extends Fragment implements MaterialDialog.OnDismissListener{

    @BindView(R.id.new_game_btn) Button mNewGameBtn;

    private User mCurrentUser;

    private MaterialDialog progressDialog;

    private String newGameKey;

    public DashboardFragment() {
    }

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                .title("New Game")
                .content("Waiting for match...")
                .progress(true, 0)
                .dismissListener(this)
                .autoDismiss(false)
                .progressIndeterminateStyle(true);

        progressDialog = builder.build();

        mNewGameBtn.setEnabled(false);
        if(AuthService.getInstance().getCurrentUser() != null){
            User.castUser(AuthService.getInstance().getCurrentUser().getUid());
        }else{
            Dialog.showDialog(getActivity(), "Authentication Error", "Could not find user...", "Okay");
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserCastCallBack(UserCastEvent event) {
        mNewGameBtn.setEnabled(true);
        if (event.getError() == null){
            mCurrentUser = event.getUser();
        }else{
            Dialog.showDialog(getActivity(), "Authentication Error", event.getError(), "Okay");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPendingGameCallBack(PendingGameRetrieveEvent event) {


        //Just gameID

        if (event.getError() == null){

            progressDialog.dismiss();

            Game game = event.getGame();

            game.setJoinerID(mCurrentUser.getUUID());

            FBDataService.getInstance().gamesRef().child(game.getUuid()).setValue(game);

            FBDataService.getInstance().gamesRef().child(newGameKey).removeValue();

            L.m("There is a game with ID " + event.getGame().getUuid());

        }else{

            if(event.getError().equals("No Games")){

//                newGameKey = FBDataService.getInstance().gamesRef().push().getKey();
//                Game game = new Game(newGameKey, mCurrentUser.getUUID(), mCurrentUser.getUUID(), Constants.STATE_OPEN);
//                FBDataService.getInstance().gamesRef().child(newGameKey).setValue(game);

                //ListenForChnages

            }else{

                progressDialog.dismiss();

                L.m("Other type of error");

            }

        }
    }

    //OnSubscribe-UserCurrentGameChanges
    //Upon change - get game object and navigate

    @OnClick(R.id.new_game_btn)
    public void onNewGameBtnPressed() {
//        progressDialog.show();

//        FBDataService.getInstance().retrievePendingGame();

        navigateToGameLobbyActivity();

    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {

        L.m("dismiss!");

        removePendingGame();

    }

    public void removePendingGame(){
        if(newGameKey != null) {
            FBDataService.getInstance().gamesRef().child(newGameKey).removeValue();
//            FBDataService.getInstance().pendingGamesRef().child(newGameKey).removeValue();
        }

    }
    public void navigateToGameLobbyActivity(){

        Bundle bundle = new Bundle();
        Parcelable wrappedUser = Parcels.wrap(mCurrentUser);
        bundle.putParcelable(Constants.EXTRA_USER_PARCEL, wrappedUser);


        Intent intent = new Intent(getActivity(), GameLobbyActivity.class);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }


}
