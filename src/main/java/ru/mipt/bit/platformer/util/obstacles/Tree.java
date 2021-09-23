package ru.mipt.bit.platformer.util.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.AbstractTexturedObject;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Tree extends AbstractObstacle {
    public Tree(Batch batch, Texture texture, TiledMapTileLayer ground, GridPoint2 coordinates) {
        super(batch, texture, coordinates);;
        moveRectangleAtTileCenter(ground, rectangle, coordinates);
    }
}
