package ru.mipt.bit.platformer.util;

import ru.mipt.bit.platformer.util.events.CollisionEvent;
import ru.mipt.bit.platformer.util.events.CreateEvent;
import ru.mipt.bit.platformer.util.events.ObjectEventManager;
import ru.mipt.bit.platformer.util.events.RemoveEvent;
import ru.mipt.bit.platformer.util.levels.Level;
import ru.mipt.bit.platformer.util.obstacles.Bullet;
import ru.mipt.bit.platformer.util.players.Tank;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SimpleEngine implements Engine {
    private final Level level;

    private final List<Bullet> bulletsToDelete = new ArrayList<>();

    private final List<Tank> tanksToDelete = new ArrayList<>();

    public SimpleEngine(Level level) {
        this.level = level;
    }

    @Override
    public void processOneTick(float deltaTime, float speed) {
        processPlayerMove(deltaTime, speed);
        processEnemiesMove(deltaTime, speed);
        processBulletsMove(deltaTime, speed);

        removeDeletedObjects();
    }

    @Override
    public void subscribeToAllEvents() {
        UUID uuid = UUID.randomUUID();

        ObjectEventManager.subscribe(
                uuid,
                ObjectEventManager.getEventName(Bullet.class, CollisionEvent.class),
                event -> processBulletCollision((CollisionEvent) event)
        );
    }

    private void processPlayerMove(float deltaTime, float speed) {
        Tank player = level.getPlayer();
        if (player != null) {
            player.processOneTick(deltaTime, speed);
        }
    }

    private void processEnemiesMove(float deltaTime, float speed) {
        for (Tank enemy : level.getEnemies()) {
            enemy.processOneTick(deltaTime, speed);
        }
    }

    private void processBulletsMove(float deltaTime, float speed) {
        for (Bullet bullet : level.getBullets().values()) {
            bullet.processOneTick(deltaTime, speed);
        }
    }

    private void removeDeletedObjects() {
        for (Bullet bullet : bulletsToDelete) {
            ObjectEventManager.notifyEvent(Bullet.class, new RemoveEvent(bullet));
        }

        for (Tank tank : tanksToDelete) {
            ObjectEventManager.notifyEvent(Tank.class, new RemoveEvent(tank));
        }
    }

    private void processBulletCollision(CollisionEvent collisionEvent) {
        bulletsToDelete.add((Bullet) collisionEvent.getObject());

        if (collisionEvent.getDestinationObject() == null) {
            return;
        }

        if (canBeDeletedByBullet(collisionEvent.getDestinationObject())) {
            if (collisionEvent.getDestinationObject() instanceof HasLives) {
                HasLives hasLives = (HasLives) collisionEvent.getDestinationObject();

                if (hasLives.processDamage()) {
                    tanksToDelete.add((Tank) collisionEvent.getDestinationObject());
                }
            }
        }
    }

    private boolean canBeDeletedByBullet(AbstractObjectWithCoordinates object) {
        return object instanceof Tank || object instanceof Bullet;
    }
}
