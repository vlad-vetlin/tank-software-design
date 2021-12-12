package ru.mipt.bit.platformer.util.levels;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.ObjectFactory;
import ru.mipt.bit.platformer.util.AbstractMovable;
import ru.mipt.bit.platformer.util.AbstractObjectWithCoordinates;
import ru.mipt.bit.platformer.util.RenderableObjectWithCoordinates;
import ru.mipt.bit.platformer.util.obstacles.Bullet;
import ru.mipt.bit.platformer.util.obstacles.Tree;
import ru.mipt.bit.platformer.util.players.Tank;

import java.util.*;

/**
 * Адаптер в системе портов и адаптеров
 * InfrastructureLayer
 */
public class ObjectsRepository {
    private final Map<GridPoint2, AbstractObjectWithCoordinates> obstacles = new HashMap<>();

    private static final Map<UUID, Bullet> bullets = new HashMap<>();

    private final Map<UUID, Tank> enemies = new HashMap<>();

    private Tank player;

    public void setPlayer(Tank player) {
        this.player = player;
    }

    public void removePlayer() {
        System.out.println("Game over!");
    }

    public Tank getPlayer() {
        return player;
    }

    public void addObstacles(Collection<GridPoint2> positions) {
        for (GridPoint2 position : positions) {
            obstacles.put(position, new Tree(position));
        }
    }

    public void addEnemies(Collection<GridPoint2> positions) {
        for (GridPoint2 position : positions) {
            Tank enemy = ObjectFactory.createEnemy(position);
            enemies.put(enemy.getId(), enemy);
        }
    }

    public ArrayList<RenderableObjectWithCoordinates> getRenderableObjects() {
        ArrayList<RenderableObjectWithCoordinates> result = new ArrayList<>();

        result.add(player);
        result.addAll(obstacles.values());
        result.addAll(enemies.values());

        return result;
    }

    public AbstractObjectWithCoordinates getObject(GridPoint2 point) {
        if (obstacles.containsKey(point)) {
            return obstacles.get(point);
        }

        for (Tank enemy : enemies.values()) {
            if (point.equals(enemy.getCoordinates())) {
                return enemy;
            }
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

        for (Bullet bullet : getBullets()) {
            if (bullet.getDestinationCoordinates().equals(point)) {
                return bullet;
            }
        }

        if (player.getDestinationCoordinates().equals(point)) {
            return player;
        }

        return null;
    }

    public Tank getEnemyByCoords(GridPoint2 point) {
        return enemies.values()
                .stream()
                .filter(enemy -> enemy.getCoordinates().equals(point))
                .findFirst()
                .orElse(null);
    }

    public Tank getPlayerByCoords(GridPoint2 point) {
        Tank enemy = getEnemyByCoords(point);

        if (enemy == null) {
            return player.getCoordinates().equals(point) ? player : null;
        }

        return enemy;
    }

    public Collection<Tank> getEnemies() {
        return enemies.values();
    }

    public Collection<Bullet> getBullets() {
        return bullets.values();
    }

    public void addBullet(Bullet bullet) {
        bullets.put(bullet.getId(), bullet);
    }

    public Collection<AbstractObjectWithCoordinates> getObstacles() {
        return obstacles.values();
    }

    public void removeBullet(Bullet bullet) {
        bullets.remove(bullet.getId());
    }

    public boolean hasEnemy(Tank tank) {
        return enemies.containsKey(tank.getId());
    }

    public void removeEnemy(Tank enemy) {
        enemies.remove(enemy.getId());
    }
}
