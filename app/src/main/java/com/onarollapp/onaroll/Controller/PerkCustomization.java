package com.onarollapp.onaroll.Controller;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.onarollapp.onaroll.Perks.EmptyPerk;
import com.onarollapp.onaroll.Perks.IncreaseChanceToRollOne;
import com.onarollapp.onaroll.Perks.Perk;
import com.onarollapp.onaroll.R;
import com.onarollapp.onaroll.Services.DataService;
import com.onarollapp.onaroll.Users.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PerkCustomization extends AppCompatActivity {

    private DataService ds;
    private Player player;
    private int selectedPerkSlot = -1;
    private int selectedPerk = -1;
    private static PerkCustomization instance;
    private ListView yourPerkList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perk_customization);

        ds = DataService.getInstance();
        //player = ds.getLoggedInPlayer();
        player = new Player();
        player.perkList = new ArrayList<Perk>();
        player.perkList.add(new EmptyPerk());
        player.perkList.add(new IncreaseChanceToRollOne());
        player.perkList.add(new IncreaseChanceToRollOne());
        player.perkList.add(new IncreaseChanceToRollOne());
        player.perkList.add(new IncreaseChanceToRollOne());
        instance = this;



        yourPerkList = (ListView) findViewById(R.id.perk_customization_your_perks_list);

        final ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, player.perkList);
        yourPerkList.setAdapter(adapter);


        yourPerkList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                PerkCustomization instance = PerkCustomization.getInstance();
                instance.selectPerk(position);
            }
        });


    }

    public void selectPerk(int perkPosition) {

        // If no perk slot is slected do nothing
        if (this.selectedPerkSlot != -1) {
            // Decrement counter for this type of perk
        }

    }

    public void setSelectedPerkSlot(int slotPosition) {
        // If a perk slot is selected
        if (this.selectedPerkSlot != -1) {

        }
    }

    public static PerkCustomization getInstance() {
        return instance;
    }



}


