package ru.mipt.bit.platformer.util.levels;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.HasLives;
import ru.mipt.bit.platformer.ObjectFactory;
import ru.mipt.bit.platformer.util.AbstractObjectWithCoordinates;
import ru.mipt.bit.platformer.util.events.CollisionEvent;
import ru.mipt.bit.platformer.util.events.CreateEvent;
import ru.mipt.bit.platformer.util.events.ObjectEventManager;
import ru.mipt.bit.platformer.util.control.ActionProcessor;
import ru.mipt.bit.platformer.util.events.RemoveEvent;
import ru.mipt.bit.platformer.util.obstacles.Bullet;
import ru.mipt.bit.platformer.util.players.Tank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Адаптер в системе портов и адаптеров
 * Domain layer
 */
public class Level {
    private final ObjectsRepository repository;

    private final GridPoint2 bounds;

    private final List<Bullet> bulletsToDelete = new ArrayList<>();

    private final List<Tank> tanksToDelete = new ArrayList<>();

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

        this.bounds = bounds;

        repository.setPlayer(ObjectFactory.createPlayer(playerStartPosition));
        repository.addObstacles(obstacles);
        repository.addEnemies(enemies);

        UUID uuid = UUID.randomUUID();

        ObjectEventManager.subscribe(
            uuid,
            ObjectEventManager.getEventName(Bullet.class, CollisionEvent.class),
            event -> processBulletCollision((CollisionEvent) event)
        );

        ObjectEventManager.subscribe(uuid, ObjectEventManager.getEventName(Bullet.class, RemoveEvent.class), event -> {
            RemoveEvent removeEvent = (RemoveEvent) event;

            bulletsToDelete.add((Bullet) removeEvent.getObject());
        });

        ObjectEventManager.subscribe(uuid, ObjectEventManager.getEventName(Tank.class, RemoveEvent.class), event -> {
            RemoveEvent removeEvent = (RemoveEvent) event;

            tanksToDelete.add((Tank) removeEvent.getObject());
        });

        ObjectEventManager.subscribe(uuid, ObjectEventManager.getEventName(Bullet.class, CreateEvent.class), event -> {
            CreateEvent createEvent = (CreateEvent) event;

            repository.addBullet((Bullet) createEvent.getObject());
        });
    }

    public Tank getPlayer() {
        return repository.getPlayer();
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
        Tank player = repository.getPlayer();
        if (player != null) {
            player.processOneTick(deltaTime, speed);
        }
    }

    private void processEnemiesMove(float deltaTime, float speed) {
        for (Tank enemy : repository.getEnemies()) {
            enemy.processOneTick(deltaTime, speed);
        }
    }

    private void processBulletsMove(float deltaTime, float speed) {
        for (Bullet bullet : repository.getBullets()) {
            bullet.processOneTick(deltaTime, speed);
        }
    }

    private void removeDeletedObjects() {
        for (Bullet bullet : bulletsToDelete) {
            repository.removeBullet(bullet);
        }

        for (Tank tank : tanksToDelete) {
            if (repository.hasEnemy(tank)) {
                repository.removeEnemy(tank);
            } else {
                repository.removePlayer();
            }
        }
    }

    public void processOneTick(float deltaTime, float speed) {
        processPlayerMove(deltaTime, speed);
        processEnemiesMove(deltaTime, speed);
        processBulletsMove(deltaTime, speed);

        removeDeletedObjects();
    }

    public void processActions() {
        for (Tank enemy : repository.getEnemies()) {
            ActionProcessor.processAction(enemy);
        }

        ActionProcessor.processAction(repository.getPlayer());
    }

    private boolean canBeDeletedByBullet(AbstractObjectWithCoordinates object) {
        return object instanceof Tank || object instanceof Bullet;
    }

    private void processBulletCollision(CollisionEvent collisionEvent) {
        ObjectEventManager.notifyEvent(Bullet.class, new RemoveEvent(collisionEvent.getObject()));

        if (collisionEvent.getDestinationObject() == null) {
            return;
        }

        if (canBeDeletedByBullet(collisionEvent.getDestinationObject())) {
            if (collisionEvent.getDestinationObject() instanceof HasLives) {
                HasLives hasLives = (HasLives) collisionEvent.getDestinationObject();

                if (hasLives.processDamage()) {
                    ObjectEventManager.notifyEvent(
                            collisionEvent.getDestinationObject().getClass(),
                            new RemoveEvent(collisionEvent.getDestinationObject())
                    );
                }
            }
        }
    }
}
