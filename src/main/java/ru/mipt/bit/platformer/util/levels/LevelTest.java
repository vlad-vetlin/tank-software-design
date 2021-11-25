package ru.mipt.bit.platformer.util.levels;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ru.mipt.bit.platformer.util.AbstractObjectWithCoordinates;
import ru.mipt.bit.platformer.util.control.SmartAiControlCommand;
import ru.mipt.bit.platformer.util.players.Action;
import ru.mipt.bit.platformer.util.players.TankPlayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LevelTest {
    @Mock
    private SmartAiControlCommand aiControl;

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

        assertTrue(level.getRepository().hasObject(new GridPoint2(1, 1)));
        assertTrue(level.getRepository().hasObject(new GridPoint2(2, 3)));
        assertFalse(level.getRepository().hasObject(new GridPoint2(3, 4)));
    }

    @Test
    void addEnemies() {
        Level level = new Level(new GridPoint2(8, 10), new GridPoint2(1, 1), List.of(), List.of(
                new GridPoint2(1, 1),
                new GridPoint2(2, 3)
        ));

        assertTrue(level.getRepository().hasObject(new GridPoint2(1, 1)));
        assertTrue(level.getRepository().hasObject(new GridPoint2(2, 3)));
        assertFalse(level.getRepository().hasObject(new GridPoint2(3, 4)));
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

        Collection<? extends AbstractObjectWithCoordinates> renderableObjects = level.getRepository().getRenderableObjects();

        assertEquals(3, renderableObjects.size());
    }

    @Test
    void processMovePlayerToDestination() {
        Level level = new Level(new GridPoint2(8, 10), new GridPoint2(1, 1));

        level.getPlayer().move(Action.MoveNorth);
        level.processOneTick(1, 1);

        GridPoint2 position = level.getPlayer().getCoordinates();

        assertEquals(new GridPoint2(1, 2), position);
    }

    @Test
    void testProcessMoveEnemyToDestination() {
        aiControl = mock(SmartAiControlCommand.class);

        Level level = new Level(
                new GridPoint2(8, 10),
                new GridPoint2(1, 1),
                List.of(),
                List.of(new GridPoint2(2, 2))
        );
        ObjectsRepository repository = level.getRepository();

        doAnswer(invocation -> {
            List<TankPlayer> bots = new ArrayList<>(repository.getEnemies());
            bots.get(0).move(Action.MoveNorth);

            return null;
        }).when(aiControl).execute();

        assertTrue(repository.hasObject(new GridPoint2(2, 2)));
        level.processAIMovements(aiControl);
        assertTrue(repository.hasObject(new GridPoint2(2, 2)));

        level.processOneTick(1, 1);
        assertFalse(repository.hasObject(new GridPoint2(2, 2)));
        assertTrue(repository.hasObject(new GridPoint2(2, 3)));
    }

    @Test
    void testHasPlayer() {
        Level level = new Level(new GridPoint2(8, 10), new GridPoint2(1, 1));

        assertTrue(level.getRepository().hasObject(new GridPoint2(1, 1)));
    }
}