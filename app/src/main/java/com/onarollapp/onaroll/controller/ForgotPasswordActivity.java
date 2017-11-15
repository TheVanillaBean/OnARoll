package com.onarollapp.onaroll.controller;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.onarollapp.onaroll.DataService.AuthService;
import com.onarollapp.onaroll.POJO.PasswordResetEvent;
import com.onarollapp.onaroll.R;
import com.onarollapp.onaroll.util.Dialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ForgotPasswordActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.email_input) EditText mEmailInput;
    @BindView(R.id.send_email_btn) Button mSubmitbtn;

    private String email;
    private MaterialDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Forgot Password");
        }
        progressDialog = Dialog.showProgressIndeterminateDialog(ForgotPasswordActivity.this, "Loading...", "Sending Reset Email...", false);

    }

    @OnClick(R.id.send_email_btn)
    public void onSendBtnPressed() {

        email = mEmailInput.getText().toString();
        if(email.length() > 5){
            mSubmitbtn.setEnabled(false);
            progressDialog.show();
            AuthService.getInstance().resetPassword(email);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPasswordCallback(PasswordResetEvent event) {
        mSubmitbtn.setEnabled(true);
        progressDialog.dismiss();
        if (event.getError() == null){
            Dialog.showDialog(ForgotPasswordActivity.this, "Email Sent", "An email has been sent to " + email + " with a reset link.", "Okay");
        }else{
            Dialog.showDialog(ForgotPasswordActivity.this, "Password Reset Error", event.getError(), "Okay");
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
