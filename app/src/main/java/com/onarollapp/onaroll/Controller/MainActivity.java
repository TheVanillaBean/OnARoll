package com.onarollapp.onaroll.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.onarollapp.onaroll.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToShop(View v) {
        Intent intent = new Intent(this, ShopActivity.class);

        startActivity(intent);
    }
}
