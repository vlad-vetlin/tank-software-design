package ru.mipt.bit.platformer.util.views;

import ru.mipt.bit.platformer.util.events.CreateEvent;
import ru.mipt.bit.platformer.util.events.ObjectEventManager;
import ru.mipt.bit.platformer.util.RenderableObjectWithCoordinates;
import ru.mipt.bit.platformer.util.events.RemoveEvent;
import ru.mipt.bit.platformer.util.levels.Level;
import ru.mipt.bit.platformer.util.obstacles.Bullet;
import ru.mipt.bit.platformer.util.players.Tank;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class LevelGraphicProcessor {
    private final LevelGraphics levelGraphics;

    private final Map<UUID, RenderableObjectWithCoordinates> objects;

    public LevelGraphicProcessor(LevelGraphics graphics, Level level) {
        levelGraphics = graphics;

        objects = new HashMap<>();

        List<RenderableObjectWithCoordinates> renderableObjects = level.getRenderableObjects();
        for (RenderableObjectWithCoordinates objectWithCoordinates : renderableObjects) {
            objects.put(objectWithCoordinates.getId(), objectWithCoordinates);
        }

        subscribeToAll();
    }

    public void render() {
        levelGraphics.render(objects.values());
    }

    private void subscribeToAll() {
        UUID uuid = UUID.randomUUID();

        ObjectEventManager.subscribe(uuid, ObjectEventManager.getEventName(Bullet.class, RemoveEvent.class), event -> {
            RemoveEvent removeEvent = (RemoveEvent) event;
            objects.remove(removeEvent.getObject().getId());
        });

        ObjectEventManager.subscribe(uuid, ObjectEventManager.getEventName(Bullet.class, CreateEvent.class), event -> {
            CreateEvent createEvent = (CreateEvent) event;
            objects.put(createEvent.getObject().getId(), createEvent.getObject());
        });

        ObjectEventManager.subscribe(uuid, ObjectEventManager.getEventName(Tank.class, RemoveEvent.class), event -> {
            RemoveEvent removeEvent = (RemoveEvent) event;
            objects.remove(removeEvent.getObject().getId());
        });
    }
}
