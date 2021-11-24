package ru.mipt.bit.platformer.util.control;

import ru.mipt.bit.platformer.util.levels.Level;
import ru.mipt.bit.platformer.util.players.Action;
import ru.mipt.bit.platformer.util.players.TankPlayer;

import java.util.Random;

public class RandomBotControlCommand implements ControlCommand {
    private static final Random random = new Random();

    private final TankPlayer player;

    public RandomBotControlCommand(TankPlayer player) {
        this.player = player;
    }

    @Override
    public void execute() {
        int direction = random.nextInt(4);

        switch (direction) {
            case 0:
                player.move(Action.MoveNorth);
                return;
            case 1:
                player.move(Action.MoveEast);
                return;
            case 2:
                player.move(Action.MoveSouth);
                return;
            case 3:
                player.move(Action.MoveWest);
        }
    }
}
