package com.onarollapp.onaroll.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.onarollapp.onaroll.Perks.EmptyPerk;
import com.onarollapp.onaroll.Perks.IncreaseChanceToRollFive;
import com.onarollapp.onaroll.Perks.IncreaseChanceToRollFour;
import com.onarollapp.onaroll.Perks.IncreaseChanceToRollOne;
import com.onarollapp.onaroll.Perks.IncreaseChanceToRollSix;
import com.onarollapp.onaroll.Perks.IncreaseChanceToRollThree;
import com.onarollapp.onaroll.Perks.IncreaseChanceToRollTwo;
import com.onarollapp.onaroll.Perks.Perk;
import com.onarollapp.onaroll.Perks.PerkListItem;
import com.onarollapp.onaroll.Perks.RollOneExtraDie;
import com.onarollapp.onaroll.R;
import com.onarollapp.onaroll.Services.DataService;
import com.onarollapp.onaroll.Users.Player;

import java.util.ArrayList;

public class PerkCustomization extends AppCompatActivity {

    private DataService ds;
    private Player player;
    public static int selectedPerkSlot = -1;
    private ListView yourPerkList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perk_customization);

        PerkCustomization.selectedPerkSlot = -1;
        ds = DataService.getInstance();
        //player = ds.getLoggedInPlayer();
        player = new Player();
        player.perkList = new ArrayList<Perk>();
        player.perkList.add(new IncreaseChanceToRollOne());
        player.perkList.add(new IncreaseChanceToRollOne());
        player.perkList.add(new IncreaseChanceToRollOne());
        player.perkList.add(new IncreaseChanceToRollTwo());
        player.perkList.add(new IncreaseChanceToRollTwo());
        player.perkList.add(new IncreaseChanceToRollTwo());
        player.perkList.add(new IncreaseChanceToRollThree());
        player.perkList.add(new IncreaseChanceToRollThree());
        player.perkList.add(new IncreaseChanceToRollFour());
        player.perkList.add(new IncreaseChanceToRollFive());
        player.perkList.add(new IncreaseChanceToRollFive());
        player.perkList.add(new IncreaseChanceToRollFive());
        player.perkList.add(new IncreaseChanceToRollFive());
        player.perkList.add(new IncreaseChanceToRollFive());
        player.perkList.add(new IncreaseChanceToRollFive());
        player.perkList.add(new IncreaseChanceToRollFive());
        player.perkList.add(new IncreaseChanceToRollSix());
        player.perkList.add(new IncreaseChanceToRollSix());
        player.perkList.add(new IncreaseChanceToRollSix());
        player.perkList.add(new RollOneExtraDie());


        ArrayList<PerkListItem> perkItems = new ArrayList<PerkListItem>();
        // For each perk the player has
        for (int i = 0; i < player.perkList.size(); i++) {
            boolean wasInList = false;
            // Check list of perk items if they already have that perk
            for (int j = 0; j < perkItems.size() && !wasInList; j++) {
                if (perkItems.get(j).perk.id.equals(player.perkList.get(i).id)) {
                    wasInList = true;
                    perkItems.get(j).amountAvailable++;
                }
            }

            if (!wasInList) {
                // If the perk Items list does not contain that perk add it to the list
                perkItems.add(new PerkListItem(player.perkList.get(i), 1));
            }
        }

        // Remove already equipped perks from available perk list
        for (int i = 0; i < player.equippedPerks.length; i++) {
            // Id of Empty Perk
            if(!player.equippedPerks[i].id.equals("2")) {
                boolean wasInList = false;
                for(int j = 0; j < perkItems.size(); j++) {
                    if (perkItems.get(j).perk.id.equals(player.perkList.get(i).id)) {
                        wasInList = true;
                        if(perkItems.get(j).amountAvailable > 1) {
                            perkItems.get(j).amountAvailable--;
                        } else {
                            perkItems.remove(j);
                        }
                    }
                }
            }
        }

        yourPerkList = (ListView) findViewById(R.id.perk_customization_your_perks_list);

        PerkListAdapter adapter = new PerkListAdapter(this, perkItems);
        yourPerkList.setAdapter(adapter);


        yourPerkList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
               if(PerkCustomization.selectedPerkSlot != -1) {
                   // Does not equal empty perk
                   if(!player.equippedPerks[PerkCustomization.selectedPerkSlot].id.equals("2")) {
                           boolean wasInList = false;
                           //parent.getItemAtPosition(position)

                       // Just need to figure out how to get the reference to the list view list in here
                       /*for(int j = 0; j < perkItems.size(); j++) {
                               if (perkItems.get(j).perk.id.equals(player.perkList.get(i).id)) {
                                   wasInList = true;
                                   if(perkItems.get(j).amountAvailable > 1) {
                                       perkItems.get(j).amountAvailable--;
                                   } else {
                                       perkItems.remove(j);
                                   }
                               }
                           }*/

                   }
               }

            }
        });

        GridLayout gridLayout = (GridLayout) findViewById(R.id.perk_customization_perk1_layout);
        gridLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PerkCustomization.selectedPerkSlot = 0;

            }
        });
        GridLayout gridLayout2 = (GridLayout) findViewById(R.id.perk_customization_perk2_layout);
        gridLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PerkCustomization.selectedPerkSlot = 1;

            }
        });
        GridLayout gridLayout3 = (GridLayout) findViewById(R.id.perk_customization_perk3_layout);
        gridLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PerkCustomization.selectedPerkSlot = 2;

            }
        });
        GridLayout gridLayout4 = (GridLayout) findViewById(R.id.perk_customization_perk4_layout);
        gridLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PerkCustomization.selectedPerkSlot = 3;

            }
        });
        GridLayout gridLayout5 = (GridLayout) findViewById(R.id.perk_customization_perk5_layout);
        gridLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PerkCustomization.selectedPerkSlot = 4;

            }
        });
        GridLayout gridLayout6 = (GridLayout) findViewById(R.id.perk_customization_perk6_layout);
        gridLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PerkCustomization.selectedPerkSlot = 5;

            }
        });
        GridLayout gridLayout7 = (GridLayout) findViewById(R.id.perk_customization_perk7_layout);
        gridLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                PerkCustomization.selectedPerkSlot = 6;

            }
        });





    }



    private class PerkListAdapter extends ArrayAdapter<PerkListItem> {
        private final Context context;
        private final ArrayList<PerkListItem> perks;

        public PerkListAdapter(Context context, ArrayList<PerkListItem> perks) {
            super(context, -1, perks);
            this.context = context;
            this.perks = perks;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.perk_customization_list_row, parent, false);

            TextView title = (TextView) rowView.findViewById(R.id.perk_title);
            TextView amountAvailable = (TextView) rowView.findViewById(R.id.amount_available);

            title.setText(perks.get(position).perk.title);
            amountAvailable.setText(Integer.toString(perks.get(position).amountAvailable));

            return rowView;
        }
    }


}


