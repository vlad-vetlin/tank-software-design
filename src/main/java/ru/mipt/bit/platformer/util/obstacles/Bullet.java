package ru.mipt.bit.platformer.util.obstacles;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.AbstractMovable;
import ru.mipt.bit.platformer.util.players.TankPlayer;
import ru.mipt.bit.platformer.util.players.moveStrategies.MoveStrategy;

public class Bullet extends AbstractMovable {
    private final TankPlayer parent;

    public static final int DAMAGE = 25;

    public Bullet(GridPoint2 position, float rotation, MoveStrategy moveStrategy, TankPlayer parent) {
        super(position, rotation, moveStrategy);
        this.parent = parent;
    }

    public TankPlayer getParent() {
        return parent;
    }
}
