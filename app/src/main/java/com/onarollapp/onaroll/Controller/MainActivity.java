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


        final Button goToShopButton = (Button)findViewById(R.id.go_to_shop);
        goToShopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Crashing when I try to change my activity not sure why
                changeActivity(ShopActivity.class);

                // This line was to test that the on click listener was working correctly
                /*final TextView test = (TextView)findViewById(R.id.helloWorld);
                test.setText("IT WORKED!!!");*/
            }
        });

    }




    private void changeActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}
