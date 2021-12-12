package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.obstacles.Bullet;
import ru.mipt.bit.platformer.util.players.Tank;

/**
 * Adapter в системе портов и адаптеров
 * InfrastructureLayer
 */
public class ObjectFactory {
    public static Tank createPlayer(GridPoint2 position) {
        return new Tank(position, Settings.TANK_MOVE_STRATEGY, Settings.PLAYER_ACTION_GENERATOR);
    }

    public static Tank createEnemy(GridPoint2 position) {
        return new Tank(position, Settings.TANK_MOVE_STRATEGY, Settings.ENEMY_ACTION_GENERATOR);
    }

    public static Bullet createBullet(GridPoint2 position, float rotation, Tank parent) {
        return new Bullet(position, rotation, Settings.BULLET_MOVE_STRATEGY, parent, Settings.BULLET_ACTION_GENERATOR);
    }
}
