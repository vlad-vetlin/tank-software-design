package ru.mipt.bit.platformer.util.views;

import ru.mipt.bit.platformer.util.RenderableObjectWithCoordinates;

/**
 * Port в системе портов и адаптеров
 * ApplicationLayer
 * @param <T>
 */
public interface Renderable<T extends RenderableObjectWithCoordinates> {
    void render();

    void setObject(T object);

    T getObject();
}
