package com.onarollapp.onaroll.ShopModels;

/**
 * Created by Curt on 11/14/2017.
 */

public abstract class ShopItem {
    // This ID is unique to all items in the shop
    public String shopId;
    // This ID is unique to all items of that item type
    public String itemId;

    public int price;
    public String title;
    public String description;
}
