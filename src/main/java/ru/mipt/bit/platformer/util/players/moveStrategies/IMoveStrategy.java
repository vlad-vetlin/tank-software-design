package ru.mipt.bit.platformer.util.players.moveStrategies;

import ru.mipt.bit.platformer.util.ICanMove;
import ru.mipt.bit.platformer.util.players.AbstractPlayer;

public interface IMoveStrategy {
    void moveUp(ICanMove player);

    void moveLeft(ICanMove player);

    void moveRight(ICanMove player);

    void moveDown(ICanMove player);

    void processMoveToDestination(ICanMove player, float deltaTime, float speed);
}
