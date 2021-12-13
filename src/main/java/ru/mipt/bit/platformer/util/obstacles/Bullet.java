package ru.mipt.bit.platformer.util.obstacles;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.AbstractMovable;
import ru.mipt.bit.platformer.util.control.ActionGenerator;
import ru.mipt.bit.platformer.util.players.Tank;
import ru.mipt.bit.platformer.util.players.moveStrategies.MoveStrategy;

/**
 * Adapter в системе портов и адаптеров
 * DomainLayer
 */
public class Bullet extends AbstractMovable {
    private final Tank parent;

    public static final int DAMAGE = 25;

    public Bullet(
            GridPoint2 position,
            float rotation,
            MoveStrategy moveStrategy,
            Tank parent,
            ActionGenerator actionGenerator
    ) {
        super(position, rotation, moveStrategy, actionGenerator);
        this.parent = parent;
    }

    public Tank getParent() {
        return parent;
    }
}
