package ru.mipt.bit.platformer.util.generators;

import ru.mipt.bit.platformer.util.levels.Level;

/**
 * Порт в системе портов и адаптеров
 * Application layer
 */
public interface LevelGenerator {
    Level createLevel();
}
