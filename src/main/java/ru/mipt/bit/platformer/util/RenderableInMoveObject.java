package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;

/**
 * Adapter в системе портов и адаптеров
 * InfrastructureLayer
 */
public interface RenderableInMoveObject extends RenderableObjectWithCoordinates {
    GridPoint2 getDestinationCoordinates();

    float getMovementProgress();
}
