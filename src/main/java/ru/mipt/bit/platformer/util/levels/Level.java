package ru.mipt.bit.platformer.util.levels;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.RenderableObject;
import ru.mipt.bit.platformer.util.obstacles.Tree;
import ru.mipt.bit.platformer.util.players.TankPlayer;
import ru.mipt.bit.platformer.util.players.moveStrategies.SimpleMoveStrategy;


import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;


public final class Level {
    private TankPlayer player;

    private final ArrayList<RenderableObject> obstacles = new ArrayList<>();

    private TankPlayer createPlayer(GridPoint2 position) {
        TankPlayer player = new TankPlayer(position);
        player.setMoveStrategy(new SimpleMoveStrategy(this));

        return player;
    }

    public void addPlayer(GridPoint2 position) {
        this.player = createPlayer(position);
    }

    public void addObstacles(Collection<GridPoint2> positions) {
        this.obstacles.addAll(
                positions.stream()
                        .map(Tree::new)
                        .collect(Collectors.toList())
        );
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
