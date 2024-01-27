package com.mygdx.game.Tools;

import com.mygdx.game.Objects.Item;
import java.util.LinkedList;

public class FunCalculator {
    
    public float evaluate(LinkedList<Item> items, Constants.COMEDYTYPE favouredType) {
        float rating = 0;
        int typeMatches = 0;
        boolean perfectMatch = false;
        for (Item item : items) {
            rating += item.getLaughRating();
            if (item.getComedytype() == favouredType) typeMatches++;
            for (Item newItem : items) {
                if (item.getPerfectMatch().equals(newItem.getName())) perfectMatch = true;
            }
        }
        rating += typeMatches / 10f;
        if (perfectMatch) rating *= 2;
        return rating;
    }

}
