package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;

public interface RenderableObject {
    float getRotation();

    GridPoint2 getCoordinates();
}
