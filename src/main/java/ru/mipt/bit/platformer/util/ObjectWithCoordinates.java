package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;

public abstract class ObjectWithCoordinates extends AbstractTexturedObject {
    protected GridPoint2 coordinates;

    public ObjectWithCoordinates(Batch batch, Texture texture, GridPoint2 coordinates) {
        super(batch, texture);
        this.coordinates = coordinates;
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }
}
