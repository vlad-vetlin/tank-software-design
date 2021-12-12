package ru.mipt.bit.platformer.util.control.AILibrarySupport;

import org.awesome.ai.state.movable.Orientation;

/**
 * Adapter в системе портов и адаптеров
 * Application layer
 */
public class OrientationAdapter {
    private final Orientation orientation;

    private boolean isEqual(float lhs, float rhs) {
        float EPS = 1e-7f;
        return Math.abs(lhs - rhs) < EPS;
    }

    public OrientationAdapter(float rotation) {
        if (isEqual(rotation, 90f)) {
            orientation = Orientation.N;
        } else if (isEqual(rotation, 0f)) {
            orientation = Orientation.E;
        } else if (isEqual(rotation, -180f)) {
            orientation = Orientation.W;
        } else {
            orientation = Orientation.S;
        }
    }

    public Orientation getOrientation() {
        return orientation;
    }
}
