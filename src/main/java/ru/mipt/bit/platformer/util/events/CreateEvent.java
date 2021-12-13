package ru.mipt.bit.platformer.util.events;

import ru.mipt.bit.platformer.util.AbstractObjectWithCoordinates;

/**
 * Adapter в системе портов и адаптеров
 * InfrastructureLayer
 */
public final class CreateEvent implements Event {
    private final AbstractObjectWithCoordinates object;

    public CreateEvent(AbstractObjectWithCoordinates object) {
        this.object = object;
    }

    public AbstractObjectWithCoordinates getObject() {
        return object;
    }
}
