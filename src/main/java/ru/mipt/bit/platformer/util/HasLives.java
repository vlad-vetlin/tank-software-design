package ru.mipt.bit.platformer.util;

/**
 * Adapter в системе портов и адаптеров
 * ApplicationLayer
 */
public interface HasLives {
    boolean processDamage();

    int getLives();
}
