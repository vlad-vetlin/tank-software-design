package ru.mipt.bit.platformer.util.actions;

import ru.mipt.bit.platformer.util.AbstractMovable;

public class MoveLeftCommand implements ActionCommand {
    private final AbstractMovable movable;

    public MoveLeftCommand(AbstractMovable movable) {
        this.movable = movable;
    }

    @Override
    public boolean exec() {
        return movable.moveLeft();
    }
}
