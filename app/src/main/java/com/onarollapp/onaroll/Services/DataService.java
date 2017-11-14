package com.onarollapp.onaroll.Services;

import com.onarollapp.onaroll.Perks.Perk;
import com.onarollapp.onaroll.ShopModels.PerkShopItem;
import com.onarollapp.onaroll.ShopModels.ShopItem;
import com.onarollapp.onaroll.Users.Player;

/**
 * Created by Curt on 11/14/2017.
 */

public class DataService {
    private static final DataService instance = new DataService();

    DataService() {
    }

    public static DataService getInstance() {
        return instance;
    }


    // Get Player object from DB by ID
    public Player getPlayer(String playerId) {
        return null;
    }

    // Update Player object in DB
    public void updatePlayer(Player player) {

    }

    // Get ShopItem object from DB by ID
    public ShopItem getShopItem(String shopId) {
        return null;
    }

    // Get Perk object from DB by ID
    public Perk getPerk(String perkId) {
        return null;
    }
}
