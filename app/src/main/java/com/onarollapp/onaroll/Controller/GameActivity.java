package com.onarollapp.onaroll.Controller;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.onarollapp.onaroll.R;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by yizhu_000 on 11/12/2017.
 */

public class GameActivity extends AppCompatActivity {

    boolean testGame;
    ObjectOutputStream toServer;
    ObjectInputStream fromServer;
    Socket socket;
    int newScore;

    TextView cumulativeScore, currentScore, cumulativeScore_label, currentScore_label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        cumulativeScore = (TextView)findViewById(R.id.cumulativeScore_textView);
        currentScore = (TextView)findViewById(R.id.currentScore_textView);
        cumulativeScore_label = (TextView)findViewById(R.id.cumulativeScore_label);
        currentScore_label = (TextView)findViewById(R.id.currentScore_label);

        testGame = false;
        newScore = 0;

        try {
            socket = new Socket("localhost", 8000);
            fromServer = new ObjectInputStream(socket.getInputStream());
            toServer = new ObjectOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("Connection", "Could not connect to server. Setting to test mode");
            testGame = true;
        }

        new Thread(new ServerListener()).start();

        Button testButton = (Button)findViewById(R.id.testGame);
        testButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                testGame = true;
                newScore = 0;
                currentScore.setText(Integer.toString(newScore));
                cumulativeScore.setText(Integer.toString(newScore));
                Log.d("DEBUG", "Game set to test mode");
            }
        });

        Button rollButton = (Button)findViewById(R.id.roll_die);
        rollButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                submitPlayerRoll();
            }
        });

        Button holdButton = (Button)findViewById(R.id.hold_die);
        holdButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                submitPlayerHold();
            }
        });

        /* For perks
        final Button goToShopButton = (Button)findViewById(R.id.go_to_shop);
        goToShopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeActivity(ShopActivity.class);
            }
        });
        */
    }

    private void submitPlayerRoll() {
        if (testGame) {
            newScore = Integer.parseInt((String) currentScore.getText()) + 1;
        }
        currentScore.setText(Integer.toString(newScore));
        currentScore.setTextSize((float) (Math.log((double) newScore * 3) + 14));
        currentScore_label.setTextSize((float) (Math.log((double) newScore * 3) + 14));
    }

    private void submitPlayerHold() {
        if (testGame){
            newScore = Integer.parseInt((String) currentScore.getText());
        }
        cumulativeScore.setText(Integer.toString(newScore));
        cumulativeScore.setTextSize((float) (Math.log((double) newScore * 3) + 14));
        cumulativeScore_label.setTextSize((float) (Math.log((double) newScore * 3) + 14));
    }

    class ServerListener implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    //SOMEOBJECT = (SOMEOBJECT) fromServer.readObject();
                } catch (Exception e) {
                    e.printStackTrace();
                    //break;
                }
            }
        }
    }

}
