package ru.mipt.bit.platformer.util.control;

import ru.mipt.bit.platformer.util.ControlCommand;
import ru.mipt.bit.platformer.util.levels.Level;
import ru.mipt.bit.platformer.util.players.Action;

import java.util.Random;

public class RandomControlCommand implements ControlCommand {
    private final Level level;

    private static final Random random = new Random();

    public RandomControlCommand(Level level) {
        this.level = level;
    }

    private Action generateAction() {
        int action = random.nextInt(5);

        switch (action) {
            case 0:
                return Action.MoveNorth;
            case 1:
                return Action.MoveEast;
            case 2:
                return Action.MoveSouth;
            case 3:
                return Action.MoveWest;
            case 4:
            default:
                return Action.Shoot;
        }
    }

    @Override
    public void execute() {
        level.getRepository()
             .getEnemies()
             .forEach(enemy -> enemy.processAction(generateAction()));
    }
}
