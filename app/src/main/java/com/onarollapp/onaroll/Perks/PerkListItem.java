package com.onarollapp.onaroll.Perks;

/**
 * Created by Curt on 11/14/2017.
 */

public class PerkListItem {
    public int amountAvailable;
    public Perk perk;

    public PerkListItem(Perk perk, int amountAvailable) {
        this.amountAvailable = amountAvailable;
        this.perk = perk;
    }
}
