package ru.mipt.bit.platformer.util.obstacles;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.RenderableObject;

public final class Tree implements RenderableObject {
    GridPoint2 coordinates;

    public Tree(GridPoint2 coordinates) {
        this.coordinates = coordinates;
    }

    public float getRotation() {
        return 0f;
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }
}
