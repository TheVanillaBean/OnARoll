package com.onarollapp.onaroll.Services;

import com.onarollapp.onaroll.Perks.IncreaseChanceToRollFive;
import com.onarollapp.onaroll.Perks.Perk;
import com.onarollapp.onaroll.ShopModels.PerkShopItem;
import com.onarollapp.onaroll.ShopModels.ShopItem;
import com.onarollapp.onaroll.Users.Player;

import java.util.ArrayList;

/**
 * Created by Curt on 11/14/2017.
 */

public class DataService {
    private static final DataService instance = new DataService();

    private DataService() {
    }

    public static DataService getInstance() {
        return instance;
    }


    // Return the player object of the user currently logged in
    public Player getLoggedInPlayer() {
        Player player = new Player();
        player.credits = 200;
        player.id = "2";
        return player;
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

    // Get all purchaseable shop items
    public ArrayList<ShopItem> getShopItems() {
        ArrayList<ShopItem> shopItems = new ArrayList<ShopItem>();
        shopItems.add(new PerkShopItem(new IncreaseChanceToRollFive(), 100, "21"));
        return null;
    }

    // Buy ShopItem for logged in player
    // Return success or not
    public boolean buyPerkShopItem(String shopId) {
        return this.buyPerkShopItem((PerkShopItem)this.getShopItem(shopId));
    }

    public boolean buyPerkShopItem(PerkShopItem shopItem) {
        Player player = this.getLoggedInPlayer();
        if(player.credits >= shopItem.price) {
            player.credits = player.credits - shopItem.price;
            player.perkList.add(shopItem.perk);
            this.updatePlayer(player);
            return true;
        } else {
            return false;
        }
    }

    // Get Perk object from DB by ID
    public Perk getPerk(String perkId) {
        return null;
    }
}
