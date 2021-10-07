package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.Rectangle;

public interface RenderableObject {
    Rectangle getCoordsAsRect();

    float getRotation();
}
