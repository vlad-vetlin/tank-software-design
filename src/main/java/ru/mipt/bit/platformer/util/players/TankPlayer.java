package ru.mipt.bit.platformer.util.players;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.BulletFactory;
import ru.mipt.bit.platformer.CanFire;
import ru.mipt.bit.platformer.util.AbstractMovable;
import ru.mipt.bit.platformer.util.obstacles.Bullet;
import ru.mipt.bit.platformer.util.players.moveStrategies.MoveStrategy;

import java.util.Map;

import static ru.mipt.bit.platformer.util.GdxGameUtils.incrementByRotation;

public final class TankPlayer extends AbstractMovable implements CanFire {
    private final BulletFactory fireCallback;

    private boolean isReloaded = true;

    private float reloadProcessedTime = 0f;

    private int lives;

    private State state = State.New;

    private Map<State, Float> stateToDeltaTimeMultiplier;

    public TankPlayer(GridPoint2 coordinates, MoveStrategy moveStrategy) {
        this(coordinates, moveStrategy, null);
    }

    public TankPlayer(GridPoint2 coordinates, MoveStrategy moveStrategy, BulletFactory fireCallback) {
        super(coordinates, 0f, moveStrategy);
        this.fireCallback = fireCallback;
        this.lives = 100;

        stateToDeltaTimeMultiplier = Map.of(
                State.New, 1f,
                State.Light, 1f,
                State.Medium, 1 / 2f,
                State.High, 1 / 3f
        );
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

    private float getPreparedDeltaTime(float deltaTime) {
        return deltaTime * stateToDeltaTimeMultiplier.get(state);
    }

    @Override
    public void processOneTick(float deltaTime, float speed) {
        super.processOneTick(getPreparedDeltaTime(deltaTime), speed);

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

        if (lives <= 25) {
            state = State.High;
        } else if (lives <= 50) {
            state = State.Medium;
        } else if (lives <= 75) {
            state = State.Light;
        }

        return lives < 0;
    }

    public int getLives() {
        return lives;
    }
}
