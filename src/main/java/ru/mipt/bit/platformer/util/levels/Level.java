package ru.mipt.bit.platformer.util.levels;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.ObjectWithCoordinates;
import ru.mipt.bit.platformer.util.control.ControlCommand;
import ru.mipt.bit.platformer.util.obstacles.Tree;
import ru.mipt.bit.platformer.util.players.TankPlayer;
import ru.mipt.bit.platformer.util.players.moveStrategies.MoveStrategy;
import ru.mipt.bit.platformer.util.players.moveStrategies.SimpleMoveStrategy;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class Level {
    private final TankPlayer player;

    private final ArrayList<ObjectWithCoordinates> obstacles = new ArrayList<>();

    // Пока нет других игроков, кажется, что правильнее будет делать его
    private final ArrayList<TankPlayer> enemies = new ArrayList<>();

    private final GridPoint2 bounds;

    private final MoveStrategy enemyMoveStrategy;

    private TankPlayer createPlayer(GridPoint2 position) {
        return new TankPlayer(position, new SimpleMoveStrategy(this));
    }

    public Level(GridPoint2 bounds, GridPoint2 playerStartPosition) {
        this.bounds = bounds;
        player = createPlayer(playerStartPosition);
        enemyMoveStrategy = new SimpleMoveStrategy(this);
    }

    public void addObstacles(Collection<GridPoint2> positions) {
        obstacles.addAll(
                positions.stream()
                        .map(Tree::new)
                        .collect(Collectors.toList())
        );
    }

    public void addEnemies(Collection<GridPoint2> positions) {
        enemies.addAll(
                positions.stream().map((value) -> new TankPlayer(value, enemyMoveStrategy)).collect(Collectors.toList())
        );
    }

    public TankPlayer getPlayer() {
        return player;
    }

    public ArrayList<ObjectWithCoordinates> getRenderableObjects() {
        ArrayList<ObjectWithCoordinates> result = new ArrayList<>();

        result.add(player);
        result.addAll(obstacles);
        result.addAll(enemies);

        return result;
    }

    public void processMoveToDestination(float deltaTime, float speed) {
        if (player != null) {
            player.processMoveToDestination(deltaTime, speed);
        }

        for (TankPlayer enemy : enemies) {
            enemy.processMoveToDestination(deltaTime, speed);
        }
    }

    public void processAIMovements(ControlCommand command) {
        command.execute();
    }

    public boolean hasObject(GridPoint2 point) {
        // obstacles are few, because of it we can use linear algo
        for (ObjectWithCoordinates obstacle : obstacles) {
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

        if (player != null && point.equals(player.getCoordinates())) {
            return true;
        }

        return player != null && point.equals(player.getDestinationCoordinates());
    }

    public int getWidth() {
        return bounds.x;
    }

    public int getHeight() {
        return bounds.y;
    }

    public List<ObjectWithCoordinates> getObstacles() {
        return obstacles;
    }

    public List<TankPlayer> getEnemies() {
        return enemies;
    }

    public Optional<TankPlayer> getPlayerByCoords(GridPoint2 point) {
        Optional<TankPlayer> founded = enemies.stream()
                .filter((enemy) -> enemy.getCoordinates().equals(point))
                .findFirst();

        if (player.getCoordinates().equals(point)) {
            return founded.or(() -> Optional.of(player));
        }

        return founded;
    }
}
