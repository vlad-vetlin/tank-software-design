package ru.mipt.bit.platformer.util.players;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ru.mipt.bit.platformer.util.actions.MoveDownCommand;
import ru.mipt.bit.platformer.util.actions.MoveLeftCommand;
import ru.mipt.bit.platformer.util.actions.MoveRightCommand;
import ru.mipt.bit.platformer.util.actions.MoveUpCommand;
import ru.mipt.bit.platformer.util.players.moveStrategies.MoveStrategy;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

class TankTest {
    @Mock
    MoveStrategy moveStrategy;

    final float EPS = 0.0000001f;

    @Test
    void processMoveToDestination() {
        moveStrategy = mock(MoveStrategy.class);

        Tank tankPlayer = new Tank(new GridPoint2(1, 1), moveStrategy);
        when(moveStrategy.canMove(tankPlayer, new GridPoint2(1, 2))).thenReturn(true);

        new MoveUpCommand(tankPlayer).exec();
        tankPlayer.processMoveToDestination(0.1f, 1f);

        assertTrue(tankPlayer.getMovementProgress() > 0);
        assertNotEquals(tankPlayer.getCoordinates(), tankPlayer.getDestinationCoordinates());
    }

    @Test
    void processMoveToDestinationEnd() {
        moveStrategy = mock(MoveStrategy.class);

        Tank tankPlayer = new Tank(new GridPoint2(1, 1), moveStrategy);
        when(moveStrategy.canMove(tankPlayer, new GridPoint2(1, 2))).thenReturn(true);

        new MoveUpCommand(tankPlayer).exec();
        tankPlayer.processMoveToDestination(1f, 1f);

        assertEquals(1f, tankPlayer.getMovementProgress(), EPS);
        assertEquals(tankPlayer.getCoordinates(), tankPlayer.getDestinationCoordinates());
    }

    @Test
    void moveUpSuccess() {
        moveStrategy = mock(MoveStrategy.class);

        Tank tankPlayer = new Tank(new GridPoint2(1, 1), moveStrategy);
        when(moveStrategy.canMove(tankPlayer, new GridPoint2(1, 2))).thenReturn(true);

        new MoveUpCommand(tankPlayer).exec();
        GridPoint2 destinationCoords = tankPlayer.getDestinationCoordinates();

        assertEquals(1, destinationCoords.x);
        assertEquals(2, destinationCoords.y);
        assertEquals(90f, tankPlayer.getRotation(), EPS);
        assertEquals(0f, tankPlayer.getMovementProgress(), EPS);
    }

    @Test
    void moveUpFail() {
        moveStrategy = mock(MoveStrategy.class);

        Tank tankPlayer = new Tank(new GridPoint2(1, 1), moveStrategy);
        when(moveStrategy.canMove(tankPlayer, new GridPoint2(1, 2))).thenReturn(false);

        new MoveUpCommand(tankPlayer).exec();
        GridPoint2 destinationCoords = tankPlayer.getDestinationCoordinates();

        assertEquals(1, destinationCoords.x);
        assertEquals(1, destinationCoords.y);
        assertEquals(90f, tankPlayer.getRotation(), EPS);
        assertEquals(1f, tankPlayer.getMovementProgress(), EPS);
    }

    @Test
    void moveRightSuccess() {
        moveStrategy = mock(MoveStrategy.class);

        Tank tankPlayer = new Tank(new GridPoint2(1, 1), moveStrategy);
        when(moveStrategy.canMove(tankPlayer, new GridPoint2(2, 1))).thenReturn(true);

        new MoveRightCommand(tankPlayer).exec();
        GridPoint2 destinationCoords = tankPlayer.getDestinationCoordinates();

        assertEquals(2, destinationCoords.x);
        assertEquals(1, destinationCoords.y);
        assertEquals(0f, tankPlayer.getRotation(), EPS);
        assertEquals(0f, tankPlayer.getMovementProgress(), EPS);
    }

    @Test
    void moveRightFail() {
        moveStrategy = mock(MoveStrategy.class);

        Tank tankPlayer = new Tank(new GridPoint2(1, 1), moveStrategy);
        when(moveStrategy.canMove(tankPlayer, new GridPoint2(2, 1))).thenReturn(false);

        new MoveRightCommand(tankPlayer).exec();
        GridPoint2 destinationCoords = tankPlayer.getDestinationCoordinates();

        assertEquals(1, destinationCoords.x);
        assertEquals(1, destinationCoords.y);
        assertEquals(0f, tankPlayer.getRotation(), EPS);
        assertEquals(1f, tankPlayer.getMovementProgress(), EPS);
    }

    @Test
    void moveDownSuccess() {
        moveStrategy = mock(MoveStrategy.class);

        Tank tankPlayer = new Tank(new GridPoint2(1, 1), moveStrategy);
        when(moveStrategy.canMove(tankPlayer, new GridPoint2(1, 0))).thenReturn(true);

        new MoveDownCommand(tankPlayer).exec();
        GridPoint2 destinationCoords = tankPlayer.getDestinationCoordinates();

        assertEquals(1, destinationCoords.x);
        assertEquals(0, destinationCoords.y);
        assertEquals(-90f, tankPlayer.getRotation(), EPS);
        assertEquals(0f, tankPlayer.getMovementProgress(), EPS);
    }

    @Test
    void moveDownFail() {
        moveStrategy = mock(MoveStrategy.class);

        Tank tankPlayer = new Tank(new GridPoint2(1, 1), moveStrategy);
        when(moveStrategy.canMove(tankPlayer, new GridPoint2(1, 0))).thenReturn(false);

        new MoveDownCommand(tankPlayer).exec();
        GridPoint2 destinationCoords = tankPlayer.getDestinationCoordinates();

        assertEquals(1, destinationCoords.x);
        assertEquals(1, destinationCoords.y);
        assertEquals(-90f, tankPlayer.getRotation(), EPS);
        assertEquals(1f, tankPlayer.getMovementProgress(), EPS);
    }

    @Test
    void moveLeftSuccess() {
        moveStrategy = mock(MoveStrategy.class);

        Tank tankPlayer = new Tank(new GridPoint2(1, 1), moveStrategy);
        when(moveStrategy.canMove(tankPlayer, new GridPoint2(0, 1))).thenReturn(true);

        new MoveLeftCommand(tankPlayer).exec();
        GridPoint2 destinationCoords = tankPlayer.getDestinationCoordinates();

        assertEquals(0, destinationCoords.x);
        assertEquals(1, destinationCoords.y);
        assertEquals(-180f, tankPlayer.getRotation(), EPS);
        assertEquals(0f, tankPlayer.getMovementProgress(), EPS);
    }

    @Test
    void moveLeftFail() {
        moveStrategy = mock(MoveStrategy.class);

        Tank tankPlayer = new Tank(new GridPoint2(1, 1), moveStrategy);
        when(moveStrategy.canMove(tankPlayer, new GridPoint2(0, 1))).thenReturn(false);

        new MoveLeftCommand(tankPlayer).exec();
        GridPoint2 destinationCoords = tankPlayer.getDestinationCoordinates();

        assertEquals(1, destinationCoords.x);
        assertEquals(1, destinationCoords.y);
        assertEquals(-180f, tankPlayer.getRotation(), EPS);
        assertEquals(1f, tankPlayer.getMovementProgress(), EPS);
    }
}