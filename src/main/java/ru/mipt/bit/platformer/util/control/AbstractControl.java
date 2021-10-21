package ru.mipt.bit.platformer.util.control;

import ru.mipt.bit.platformer.util.Movable;

public interface AbstractControl {
    void processMovement(Movable movable);
}
