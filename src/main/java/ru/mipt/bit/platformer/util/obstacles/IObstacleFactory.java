package ru.mipt.bit.platformer.util.obstacles;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;

public interface IObstacleFactory {
    void setTexturePath(String path);

    void setBatch(Batch batch);

    void setGround(TiledMapTileLayer ground);

    AbstractObstacle createObstacle(GridPoint2 point);
}
