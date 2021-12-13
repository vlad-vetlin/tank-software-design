package ru.mipt.bit.platformer.util.events;

import ru.mipt.bit.platformer.util.AbstractObjectWithCoordinates;

/**
 * Adapter в системе портов и адаптеров
 * InfrastructureLayer
 */
public final class CollisionEvent implements Event {
    private final AbstractObjectWithCoordinates object;

    private final AbstractObjectWithCoordinates destinationObject;

    public CollisionEvent(AbstractObjectWithCoordinates object, AbstractObjectWithCoordinates destinationObject) {
        this.object = object;
        this.destinationObject = destinationObject;
    }

    public AbstractObjectWithCoordinates getObject() {
        return object;
    }

    public AbstractObjectWithCoordinates getDestinationObject() {
        return destinationObject;
    }
}
