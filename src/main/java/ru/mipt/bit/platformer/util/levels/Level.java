package ru.mipt.bit.platformer.util.levels;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.RenderableObject;
import ru.mipt.bit.platformer.util.control.AIControl;
import ru.mipt.bit.platformer.util.control.AbstractControl;
import ru.mipt.bit.platformer.util.obstacles.Tree;
import ru.mipt.bit.platformer.util.players.TankPlayer;
import ru.mipt.bit.platformer.util.players.moveStrategies.MoveStrategy;
import ru.mipt.bit.platformer.util.players.moveStrategies.SimpleMoveStrategy;


import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;


public final class Level {
    private TankPlayer player;

    private final ArrayList<RenderableObject> obstacles = new ArrayList<>();

    // Пока нет других игроков, кажется, что правильнее будет делать его
    private final ArrayList<TankPlayer> enemies = new ArrayList<>();

    private final AbstractControl aiControl;

    private final GridPoint2 bounds;

    private TankPlayer createPlayer(GridPoint2 position) {
        return new TankPlayer(position, new SimpleMoveStrategy(this));
    }

    public Level(GridPoint2 bounds) {
        aiControl = new AIControl();
        this.bounds = bounds;
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

    public void addEnemies(Collection<GridPoint2> positions) {
        MoveStrategy moveStrategy = new SimpleMoveStrategy(this);
        this.enemies.addAll(
                positions.stream().map((value) -> new TankPlayer(value, moveStrategy)).collect(Collectors.toList())
        );
    }

    public TankPlayer getPlayer() {
        return player;
    }

    public ArrayList<RenderableObject> getRenderableObjects() {
        ArrayList<RenderableObject> result = new ArrayList<>();

        result.add(player);
        result.addAll(obstacles);
        result.addAll(enemies);

        return result;
    }

    public void processMoveToDestination(float deltaTime, float speed) {
        player.processMoveToDestination(deltaTime, speed);

        for (TankPlayer enemy : enemies) {
            enemy.processMoveToDestination(deltaTime, speed);
        }
    }

    public void processAIMovements() {
        for (TankPlayer enemy : enemies) {
            aiControl.processMovement(enemy);
        }
    }

    public boolean hasObject(GridPoint2 point) {
        // obstacles are few, because of it we can use linear algo
        for (RenderableObject obstacle : obstacles) {
            if (point.equals(obstacle.getCoordinates())) {
                return true;
            }
        }

        for (TankPlayer enemy : enemies) {
            if (point.equals(enemy.getCoordinates())) {
                return true;
            }

            if (point.equals(enemy.getDestinationCoordinates())) {
                return true;
            }
        }

        if (point.equals(player.getCoordinates())) {
            return true;
        }

        return point.equals(player.getDestinationCoordinates());
    }

    public int getWidth() {
        return bounds.x;
    }

    public int getHeight() {
        return bounds.y;
    }
}
