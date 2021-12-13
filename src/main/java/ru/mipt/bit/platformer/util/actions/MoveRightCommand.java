package ru.mipt.bit.platformer.util.actions;

import ru.mipt.bit.platformer.util.AbstractMovable;

public class MoveRightCommand implements ActionCommand {
    private final AbstractMovable movable;

    public MoveRightCommand(AbstractMovable movable) {
        this.movable = movable;
    }

    @Override
    public boolean exec() {
        return movable.moveRight();
    }
}
