package me.ifmo.common.data;

/**
 * Enum to represent the headers of the table.
 */

public enum TableDataHeaders{
    id,
    name,
    coordinateX,
    coordinateY,
    localDate,
    age,
    color,
    type,
    character,
    numberOfTreasures;

    /**
     * toString method for the DragonType enum.
     * @return Information about an object of type DragonType.
     */

    public static String showList() {
        StringBuilder headersList = new StringBuilder("Available headings:\n");
        for(TableDataHeaders header : values()){
            headersList.append(header).append("\n");
        }
        return headersList.substring(0, headersList.length() - 1);
    }
}