package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;

import java.util.UUID;

public abstract class AbstractObjectWithCoordinates implements RenderableObjectWithCoordinates {
    /**
     * В данный момент есть 4 значения
     * 90f - UP
     * -90f - DOWN
     * -180f - LEFT
     * 0f - Right
     */
    protected float rotation;

    private final UUID id = UUID.randomUUID();

    protected GridPoint2 coordinates;

    public AbstractObjectWithCoordinates(GridPoint2 coordinates, float rotation) {
        this.coordinates = coordinates;
        this.rotation = rotation;
    }

    public float getRotation() {
        return rotation;
    }

    public UUID getId() {
        return id;
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }
}
