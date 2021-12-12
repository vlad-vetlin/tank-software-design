package ru.mipt.bit.platformer.util.events;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Adapter в системе портов и адаптеров
 * InfrastructureLayer
 */
public final class ObjectEventManager {
    private static ObjectEventManager instance;

    Map<String, Map<UUID, Consumer<Event>>> eventNameToConsumers = new HashMap<>();

    private ObjectEventManager() {}

    public static ObjectEventManager getInstance() {
        if (instance == null) {
            return instance = new ObjectEventManager();
        }

        return instance;
    }

    public static String getEventName(Class<?> object, Class<?> event) {
        return object.getName() + event.getName();
    }

    public static void subscribe(UUID listener, String event, Consumer<Event> consumer) {
        ObjectEventManager instance = getInstance();
        instance.eventNameToConsumers.putIfAbsent(event, new HashMap<>());
        instance.eventNameToConsumers.get(event).put(listener, consumer);
    }

    public static void notifyEvent(Class<?> notifiedObject, Event event) {
        ObjectEventManager instance = getInstance();
        String eventName = getEventName(notifiedObject, event.getClass());

        if (instance.eventNameToConsumers.containsKey(eventName)) {
            Collection<Consumer<Event>> consumers = instance.eventNameToConsumers.get(eventName).values();
            for (var consumer : consumers) {
                consumer.accept(event);
            }
        }
    }
}
