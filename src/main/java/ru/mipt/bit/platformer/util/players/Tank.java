package ru.mipt.bit.platformer.util.players;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.CanFire;
import ru.mipt.bit.platformer.util.HasLives;
import ru.mipt.bit.platformer.ObjectFactory;
import ru.mipt.bit.platformer.util.AbstractMovable;
import ru.mipt.bit.platformer.util.control.ActionGenerator;
import ru.mipt.bit.platformer.util.events.CreateEvent;
import ru.mipt.bit.platformer.util.events.ObjectEventManager;
import ru.mipt.bit.platformer.util.obstacles.Bullet;
import ru.mipt.bit.platformer.util.players.moveStrategies.MoveStrategy;

import java.util.Map;

import static ru.mipt.bit.platformer.util.GdxGameUtils.incrementByRotation;

/**
 * Adapter в системе портов и адаптеров
 * DomainLayer
 */
public final class Tank extends AbstractMovable implements CanFire, HasLives {
    private boolean isReloaded = true;

    private float reloadProcessedTime = 0f;

    private int lives;

    private State state = State.New;

    private final Map<State, Float> stateToDeltaTimeMultiplier;

    public Tank(GridPoint2 coordinates, MoveStrategy moveStrategy) {
        this(coordinates, moveStrategy, null);
    }

    public Tank(
            GridPoint2 coordinates,
            MoveStrategy moveStrategy,
            ActionGenerator actionGenerator
    ) {
        super(coordinates, 0f, moveStrategy, actionGenerator);
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

        ObjectEventManager.notifyEvent(
                Bullet.class,
                new CreateEvent(
                        ObjectFactory.createBullet(
                                incrementByRotation(getCoordinates(),getRotation()),
                                getRotation(),
                                this
                        )
                )
        );
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

    @Override
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

    @Override
    public int getLives() {
        return lives;
    }
}
