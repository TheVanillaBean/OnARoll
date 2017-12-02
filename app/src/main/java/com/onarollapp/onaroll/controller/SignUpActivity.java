package com.onarollapp.onaroll.controller;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.onarollapp.onaroll.DataService.AuthService;
import com.onarollapp.onaroll.DataService.FBDataService;
import com.onarollapp.onaroll.POJO.AuthEvent;
import com.onarollapp.onaroll.POJO.UserUpdateEvent;
import com.onarollapp.onaroll.R;
import com.onarollapp.onaroll.model.User;
import com.onarollapp.onaroll.util.Constants;
import com.onarollapp.onaroll.util.Dialog;
import com.onarollapp.onaroll.util.L;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.email_input) EditText mEmailInput;
    @BindView(R.id.password_input) EditText mPasswordInput;
    @BindView(R.id.name_input) EditText mNameInput;
    @BindView(R.id.sign_up_btn) Button mSignUpBtn;

    private User mUser;
    private MaterialDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("New Player");
        }
        progressDialog = Dialog.showProgressIndeterminateDialog(SignUpActivity.this, "Loading...", "Signing up...", false);

    }

    @OnClick(R.id.sign_up_btn)
    public void onSignUpBtnPressed() {
        processSignUp();
    }

    private void processSignUp() {

        String email = mEmailInput.getText().toString();
        String password = mPasswordInput.getText().toString();
        String name = mNameInput.getText().toString();

        if(!isAnyFormFieldEmpty()){
            Dialog.showDialog(SignUpActivity.this, "Sign Up Error", "One or More Fields are Blank", "Okay");
        }else{
            mUser = new User(email, password, name);
            signUpUser();
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSignUpCallBack(AuthEvent event) {
        if (event.getError() == null){
            mUser.setUuid(event.getUser().getUid());
            FBDataService.getInstance().saveUser(mUser);
        }else{
            mSignUpBtn.setEnabled(true);
            progressDialog.dismiss();
            Dialog.showDialog(SignUpActivity.this, "Authentication Error", event.getError(), "Okay");
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSaveUserCallback(UserUpdateEvent event) {
        mSignUpBtn.setEnabled(true);
        progressDialog.dismiss();
        if (event.getError() == null){
            navigateToPlayerActivity();
        }else{
            Dialog.showDialog(SignUpActivity.this, "Authentication Error", event.getError(), "Okay");
        }
    }

    private void navigateToPlayerActivity(){
        Intent intent  = new Intent(SignUpActivity.this, PlayerMainActivity.class);
        intent.putExtra(Constants.EXTRA_USER_ID, mUser.getUUID());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    private void signUpUser(){
        if (mUser != null){
            progressDialog.show();
            mPasswordInput.setText("");
            mSignUpBtn.setEnabled(false);
            AuthService.getInstance().signUp(mUser.getEmail(), mUser.getPassword());
        }
    }

    private boolean isAnyFormFieldEmpty() {
        return !(mEmailInput.getText().toString().isEmpty() || mPasswordInput.getText().toString().isEmpty() | mNameInput.getText().toString().isEmpty());
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
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
