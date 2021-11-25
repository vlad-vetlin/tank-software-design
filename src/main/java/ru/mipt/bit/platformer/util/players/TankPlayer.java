package ru.mipt.bit.platformer.util.players;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.BulletFactory;
import ru.mipt.bit.platformer.CanFire;
import ru.mipt.bit.platformer.util.AbstractMovable;
import ru.mipt.bit.platformer.util.obstacles.Bullet;
import ru.mipt.bit.platformer.util.players.moveStrategies.MoveStrategy;

import static ru.mipt.bit.platformer.util.GdxGameUtils.incrementByRotation;

public final class TankPlayer extends AbstractMovable implements CanFire {
    private final BulletFactory fireCallback;

    private boolean isReloaded = true;

    private float reloadProcessedTime = 0f;

    private int lives;

    public TankPlayer(GridPoint2 coordinates, MoveStrategy moveStrategy) {
        this(coordinates, moveStrategy, null);
    }

    public TankPlayer(GridPoint2 coordinates, MoveStrategy moveStrategy, BulletFactory fireCallback) {
        super(coordinates, 0f, moveStrategy);
        this.fireCallback = fireCallback;
        this.lives = 100;
    }

    @Override
    public void fire() {
        if (!isReloaded) {
            return;
        }
        isReloaded = false;

        if (fireCallback != null) {
            fireCallback.createBullet(this, incrementByRotation(getCoordinates(), getRotation()), getRotation());
        }
    }

    @Override
    public void processOneTick(float deltaTime, float speed) {
        super.processOneTick(deltaTime, speed);

        if (!isReloaded) {
            reloadProcessedTime += deltaTime * speed;
            float RELOAD_TIME = 0.5f;
            if (reloadProcessedTime > RELOAD_TIME) {
                reloadProcessedTime = 0f;
                isReloaded = true;
            }
        }
    }

    public void processAction(Action action) {
        if (action == Action.Shoot) {
            fire();
            return;
        }

        move(action);
    }

    public boolean processDamage() {
        lives -= Bullet.DAMAGE;
        return lives < 0;
    }
}
