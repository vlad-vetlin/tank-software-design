package ru.mipt.bit.platformer.util.actions;

import ru.mipt.bit.platformer.util.players.Tank;

public class FireCommand implements ActionCommand {
    private final Tank tank;

    public FireCommand(Tank tank) {
        this.tank = tank;
    }

    @Override
    public boolean exec() {
        tank.fire();

        return true;
    }
}
