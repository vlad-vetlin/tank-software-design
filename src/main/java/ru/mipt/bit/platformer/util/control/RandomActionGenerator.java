package ru.mipt.bit.platformer.util.control;

import ru.mipt.bit.platformer.util.AbstractMovable;
import ru.mipt.bit.platformer.util.actions.*;
import ru.mipt.bit.platformer.util.levels.Level;
import ru.mipt.bit.platformer.util.players.Tank;

import java.util.Random;

/**
 * Port в системе портов и адаптеров
 * ApplicationLayer
 */
public final class RandomActionGenerator implements ActionGenerator {
    private static final Random random = new Random();

    @Override
    public ActionCommand getRecommendation(AbstractMovable movable) {
        int action = random.nextInt(5);

        switch (action) {
            case 0:
                return new MoveUpCommand(movable);
            case 1:
                return new MoveRightCommand(movable);
            case 2:
                return new MoveDownCommand(movable);
            case 3:
                return new MoveLeftCommand(movable);
        }

        if (movable instanceof Tank) {
            return new FireCommand((Tank) movable);
        }

        return new NothingCommand(movable);
    }

    @Override
    public void setNewGameState(Level level) {}
}
