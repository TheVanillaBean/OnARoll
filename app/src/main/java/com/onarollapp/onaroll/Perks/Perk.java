package com.onarollapp.onaroll.Perks;

import com.onarollapp.onaroll.Controller.RollEvent;
import com.onarollapp.onaroll.Controller.RollResult;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * Created by Curt on 11/12/2017.
 */

public abstract class Perk {

    public double oneModifier = 0;
    public double twoModifer = 0;
    public double threeModifer = 0;
    public double fourModifer = 0;
    public double fiveModifer = 0;
    public double sixModifer = 0;
    public double sevenModifer = 0;
    public double eightModifer = 0;

    public abstract RollResult onRoll(RollResult roll);

}
