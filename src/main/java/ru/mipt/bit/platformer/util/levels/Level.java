package ru.mipt.bit.platformer.util.levels;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.util.ICanBeRendered;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.util.obstacles.IObstacleFactory;
import ru.mipt.bit.platformer.util.obstacles.TreeObstacleFactory;
import ru.mipt.bit.platformer.util.players.AbstractPlayer;
import ru.mipt.bit.platformer.util.players.TankPlayer;
import ru.mipt.bit.platformer.util.players.moveStrategies.SimpleMoveStrategy;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public final class Level implements ICanBeRendered, Disposable {
    private final Batch batch;
    private final MapRenderer renderer;
    private final TiledMapTileLayer groundLayer;

    private final GridPoint2 PLAYER_START_POSITION = new GridPoint2(1, 1);

    Map map;

    AbstractPlayer createPlayer() {
        AbstractPlayer player = new TankPlayer(
                batch,
                new Texture("images/tank_blue.png"),
                PLAYER_START_POSITION,
                new TileMovement(groundLayer, Interpolation.smooth)
        );
        player.setMoveStrategy(new SimpleMoveStrategy(map));

        return player;
    }

    IObstacleFactory createObstacleFactory() {
        IObstacleFactory treeFactory = TreeObstacleFactory.getInstance();
        treeFactory.setBatch(batch);
        treeFactory.setGround(groundLayer);
        treeFactory.setTexturePath("images/greenTree.png");

        return treeFactory;
    }

    private void initMap() {
        map = new Map();

        AbstractPlayer player = createPlayer();
        IObstacleFactory treeFactory = createObstacleFactory();

        map.addPlayer(player);
        map.addObstacle(treeFactory.createObstacle(new GridPoint2(1, 3)));
        map.addObstacle(treeFactory.createObstacle(new GridPoint2(2, 5)));
    }

    public Level() {
        batch = new SpriteBatch();

        // load level tiles
        TiledMap levelMap = new TmxMapLoader().load("level.tmx");
        renderer = createSingleLayerMapRenderer(levelMap, batch);
        groundLayer = getSingleLayer(levelMap);

        initMap();
    }

    public AbstractPlayer getPlayer() {
        return map.getPlayer();
    }

    @Override
    public void render() {
        renderer.render();

        // start recording all drawing commands
        batch.begin();

        map.render();

        // submit all drawing requests
        batch.end();
    }

    @Override
    public void dispose() {
        map.dispose();
        batch.dispose();
    }

    public void processMoveToDestination(float deltaTime, float speed) {
        map.getPlayer().processMoveToDestination(deltaTime, speed);
    }
}
