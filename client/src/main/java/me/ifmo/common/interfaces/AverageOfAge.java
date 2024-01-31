package me.ifmo.common.interfaces;

/**
 * Functional interface for implementing the lambda expression of the average of age command.
 */

@FunctionalInterface
public interface AverageOfAge{
    long sumAge(long summa, long dragonAge);
}