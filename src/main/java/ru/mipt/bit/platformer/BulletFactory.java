package ru.mipt.bit.platformer;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.players.TankPlayer;

public interface BulletFactory {
    void createBullet(TankPlayer parent, GridPoint2 position, float rotation);
}
