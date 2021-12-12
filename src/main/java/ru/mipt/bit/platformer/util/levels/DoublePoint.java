package ru.mipt.bit.platformer.util.levels;

/**
 * Adapter в системе портов и адаптеров
 * InfrastructureLayer
 */
public class DoublePoint {
    private final float x;
    private final float y;

    public DoublePoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getSqrDestination(DoublePoint point) {
        return (x - point.x) * (x - point.x) + (y - point.y) * (y - point.y);
    }
}
