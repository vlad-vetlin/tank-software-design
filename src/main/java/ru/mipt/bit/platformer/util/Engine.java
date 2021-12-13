package ru.mipt.bit.platformer.util;

// Only for dependency inversion
public interface Engine {
    void processOneTick(float deltaTime, float speed);

    void subscribeToAllEvents();
}
