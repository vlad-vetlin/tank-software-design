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
import java.util.Collection;
import java.util.stream.Collectors;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public final class Level {
    private final TiledMapTileLayer groundLayer;

    private TankPlayer player;

    private final ArrayList<RenderableObject> obstacles = new ArrayList<>();

    private TankPlayer createPlayer(Rectangle rectangle, GridPoint2 position) {
        TankPlayer player = new TankPlayer(
                rectangle,
                position,
                new TileMovement(groundLayer, Interpolation.smooth)
        );
        player.setMoveStrategy(new SimpleMoveStrategy(this));

        return player;
    }

    public void addPlayer(Rectangle playerRect, GridPoint2 position) {
        this.player = createPlayer(playerRect, position);
    }

    public void addObstacles(Rectangle obstacleRect, Collection<GridPoint2> positions) {
        this.obstacles.addAll(
                positions.stream()
                        .map(position -> new Tree(obstacleRect, groundLayer, position))
                        .collect(Collectors.toList())
        );
    }

    public Level(TiledMap levelMap) {
        // load level tiles
        groundLayer = getSingleLayer(levelMap);
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
        for (RenderableObject obstacle : obstacles) {
            if (point.equals(obstacle.getCoordinates())) {
                return true;
            }
        }

        return false;
    }
}
