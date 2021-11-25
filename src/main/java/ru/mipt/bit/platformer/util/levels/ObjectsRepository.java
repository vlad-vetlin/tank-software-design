package ru.mipt.bit.platformer.util.levels;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.BulletFactory;
import ru.mipt.bit.platformer.util.ObjectEventManager;
import ru.mipt.bit.platformer.util.AbstractObjectWithCoordinates;
import ru.mipt.bit.platformer.util.obstacles.Bullet;
import ru.mipt.bit.platformer.util.obstacles.Tree;
import ru.mipt.bit.platformer.util.players.TankPlayer;
import ru.mipt.bit.platformer.util.players.moveStrategies.MoveStrategy;

import java.util.*;

public class ObjectsRepository {
    private final Map<GridPoint2, AbstractObjectWithCoordinates> obstacles = new HashMap<>();

    private static final Map<UUID, Bullet> bullets = new HashMap<>();

    private final Map<UUID, TankPlayer> enemies = new HashMap<>();

    private TankPlayer player;

    private ObjectEventManager observer;

    public void setPlayer(TankPlayer player) {
        this.player = player;
    }

    public void removePlayer() {
        notify("RemovePlayer", player);
    }

    public TankPlayer getPlayer() {
        return player;
    }

    public void setObserver(ObjectEventManager eventManager) {
        observer = eventManager;
    }

    public void addObstacles(Collection<GridPoint2> positions) {
        for (GridPoint2 position : positions) {
            obstacles.put(position, new Tree(position));
        }
    }

    public void addEnemies(Collection<GridPoint2> positions, MoveStrategy moveStrategy, BulletFactory callback) {
        for (GridPoint2 position : positions) {
            TankPlayer enemy = new TankPlayer(position, moveStrategy, callback);
            enemies.put(enemy.getId(), enemy);
        }
    }

    public ArrayList<AbstractObjectWithCoordinates> getRenderableObjects() {
        ArrayList<AbstractObjectWithCoordinates> result = new ArrayList<>();

        result.add(player);
        result.addAll(obstacles.values());
        result.addAll(enemies.values());

        return result;
    }

    public boolean hasObject(GridPoint2 point) {
        if (obstacles.containsKey(point)) {
            return true;
        }

        for (TankPlayer enemy : enemies.values()) {
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

    public TankPlayer getEnemyByCoords(GridPoint2 point) {
        return enemies.values()
                .stream()
                .filter(enemy -> enemy.getCoordinates().equals(point))
                .findFirst()
                .orElse(null);
    }

    public TankPlayer getPlayerByCoords(GridPoint2 point) {
        TankPlayer enemy = getEnemyByCoords(point);

        if (enemy == null) {
            return player.getCoordinates().equals(point) ? player : null;
        }

        return enemy;
    }

    public Collection<TankPlayer> getEnemies() {
        return enemies.values();
    }

    public Collection<Bullet> getBullets() {
        return bullets.values();
    }

    public void addBullet(Bullet bullet) {
        bullets.put(bullet.getId(), bullet);
        notify("AddBullet", bullet);
    }

    public Collection<AbstractObjectWithCoordinates> getObstacles() {
        return obstacles.values();
    }

    public AbstractObjectWithCoordinates getObstacleByCoords(GridPoint2 point) {
        return obstacles.getOrDefault(point, null);
    }

    public void removeBullet(Bullet bullet) {
        if (bullets.containsKey(bullet.getId())) {
            bullets.remove(bullet.getId());
            notify("RemoveBullet", bullet);
        }
    }

    public void removeEnemy(TankPlayer enemy) {
        if (enemies.containsKey(enemy.getId())) {
            enemies.remove(enemy.getId());
            notify("RemoveEnemy", enemy);
        }
    }

    private void notify(String event, AbstractObjectWithCoordinates object) {
        if (observer != null) {
            observer.notify(event, object);
        }
    }
}
