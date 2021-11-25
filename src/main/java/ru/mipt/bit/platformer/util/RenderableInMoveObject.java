package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;

public interface RenderableInMoveObject extends RenderableObjectWithCoordinates {
    GridPoint2 getDestinationCoordinates();

    float getMovementProgress();
}
