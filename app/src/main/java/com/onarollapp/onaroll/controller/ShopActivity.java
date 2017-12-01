package com.onarollapp.onaroll.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.onarollapp.onaroll.ShopModels.PerkShopItem;
import com.onarollapp.onaroll.ShopModels.ShopItem;
import com.onarollapp.onaroll.Users.Player;

import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perk_customization);

        DataService ds = DataService.getInstance();
        ArrayList<ShopItem> shopItems = ds.getShopItems();
        Player player = ds.getLoggedInPlayer();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Shop - $" + player.credits);

        ListView yourPerkList = (ListView) findViewById(R.id.shop_list);

        final ShopListAdapter adapter = new ShopListAdapter(this, shopItems);
        yourPerkList.setAdapter(adapter);


        yourPerkList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                DataService ds = DataService.getInstance();
                Player player = ds.getLoggedInPlayer();
                ds.buyPerkShopItem("sds");
                ds.buyPerkShopItem(new PerkShopItem(new EmptyPerk(), 10, "2121"));
                if(ds.buyPerkShopItem((PerkShopItem)adapter.shopItems.get(position))) {
                    ActionBar actionBar = getSupportActionBar();
                    actionBar.setTitle("Shop - $" + player.credits);
                } 

            }
        });


    }





    private class ShopListAdapter extends ArrayAdapter<ShopItem> {
        private final Context context;
        private final ArrayList<ShopItem> shopItems;

        public ShopListAdapter(Context context, ArrayList<ShopItem> shopItems) {
            super(context, -1, shopItems);
            this.context = context;
            this.shopItems = shopItems;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.perk_customization_list_row, parent, false);

            TextView title = (TextView) rowView.findViewById(R.id.shop_item_title);
            title.setText(shopItems.get(position).title);


            TextView description = (TextView) rowView.findViewById(R.id.shop_item_description);
            description.setText(shopItems.get(position).description);

            Button buyButton = (Button) rowView.findViewById(R.id.shop_item_buy_button);
            buyButton.setText("Buy $" + shopItems.get(position).price);



            return rowView;
        }
    }


}


