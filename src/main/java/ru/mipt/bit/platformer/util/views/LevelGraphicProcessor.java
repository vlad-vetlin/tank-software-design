package ru.mipt.bit.platformer.util.views;

import ru.mipt.bit.platformer.util.ObjectEventManager;
import ru.mipt.bit.platformer.util.RenderableObjectWithCoordinates;
import ru.mipt.bit.platformer.util.levels.Level;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class LevelGraphicProcessor {
    private final LevelGraphics levelGraphics;

    private final Map<UUID, RenderableObjectWithCoordinates> objects;

    public LevelGraphicProcessor(LevelGraphics graphics, Level level, ObjectEventManager observer) {
        levelGraphics = graphics;

        objects = new HashMap<>();

        List<RenderableObjectWithCoordinates> renderableObjects = level.getRepository().getRenderableObjects();
        for (RenderableObjectWithCoordinates objectWithCoordinates : renderableObjects) {
            objects.put(objectWithCoordinates.getId(), objectWithCoordinates);
        }

        subscribeToAll(observer);
    }

    public void render() {
        levelGraphics.render(objects.values());
    }

    private void subscribeToAll(ObjectEventManager observer) {
        UUID uuid = UUID.randomUUID();

        observer.subscribe(uuid, "RemovePlayer", player -> {
            System.out.println("Game over!");
        });

        observer.subscribe(uuid, "RemoveBullet", bullet -> {
            objects.remove(bullet.getId());
        });

        observer.subscribe(uuid, "AddBullet", bullet -> {
            objects.put(bullet.getId(), bullet);
        });

        observer.subscribe(uuid, "RemoveEnemy", enemy -> {
            objects.remove(enemy.getId());
        });
    }
}
