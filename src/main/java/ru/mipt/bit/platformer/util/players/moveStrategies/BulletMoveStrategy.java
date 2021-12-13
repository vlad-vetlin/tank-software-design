package ru.mipt.bit.platformer.util.players.moveStrategies;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.AbstractMovable;
import ru.mipt.bit.platformer.util.AbstractObjectWithCoordinates;
import ru.mipt.bit.platformer.util.events.CollisionEvent;
import ru.mipt.bit.platformer.util.events.ObjectEventManager;
import ru.mipt.bit.platformer.util.levels.DoublePoint;
import ru.mipt.bit.platformer.util.levels.Level;
import ru.mipt.bit.platformer.util.obstacles.Bullet;

/**
 * Adapter в системе портов и адаптеров
 * ApplicationLayer
 */
public class BulletMoveStrategy implements MoveStrategy {
    private Level level;

    public BulletMoveStrategy(Level level) {
        this.level = level;
    }

    @Override
    public boolean canMove(AbstractMovable movable, GridPoint2 destination) {
        if (destination.x < 0 || destination.x >= level.getWidth() ||
                destination.y < 0 || destination.y >= level.getHeight()) {
            ObjectEventManager.notifyEvent(movable.getClass(), new CollisionEvent(movable, null));
            return false;
        }

        if (checkPoint(movable, destination)) {
            return false;
        }

        return !checkPoint(movable, movable.getCoordinates());
    }

    @Override
    public void setLevel(Level level) {
        this.level = level;
    }

    private boolean checkPoint(AbstractMovable movable, GridPoint2 destination) {
        AbstractObjectWithCoordinates object = level.getObject(destination);
        if (object != null && checkIsCollision(movable, object)) {
            ObjectEventManager.notifyEvent(movable.getClass(), new CollisionEvent(movable, object));
            return true;
        }

        object = level.getObjectByDestination(destination);
        if (object != null && checkIsCollision(movable, object)) {
            ObjectEventManager.notifyEvent(movable.getClass(), new CollisionEvent(movable, object));
            return true;
        }

        return false;
    }

    private boolean checkIsCollision(AbstractObjectWithCoordinates lhs, AbstractObjectWithCoordinates rhs) {
        if (lhs.getId().equals(rhs.getId())) {
            return false;
        }

        DoublePoint lhsPoint = getRealCoordinates(lhs);
        DoublePoint rhsPoint = getRealCoordinates(rhs);

        if (lhs instanceof Bullet && ((Bullet)lhs).getParent().getId().equals(rhs.getId())) {
            return false;
        }

        return lhsPoint.getSqrDestination(rhsPoint) <= (1. / 2.);
    }

    private DoublePoint getRealCoordinates(AbstractObjectWithCoordinates object) {
        if (object instanceof AbstractMovable) {
            AbstractMovable movable = (AbstractMovable) object;

            float x = (movable.getDestinationCoordinates().x - movable.getCoordinates().x)
                    * movable.getMovementProgress()
                    + movable.getCoordinates().x;

            float y = (movable.getDestinationCoordinates().y - movable.getCoordinates().y)
                    * movable.getMovementProgress()
                    + movable.getCoordinates().y;

            return new DoublePoint(x, y);
        }

        return new DoublePoint(object.getCoordinates().x, object.getCoordinates().y);
    }
}
