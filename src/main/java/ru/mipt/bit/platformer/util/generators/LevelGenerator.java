package ru.mipt.bit.platformer.util.generators;

import ru.mipt.bit.platformer.util.levels.Level;

public interface LevelGenerator {
    Level createLevel();
}
