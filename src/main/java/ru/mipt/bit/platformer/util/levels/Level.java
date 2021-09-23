package ru.mipt.bit.platformer.util.levels;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.util.ICanBeRendered;
import ru.mipt.bit.platformer.util.ICanMove;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.util.obstacles.AbstractObstacle;
import ru.mipt.bit.platformer.util.obstacles.Tree;
import ru.mipt.bit.platformer.util.players.AbstractPlayer;
import ru.mipt.bit.platformer.util.players.TankPlayer;
import ru.mipt.bit.platformer.util.players.moveStrategies.SimpleMoveStrategy;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class Level implements ICanBeRendered, Disposable {
    private Batch batch;

    private TiledMap levelMap;
    private MapRenderer renderer;

    private TiledMapTileLayer groundLayer;

    Map map;

    public TiledMapTileLayer getGroundLayer() {
        return groundLayer;
    }

    public Level() {
        batch = new SpriteBatch();

        // load level tiles
        levelMap = new TmxMapLoader().load("level.tmx");
        // create renderer for map
        renderer = createSingleLayerMapRenderer(levelMap, batch);

        // create ground
        groundLayer = getSingleLayer(levelMap);

        map = new Map();

        AbstractPlayer player = new TankPlayer(
                batch,
                new Texture("images/tank_blue.png"),
                new GridPoint2(1, 1),
                new TileMovement(groundLayer, Interpolation.smooth)
        );

        AbstractObstacle obstacle = new Tree(
                batch,
                new Texture("images/greenTree.png"),
                groundLayer,
                new GridPoint2(1, 3)
        );

        player.setMoveStrategy(new SimpleMoveStrategy(map));

        map.addPlayer(player);
        map.addObstacle(obstacle);
    }

    // TODO возможно добавить класс основного игрока, ибо он всегда должен реализовывать интерфейс ICanMove
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
}
