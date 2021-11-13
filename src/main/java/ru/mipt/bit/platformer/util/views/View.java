package ru.mipt.bit.platformer.util.views;

import ru.mipt.bit.platformer.util.ObjectWithCoordinates;

public interface View {
    void render(ObjectWithCoordinates renderableObject);
}
