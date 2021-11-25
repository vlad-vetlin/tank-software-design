package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;

import java.util.UUID;

public interface RenderableObjectWithCoordinates {
    float getRotation();

    UUID getId();

    GridPoint2 getCoordinates();
}
