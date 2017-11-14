package com.onarollapp.onaroll.ShopModels;

import com.onarollapp.onaroll.Perks.Perk;

/**
 * Created by Curt on 11/14/2017.
 */

public class PerkShopItem extends ShopItem {

    PerkShopItem(Perk perk, int price, String shopId, String title, String description) {
        this.itemId = perk.id;
        this.price = price;
        this.shopId = shopId;
        this.title = title;
        this.description = description;
    }
}
