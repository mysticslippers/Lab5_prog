package me.ifmo.common.interfaces;

/**
 * Functional interface for implementing the lambda expression of the add_if_max command.
 */

@FunctionalInterface
public interface AddIFMax{
    boolean addIfMax(long ageInputDragon, long ageMaxDragon);
}