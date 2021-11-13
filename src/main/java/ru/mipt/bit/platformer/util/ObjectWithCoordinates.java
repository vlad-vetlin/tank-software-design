package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;

public interface ObjectWithCoordinates {
    float getRotation();

    GridPoint2 getCoordinates();
}
