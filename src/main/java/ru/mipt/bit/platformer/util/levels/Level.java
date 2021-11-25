package ru.mipt.bit.platformer.util.levels;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.BulletFactory;
import ru.mipt.bit.platformer.util.ControlCommand;
import ru.mipt.bit.platformer.util.obstacles.Bullet;
import ru.mipt.bit.platformer.util.players.TankPlayer;
import ru.mipt.bit.platformer.util.players.moveStrategies.BulletMoveStrategy;
import ru.mipt.bit.platformer.util.players.moveStrategies.SimpleMoveStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Level {
    private final ObjectsRepository repository;

    private final GridPoint2 bounds;

    private TankPlayer createPlayer(GridPoint2 position, BulletFactory callback) {
        return new TankPlayer(position, new SimpleMoveStrategy(this), callback);
    }

    public Level(GridPoint2 bounds, GridPoint2 playerStartPosition) {
        this(bounds, playerStartPosition, List.of(), List.of());
    }

    public Level(
            GridPoint2 bounds,
            GridPoint2 playerStartPosition,
            Collection<GridPoint2> obstacles,
            Collection<GridPoint2> enemies
    ) {
        repository = new ObjectsRepository();
        BulletFactory callback = (parent, position, rotation) -> repository.addBullet(
                new Bullet(
                        position,
                        rotation,
                        new BulletMoveStrategy(this),
                        parent
                )
        );

        this.bounds = bounds;

        repository.setPlayer(createPlayer(playerStartPosition, callback));
        repository.addObstacles(obstacles);
        repository.addEnemies(enemies, new SimpleMoveStrategy(this), callback);
    }

    public TankPlayer getPlayer() {
        return repository.getPlayer();
    }

    public void processAIMovements(ControlCommand command) {
        command.execute();
    }

    public int getWidth() {
        return bounds.x;
    }

    public int getHeight() {
        return bounds.y;
    }

    public ObjectsRepository getRepository() {
        return repository;
    }

    private void processPlayerMove(float deltaTime, float speed) {
        TankPlayer player = repository.getPlayer();
        if (player != null) {
            player.processOneTick(deltaTime, speed);
        }
    }

    private void processEnemiesMove(float deltaTime, float speed) {
        for (TankPlayer enemy : repository.getEnemies()) {
            enemy.processOneTick(deltaTime, speed);
        }
    }

    private void processBulletsMove(float deltaTime, float speed) {
        List<Bullet> bulletsToDelete = new ArrayList<>();
        for (Bullet bullet : repository.getBullets()) {
            if (!bullet.moveForward()) {
                bulletsToDelete.add(bullet);
            }
            bullet.processOneTick(deltaTime, speed);
        }

        for (Bullet bullet : bulletsToDelete) {
            repository.removeBullet(bullet);
        }
    }

    private void checkObstacleCollisions() {
        List<Bullet> bulletsToDelete = new ArrayList<>();
        for (Bullet bullet : repository.getBullets()) {
            if (repository.getObstacleByCoords(bullet.getCoordinates()) != null) {
                bulletsToDelete.add(bullet);
            }
        }

        for (Bullet bullet : bulletsToDelete) {
            repository.removeBullet(bullet);
        }
    }

    private TankPlayer checkEnemyCollision(GridPoint2 point, boolean isStart) {
        TankPlayer enemy = repository.getEnemyByCoords(point);
        if (enemy == null) {
            return null;
        }

        if (isStart && enemy.getMovementProgress() > 0.5f) {
            return null;
        }

        if (!isStart && enemy.getMovementProgress() < 0.5f) {
            return null;
        }

        if (enemy.getCoordinates().equals(point)) {
            return enemy;
        }

        return null;
    }

    private void checkEnemyCollisions() {
        List<Bullet> bulletsToDelete = new ArrayList<>();
        for (Bullet bullet : repository.getBullets()) {
            TankPlayer enemy = checkEnemyCollision(bullet.getCoordinates(), true);

            if (enemy != null && bullet.getParent() != enemy) {
                if (enemy.processDamage()) {
                    repository.removeEnemy(enemy);
                }
                bulletsToDelete.add(bullet);
                continue;
            }

            enemy = checkEnemyCollision(bullet.getDestinationCoordinates(), false);

            if (enemy != null && bullet.getParent() != enemy && bullet.getMovementProgress() > 0.5f) {
                if (enemy.processDamage()) {
                    repository.removeEnemy(enemy);
                }
                bulletsToDelete.add(bullet);
            }
        }

        for (Bullet bullet : bulletsToDelete) {
            repository.removeBullet(bullet);
        }
    }

    private void checkPlayerCollisions() {
        List<Bullet> bulletsToDelete = new ArrayList<>();

        for (Bullet bullet : repository.getBullets()) {
            TankPlayer player = repository.getPlayer();

            if (bullet.getParent() == player) {
                continue;
            }

            if (player.getMovementProgress() > 0.5f) {
                continue;
            }

            if (bullet.getCoordinates().equals(player.getCoordinates())) {
                if (player.processDamage()) {
                    repository.removePlayer();
                    bulletsToDelete.add(bullet);
                }
            }

            if (
                    bullet.getMovementProgress() > 0.5f &&
                    bullet.getDestinationCoordinates().equals(player.getCoordinates())
            ) {
                if (player.processDamage()) {
                    repository.removePlayer();
                    bulletsToDelete.add(bullet);
                }
            }
        }

        for (Bullet bullet : bulletsToDelete) {
            repository.removeBullet(bullet);
        }
    }

    private void checkCollisions() {
        checkObstacleCollisions();
        checkEnemyCollisions();
        checkPlayerCollisions();
    }

    public void processOneTick(float deltaTime, float speed) {
        processPlayerMove(deltaTime, speed);
        processEnemiesMove(deltaTime, speed);
        processBulletsMove(deltaTime, speed);
        checkCollisions();
    }
}
