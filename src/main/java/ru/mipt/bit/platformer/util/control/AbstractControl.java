package ru.mipt.bit.platformer.util.control;

import ru.mipt.bit.platformer.util.levels.Level;
import ru.mipt.bit.platformer.util.players.TankPlayer;

public interface AbstractControl {
    void processMovement(Level level, TankPlayer player);
}
