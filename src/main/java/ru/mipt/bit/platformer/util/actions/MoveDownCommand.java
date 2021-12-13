package ru.mipt.bit.platformer.util.actions;

import ru.mipt.bit.platformer.util.AbstractMovable;

public class MoveDownCommand implements ActionCommand {
    private final AbstractMovable movable;

    public MoveDownCommand(AbstractMovable movable) {
        this.movable = movable;
    }

    @Override
    public boolean exec() {
        return movable.moveDown();
    }
}
