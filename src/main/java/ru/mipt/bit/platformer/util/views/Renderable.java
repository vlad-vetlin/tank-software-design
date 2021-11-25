package ru.mipt.bit.platformer.util.views;

import ru.mipt.bit.platformer.util.RenderableObjectWithCoordinates;

public interface Renderable<T extends RenderableObjectWithCoordinates> {
    void render();

    void setObject(T object);

    T getObject();
}
