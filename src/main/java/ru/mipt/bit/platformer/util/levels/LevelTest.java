package ru.mipt.bit.platformer.util.levels;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.util.RenderableObjectWithCoordinates;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LevelTest {
    @Test
    void addPlayer() {
        Level level = new Level(new GridPoint2(8, 10), new GridPoint2(1, 1));
        GridPoint2 position = level.getPlayer().getCoordinates();

        assertEquals(1, position.x);
        assertEquals(1, position.y);
    }

    @Test
    void addObstacles() {
        Level level = new Level(
                new GridPoint2(8, 10),
                new GridPoint2(1, 1),
                List.of(
                        new GridPoint2(1, 1),
                        new GridPoint2(2, 3)
                ),
                List.of()
        );

        assertNotNull(level.getObject(new GridPoint2(1, 1)));
        assertNotNull(level.getObject(new GridPoint2(2, 3)));
        assertNull(level.getObject(new GridPoint2(3, 4)));
    }

    @Test
    void addEnemies() {
        Level level = new Level(new GridPoint2(8, 10), new GridPoint2(1, 1), List.of(), List.of(
                new GridPoint2(1, 1),
                new GridPoint2(2, 3)
        ));

        assertNotNull(level.getObject(new GridPoint2(1, 1)));
        assertNotNull(level.getObject(new GridPoint2(2, 3)));
        assertNull(level.getObject(new GridPoint2(3, 4)));
    }

    @Test
    void getRenderableObjects() {
        Level level = new Level(
                new GridPoint2(8, 10),
                new GridPoint2(1, 1),
                List.of(),
                List.of(
                        new GridPoint2(1, 1),
                        new GridPoint2(2, 3)
                )
        );

        Collection<? extends RenderableObjectWithCoordinates> renderableObjects = level.getRenderableObjects();

        assertEquals(3, renderableObjects.size());
    }

    @Test
    void testHasPlayer() {
        Level level = new Level(new GridPoint2(8, 10), new GridPoint2(1, 1));

        assertNotNull(level.getObject(new GridPoint2(1, 1)));
    }
}