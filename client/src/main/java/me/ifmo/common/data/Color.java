package me.ifmo.common.data;

/**
 * Enum to represent the Color field.
 */

public enum Color{
    RED,
    BLACK,
    ORANGE,
    WHITE,
    BROWN;

    /**
     * toString method for the Color enum.
     * @return Information about an object of type Color.
     */

    public static String showList(){
        StringBuilder colorList = new StringBuilder("Available colors:\n");
        for(Color color : values()){
            colorList.append(color).append("\n");
        }
        return colorList.substring(0, colorList.length() - 1);
    }
}