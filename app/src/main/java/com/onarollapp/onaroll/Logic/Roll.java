package com.onarollapp.onaroll.Logic;

import com.onarollapp.onaroll.Perks.Perk;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Curt on 11/12/2017.
 */

public class Roll {

    public static GameState Roll(ArrayList<Perk> perks, GameState gameState) {

        Random rand = new Random();
        double oneModifier = 100;
        double twoModifer = 100;
        double threeModifer = 100;
        double fourModifer = 100;
        double fiveModifer = 100;
        double sixModifer = 100;
        double sevenModifer = 0;
        double eightModifer = 0;

        int dieModifer = 2;

        double total = 0;

        //Get all roll
        for(int i = 0; i < perks.size(); i++) {
            oneModifier = perks.get(i).oneModifier + oneModifier;
            twoModifer = perks.get(i).twoModifer + twoModifer;
            threeModifer = perks.get(i).threeModifer + threeModifer;
            fourModifer = perks.get(i).fourModifer + fourModifer;
            fiveModifer = perks.get(i).fiveModifer + fiveModifer;
            sixModifer = perks.get(i).sixModifer + sixModifer;
            sevenModifer = perks.get(i).sevenModifer + sevenModifer;
            eightModifer = perks.get(i).eightModifer + eightModifer;

            dieModifer = perks.get(i).dieModifier + dieModifer;
        }

        total = oneModifier + twoModifer + threeModifer + fourModifer + fiveModifer + sixModifer + sevenModifer + eightModifer;

        ArrayList<Integer> rollResultsList = new ArrayList<Integer>();

        for(int i = 0; i < dieModifer; i++) {
            // Get a random decimal between 0 and 1

            double randNum = rand.nextDouble();

            if(randNum < oneModifier / total) {
                rollResultsList.add(1);
            } else if(randNum < twoModifer / total) {
                rollResultsList.add(2);
            } else if(randNum < threeModifer / total) {
                rollResultsList.add(3);
            } else if(randNum < fourModifer / total) {
                rollResultsList.add(4);
            } else if(randNum < fiveModifer / total) {
                rollResultsList.add(5);
            } else if(randNum < sixModifer / total) {
                rollResultsList.add(6);
            } else if(randNum < sevenModifer / total) {
                rollResultsList.add(7);
            } else if(randNum < eightModifer / total) {
                rollResultsList.add(8);
            } else {
                rollResultsList.add(0);
            }


        }


        RollResult rollResult = new RollResult(rollResultsList);
        // Run all event listeners for each Perk

        for(int i = 0; i < perks.size(); i++) {
            gameState = perks.get(i).onRoll(gameState);
        }

        return gameState;

    }



}
