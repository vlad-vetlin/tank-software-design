package ru.mipt.bit.platformer.util.obstacles;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.RenderableObject;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public final class Tree implements RenderableObject {
    Rectangle rectangle;

    GridPoint2 coordinates;

    public Tree(Rectangle rectangle, TiledMapTileLayer ground, GridPoint2 coordinates) {
        this.coordinates = coordinates;

        this.rectangle = new Rectangle();
        this.rectangle.setWidth(rectangle.getWidth());
        this.rectangle.setHeight(rectangle.getHeight());

        moveRectangleAtTileCenter(ground, this.rectangle, coordinates);
    }

    @Override
    public Rectangle getCoordsAsRect() {
        return rectangle;
    }

    @Override
    public float getRotation() {
        return 0f;
    }

    @Override
    public GridPoint2 getCoordinates() {
        return coordinates;
    }
}
