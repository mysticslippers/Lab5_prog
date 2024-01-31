package me.ifmo.common.interfaces;

import me.ifmo.common.data.DragonCharacter;

/**
 * Functional interface for implementing the lambda expression of the remove_all_by_character command.
 */

@FunctionalInterface
public interface RemoveAllByCharacter{
    boolean containsCharacter(DragonCharacter characterInput, DragonCharacter dragonCharacter);
}