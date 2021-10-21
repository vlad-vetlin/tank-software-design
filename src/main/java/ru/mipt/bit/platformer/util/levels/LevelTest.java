package ru.mipt.bit.platformer.util.levels;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.util.RenderableObject;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LevelTest {
    private final Level level = new Level(new GridPoint2(8, 10));

    @Test
    void addPlayer() {
        level.addPlayer(new GridPoint2(1, 1));

        assertFalse(level.hasObject(new GridPoint2(1, 1)));
        GridPoint2 position = level.getPlayer().getCoordinates();
        assertEquals(1, position.x);
        assertEquals(1, position.y);
    }

    @Test
    void addObstacles() {
        level.addObstacles(List.of(
                new GridPoint2(1, 1),
                new GridPoint2(2, 3)
        ));

        assertTrue(level.hasObject(new GridPoint2(1, 1)));
        assertTrue(level.hasObject(new GridPoint2(2, 3)));
        assertFalse(level.hasObject(new GridPoint2(3, 4)));
    }

    @Test
    void getRenderableObjects() {
        level.addPlayer(new GridPoint2(1, 1));
        level.addObstacles(List.of(
                new GridPoint2(1, 1),
                new GridPoint2(2, 3)
        ));

        Collection<? extends RenderableObject> renderableObjects = level.getRenderableObjects();

        assertEquals(3, renderableObjects.size());
    }
}