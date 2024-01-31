package me.ifmo.common.data;

/**
 * Enum to represent the DragonCharacter field.
 */

public enum DragonCharacter{
    WISE,
    EVIL,
    GOOD,
    CHAOTIC_EVIL;

    /**
     * toString method for the DragonCharacter enum.
     * @return Information about an object of type DragonCharacter.
     */

    public static String showList(){
        StringBuilder dragonCharacterList = new StringBuilder("Available characters:\n");
        for(DragonCharacter character : values()){
            dragonCharacterList.append(character).append("\n");
        }
        return dragonCharacterList.substring(0, dragonCharacterList.length() - 1);
    }
}