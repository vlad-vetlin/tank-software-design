package ru.mipt.bit.platformer.util.levels;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.*;
import ru.mipt.bit.platformer.ObjectFactory;
import ru.mipt.bit.platformer.util.events.CreateEvent;
import ru.mipt.bit.platformer.util.events.ObjectEventManager;
import ru.mipt.bit.platformer.util.events.RemoveEvent;
import ru.mipt.bit.platformer.util.obstacles.Bullet;
import ru.mipt.bit.platformer.util.obstacles.Tree;
import ru.mipt.bit.platformer.util.players.Tank;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Адаптер в системе портов и адаптеров
 * Domain layer
 */
public class Level {
    private final GridPoint2 bounds;

    private final Map<UUID, Tank> enemies = new HashMap<>();

    private Tank player;

    private final Map<UUID, Bullet> bullets = new HashMap<>();

    private final Map<GridPoint2, AbstractObjectWithCoordinates> obstacles = new HashMap<>();

    public Level(GridPoint2 bounds, GridPoint2 playerStartPosition) {
        this(bounds, playerStartPosition, List.of(), List.of());
    }

    public Level(
            GridPoint2 bounds,
            GridPoint2 playerStartPosition,
            Collection<GridPoint2> obstacles,
            Collection<GridPoint2> enemies
    ) {
        this.bounds = bounds;

        player = ObjectFactory.createPlayer(playerStartPosition);

        this.obstacles.putAll(obstacles.stream()
                .map(Tree::new)
                .collect(Collectors.toMap(Tree::getCoordinates, tree -> tree)));

        this.enemies.putAll(enemies.stream()
                .map(ObjectFactory::createEnemy)
                .collect(Collectors.toMap(Tank::getId, value -> value)));
    }

    public int getWidth() {
        return bounds.x;
    }

    public int getHeight() {
        return bounds.y;
    }

    public Tank getPlayer() {
        return player;
    }

    public Collection<Tank> getEnemies() {
        return enemies.values();
    }

    public Collection<AbstractObjectWithCoordinates> getObstacles() {
        return obstacles.values();
    }

    public Map<UUID, Bullet> getBullets() {
        return this.bullets;
    }

    public Tank getPlayerByCoords(GridPoint2 point) {
        Tank enemy = getEnemyByCoords(point);

        if (enemy == null) {
            return player.getCoordinates().equals(point) ? player : null;
        }

        return enemy;
    }

    public AbstractObjectWithCoordinates getObject(GridPoint2 point) {
        if (obstacles.containsKey(point)) {
            return obstacles.get(point);
        }

        Tank enemy = getEnemyByCoords(point);
        if (enemy != null) {
            return enemy;
        }

        if (player != null && point.equals(player.getCoordinates())) {
            return player;
        }

        return null;
    }

    public AbstractMovable getObjectByDestination(GridPoint2 point) {
        for (Tank tank : getEnemies()) {
            if (tank.getDestinationCoordinates().equals(point)) {
                return tank;
            }
        }

        for (Bullet bullet : bullets.values()) {
            if (bullet.getDestinationCoordinates().equals(point)) {
                return bullet;
            }
        }

        if (player.getDestinationCoordinates().equals(point)) {
            return player;
        }

        return null;
    }

    public ArrayList<RenderableObjectWithCoordinates> getRenderableObjects() {
        ArrayList<RenderableObjectWithCoordinates> result = new ArrayList<>();

        result.add(player);
        result.addAll(obstacles.values());
        result.addAll(enemies.values());

        return result;
    }

    public boolean hasEnemy(Tank tank) {
        return enemies.containsKey(tank.getId());
    }

    public void addBullet(Bullet bullet) {
        bullets.put(bullet.getId(), bullet);
    }

    public void processActions() {
        for (Tank enemy : enemies.values()) {
            enemy.getActionGenerator().getRecommendation(enemy).exec();
        }

        player.getActionGenerator().getRecommendation(player).exec();
    }

    public void subscribeToAllEvents() {
        UUID uuid = UUID.randomUUID();

        ObjectEventManager.subscribe(uuid, ObjectEventManager.getEventName(Bullet.class, RemoveEvent.class), event -> {
            RemoveEvent removeEvent = (RemoveEvent) event;

            bullets.remove(removeEvent.getObject().getId());
        });

        ObjectEventManager.subscribe(uuid, ObjectEventManager.getEventName(Tank.class, RemoveEvent.class), event -> {
            RemoveEvent removeEvent = (RemoveEvent) event;

            if (hasEnemy((Tank) removeEvent.getObject())) {
                enemies.remove(removeEvent.getObject().getId());
            } else {
                System.out.println("Game over!!!");
            }
        });

        ObjectEventManager.subscribe(uuid, ObjectEventManager.getEventName(Bullet.class, CreateEvent.class), event -> {
            CreateEvent createEvent = (CreateEvent) event;

            addBullet((Bullet) createEvent.getObject());
        });
    }

    private Tank getEnemyByCoords(GridPoint2 point) {
        for (Tank enemy : enemies.values()) {
            if (point.equals(enemy.getCoordinates())) {
                return enemy;
            }
        }

        return null;
    }
}
