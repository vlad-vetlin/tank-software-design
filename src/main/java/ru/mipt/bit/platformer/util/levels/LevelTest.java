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
    void addEnemies() {
        level.addEnemies(List.of(
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

    @Test
    void processMovePlayerToDestination() {
        level.addPlayer(new GridPoint2(1, 1));

        level.getPlayer().moveUp();
        level.processMoveToDestination(1, 1);

        GridPoint2 position = level.getPlayer().getCoordinates();

        assertEquals(new GridPoint2(1, 2), position);
    }

    private boolean shiftOnOne(GridPoint2 point) {
        int count = 0;
        if (level.hasObject(new GridPoint2(1, 0))) {
            ++count;
        }
        if (level.hasObject(new GridPoint2(1, 2))) {
            ++count;
        }
        if (level.hasObject(new GridPoint2(0, 1))) {
            ++count;
        }
        if (level.hasObject(new GridPoint2(2, 1))) {
            ++count;
        }

        return count == 1;
    }

    @Test
    void testProcessMoveEnemyToDestination() {
        level.addEnemies(List.of(new GridPoint2(1, 1)));

        assertTrue(level.hasObject(new GridPoint2(1, 1)));
        level.processAIMovements();
        assertTrue(level.hasObject(new GridPoint2(1, 1)));

        level.processMoveToDestination(1, 1);
        assertFalse(level.hasObject(new GridPoint2(1, 1)));
        assertTrue(shiftOnOne(new GridPoint2(1, 1)));
    }

    @Test
    void testHasPlayer() {
        level.addPlayer(new GridPoint2(1, 1));
        assertTrue(level.hasObject(new GridPoint2(1, 1)));
    }
}