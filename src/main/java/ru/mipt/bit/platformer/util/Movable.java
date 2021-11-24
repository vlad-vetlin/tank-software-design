package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.players.Action;
import ru.mipt.bit.platformer.util.players.moveStrategies.MoveStrategy;

public interface Movable {
    void move(Action action);

    void processMoveToDestination(float deltaTime, float speed);

    GridPoint2 getCoordinates();
}
