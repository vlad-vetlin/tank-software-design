package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;

public interface Movable {
    void processMoveToDestination(float deltaTime, float speed);

    GridPoint2 getCoordinates();
}
