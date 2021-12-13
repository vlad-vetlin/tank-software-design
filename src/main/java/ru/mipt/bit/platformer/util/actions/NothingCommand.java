package ru.mipt.bit.platformer.util.actions;

import ru.mipt.bit.platformer.util.AbstractObjectWithCoordinates;

public class NothingCommand implements ActionCommand {
    private final AbstractObjectWithCoordinates object;

    public NothingCommand(AbstractObjectWithCoordinates object) {
        this.object = object;
    }

    @Override
    public boolean exec() {
        return true;
    }
}
