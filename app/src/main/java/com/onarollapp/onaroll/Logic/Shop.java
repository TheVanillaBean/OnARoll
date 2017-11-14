package com.onarollapp.onaroll.Logic;

import android.provider.ContactsContract;

import com.onarollapp.onaroll.Perks.Perk;
import com.onarollapp.onaroll.Services.DataService;
import com.onarollapp.onaroll.ShopModels.ShopItem;
import com.onarollapp.onaroll.Users.Player;

/**
 * Created by Curt on 11/14/2017.
 */

public class Shop {
    public  static boolean buyItem(String shopId, String playerId) {
        DataService ds = DataService.getInstance();
        ShopItem item = ds.getShopItem(shopId);
        Player player = ds.getPlayer(playerId);
        Perk perk = ds.getPerk(item.itemId);


        if(player.credits > item.price) {
            player.credits = player.credits - item.price;
            player.perkList.add(perk);
            ds.updatePlayer(player);
            return true;
        } else {
            // Player could not afford the item
            return false;
        }
    }


}
