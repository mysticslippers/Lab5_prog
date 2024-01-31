package me.ifmo.common.data;

/**
 * Enum to represent the DragonType field.
 */

public enum DragonType{
    WATER,
    UNDERGROUND,
    AIR,
    FIRE;

    /**
     * toString method for the DragonType enum.
     * @return Information about an object of type DragonType.
     */

    public static String showList() {
        StringBuilder dragonTypeList = new StringBuilder("Available types:\n");
        for(DragonType type : values()){
            dragonTypeList.append(type).append("\n");
        }
        return dragonTypeList.substring(0, dragonTypeList.length() - 1);
    }
}