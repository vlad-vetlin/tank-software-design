package ru.mipt.bit.platformer.util.views;

/**
 * Port в системе портов и адаптеров
 * ApplicationLayer
 * @param <T>
 */
public interface View<T> {
    void render(T renderableObject);
}
