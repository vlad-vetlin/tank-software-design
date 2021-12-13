package ru.mipt.bit.platformer.util.actions;

import ru.mipt.bit.platformer.util.AbstractMovable;

public class MoveUpCommand implements ActionCommand {
    private final AbstractMovable movable;

    public MoveUpCommand(AbstractMovable movable) {
        this.movable = movable;
    }

    @Override
    public boolean exec() {
        return movable.moveUp();
    }
}
