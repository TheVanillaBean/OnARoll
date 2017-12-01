package com.onarollapp.onaroll.Users;

import com.onarollapp.onaroll.Perks.Perk;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Curt on 11/14/2017.
 */

public class Player {
    public String id;
    public int credits;

    public ArrayList<Perk> perkList;
    public Perk[] equippedPerks = new Perk[7];
}
