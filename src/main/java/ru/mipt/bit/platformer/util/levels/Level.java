package ru.mipt.bit.platformer.util.levels;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.RenderableObject;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.util.obstacles.Tree;
import ru.mipt.bit.platformer.util.players.TankPlayer;
import ru.mipt.bit.platformer.util.players.moveStrategies.SimpleMoveStrategy;


import java.util.ArrayList;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public final class Level {
    private final TiledMapTileLayer groundLayer;

    private final GridPoint2 PLAYER_START_POSITION = new GridPoint2(1, 1);

    private TankPlayer player;

    private final ArrayList<Tree> obstacles = new ArrayList<>();

    TankPlayer createPlayer(Rectangle rectangle) {
        TankPlayer player = new TankPlayer(
                rectangle,
                PLAYER_START_POSITION,
                new TileMovement(groundLayer, Interpolation.smooth)
        );
        player.setMoveStrategy(new SimpleMoveStrategy(this));

        return player;
    }

    private void initMap(Rectangle playerRect, Rectangle obstacleRect) {
        this.player = createPlayer(playerRect);

        this.obstacles.add(new Tree(obstacleRect, groundLayer, new GridPoint2(1, 3)));
        this.obstacles.add(new Tree(obstacleRect, groundLayer, new GridPoint2(2, 5)));
    }

    public Level(TiledMap levelMap, Rectangle playerRect, Rectangle obstacleRect) {
        // load level tiles
        groundLayer = getSingleLayer(levelMap);

        initMap(playerRect, obstacleRect);
    }

    public TankPlayer getPlayer() {
        return player;
    }

    public ArrayList<RenderableObject> getRenderableObjects() {
        ArrayList<RenderableObject> result = new ArrayList<>();

        result.add(player);
        result.addAll(obstacles);

        return result;
    }

    public void processMoveToDestination(float deltaTime, float speed) {
        player.processMoveToDestination(deltaTime, speed);
    }

    public boolean hasObstacle(GridPoint2 point) {
        // obstacle are few, because of it we can use linear algo
        for (Tree obstacle : obstacles) {
            if (point.equals(obstacle.getCoordinates())) {
                return true;
            }
        }

        return false;
    }
}
