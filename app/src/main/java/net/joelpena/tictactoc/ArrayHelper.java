package net.joelpena.tictactoc;

/**
 * Created by Joel on 2/9/2017.
 */

public class ArrayHelper {

    public static boolean arrayContainsThisKey(int [] array, int key){
        for (int value: array) {
            if(value == key) return true;
        }

        return false;
    }

    public static boolean thisPositionWasPlayed(int [] array, int position){
        return array[position] == 1 || array[position] == 2;
    }
}
