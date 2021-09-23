package ru.mipt.bit.platformer.util.obstacles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;

public final class TreeObstacleFactory implements IObstacleFactory {
    private String texturePath;
    private Batch batch;
    private TiledMapTileLayer ground;

    private static TreeObstacleFactory instance = null;

    private TreeObstacleFactory() {}

    public static TreeObstacleFactory getInstance() {
        if (instance == null) {
            return instance = new TreeObstacleFactory();
        }

        return instance;
    }

    @Override
    public AbstractObstacle createObstacle(GridPoint2 point) {
        return new Tree(batch, new Texture(texturePath), ground, point);
    }

    @Override
    public void setTexturePath(String path) {
        texturePath = path;
    }

    @Override
    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    @Override
    public void setGround(TiledMapTileLayer ground) {
        this.ground = ground;
    }
}
