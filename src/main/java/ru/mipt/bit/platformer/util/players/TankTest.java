package ru.mipt.bit.platformer.util.players;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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

        tankPlayer.move(Action.MoveNorth);
        tankPlayer.processMoveToDestination(0.1f, 1f);

        assertTrue(tankPlayer.getMovementProgress() > 0);
        assertNotEquals(tankPlayer.getCoordinates(), tankPlayer.getDestinationCoordinates());
    }

    @Test
    void processMoveToDestinationEnd() {
        moveStrategy = mock(MoveStrategy.class);

        Tank tankPlayer = new Tank(new GridPoint2(1, 1), moveStrategy);
        when(moveStrategy.canMove(tankPlayer, new GridPoint2(1, 2))).thenReturn(true);

        tankPlayer.move(Action.MoveNorth);
        tankPlayer.processMoveToDestination(1f, 1f);

        assertEquals(1f, tankPlayer.getMovementProgress(), EPS);
        assertEquals(tankPlayer.getCoordinates(), tankPlayer.getDestinationCoordinates());
    }

    @Test
    void moveUpSuccess() {
        moveStrategy = mock(MoveStrategy.class);

        Tank tankPlayer = new Tank(new GridPoint2(1, 1), moveStrategy);
        when(moveStrategy.canMove(tankPlayer, new GridPoint2(1, 2))).thenReturn(true);

        tankPlayer.move(Action.MoveNorth);
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

        tankPlayer.move(Action.MoveNorth);
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

        tankPlayer.move(Action.MoveEast);
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

        tankPlayer.move(Action.MoveEast);
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

        tankPlayer.move(Action.MoveSouth);
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

        tankPlayer.move(Action.MoveSouth);
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

        tankPlayer.move(Action.MoveWest);
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

        tankPlayer.move(Action.MoveWest);
        GridPoint2 destinationCoords = tankPlayer.getDestinationCoordinates();

        assertEquals(1, destinationCoords.x);
        assertEquals(1, destinationCoords.y);
        assertEquals(-180f, tankPlayer.getRotation(), EPS);
        assertEquals(1f, tankPlayer.getMovementProgress(), EPS);
    }
}