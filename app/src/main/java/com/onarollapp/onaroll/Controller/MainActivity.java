package com.onarollapp.onaroll.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.onarollapp.onaroll.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button goToShopButton = (Button)findViewById(R.id.go_to_shop);
        goToShopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeActivity(ShopActivity.class);
            }
        });

        Button goToPerkCustomization = (Button)findViewById(R.id.go_to_perk_customiztion);
        goToPerkCustomization.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeActivity(PerkCustomization.class);
            }
        });

        Button startGame = (Button)findViewById(R.id.start_game);
        startGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeActivity(GameActivity.class);
            }
        });

    }




    private void changeActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}
