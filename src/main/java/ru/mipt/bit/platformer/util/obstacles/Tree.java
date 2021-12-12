package ru.mipt.bit.platformer.util.obstacles;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.AbstractObjectWithCoordinates;

/**
 * Adapter в системе портов и адаптеров
 * DomainLayer
 */
public final class Tree extends AbstractObjectWithCoordinates {
    public Tree(GridPoint2 coordinates) {
        super(coordinates, 0f);
    }
}
