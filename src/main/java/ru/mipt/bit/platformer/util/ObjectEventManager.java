package ru.mipt.bit.platformer.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class ObjectEventManager {
    Map<String, Map<UUID, Consumer<AbstractObjectWithCoordinates>>> eventNameToConsumers = new HashMap<>();

    public void subscribe(UUID listener, String event, Consumer<AbstractObjectWithCoordinates> consumer) {
        eventNameToConsumers.putIfAbsent(event, new HashMap<>());
        eventNameToConsumers.get(event).put(listener, consumer);
    }

    public void unsubscribe(UUID listener, String event) {
        if (eventNameToConsumers.containsKey(event)) {
            eventNameToConsumers.get(event).remove(listener);
        }
    }

    public void notify(String event, AbstractObjectWithCoordinates object) {
        if (eventNameToConsumers.containsKey(event)) {
            Collection<Consumer<AbstractObjectWithCoordinates>> consumers = eventNameToConsumers.get(event).values();
            for (var consumer : consumers) {
                consumer.accept(object);
            }
        }
    }
}
