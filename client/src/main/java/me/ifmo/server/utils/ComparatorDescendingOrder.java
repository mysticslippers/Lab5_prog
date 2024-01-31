package me.ifmo.server.utils;

import me.ifmo.common.data.Dragon;

import java.util.Comparator;
import java.util.Objects;

/**
 * The class that sets the sorting rules.
 */

public class ComparatorDescendingOrder implements Comparator<Dragon>{

    /**
     * Method for sorting the collection by the numberOfTreasures field of the DragonCave class in descending order.
     * @param dragon1 - The first object of type Dragon.
     * @param dragon2 - The second object of type Dragon.
     * @return Returns the result of the comparison.
     */

    @Override
    public int compare(Dragon dragon1, Dragon dragon2){
        if(dragon1.getCave().getNumberOfTreasures() > dragon2.getCave().getNumberOfTreasures()){
            return -1;
        }
        else if(Objects.equals(dragon1.getCave().getNumberOfTreasures(), dragon2.getCave().getNumberOfTreasures())){
            return 0;
        }
        else{
            return 1;
        }
    }
}