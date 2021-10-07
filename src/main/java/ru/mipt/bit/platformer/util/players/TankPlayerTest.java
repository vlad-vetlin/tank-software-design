package ru.mipt.bit.platformer.util.players;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ru.mipt.bit.platformer.util.players.moveStrategies.MoveStrategy;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

class TankPlayerTest {

    @Mock
    MoveStrategy moveStrategy;

    final float EPS = 0.0000001f;

    @Test
    void processMoveToDestination() {
        moveStrategy = mock(MoveStrategy.class);

        TankPlayer tankPlayer = new TankPlayer(new GridPoint2(1, 1));
        when(moveStrategy.canMove(tankPlayer, new GridPoint2(1, 2))).thenReturn(true);

        tankPlayer.setMoveStrategy(moveStrategy);

        tankPlayer.moveUp();
        tankPlayer.processMoveToDestination(0.1f, 1f);

        assertTrue(tankPlayer.getMovementProgress() > 0);
        assertNotEquals(tankPlayer.getCoordinates(), tankPlayer.getDestinationCoordinates());
    }

    @Test
    void processMoveToDestinationEnd() {
        moveStrategy = mock(MoveStrategy.class);

        TankPlayer tankPlayer = new TankPlayer(new GridPoint2(1, 1));
        when(moveStrategy.canMove(tankPlayer, new GridPoint2(1, 2))).thenReturn(true);

        tankPlayer.setMoveStrategy(moveStrategy);

        tankPlayer.moveUp();
        tankPlayer.processMoveToDestination(1f, 1f);

        assertEquals(1f, tankPlayer.getMovementProgress(), EPS);
        assertEquals(tankPlayer.getCoordinates(), tankPlayer.getDestinationCoordinates());
    }

    @Test
    void moveUpSuccess() {
        moveStrategy = mock(MoveStrategy.class);

        TankPlayer tankPlayer = new TankPlayer(new GridPoint2(1, 1));
        when(moveStrategy.canMove(tankPlayer, new GridPoint2(1, 2))).thenReturn(true);

        tankPlayer.setMoveStrategy(moveStrategy);

        tankPlayer.moveUp();
        GridPoint2 destinationCoords = tankPlayer.getDestinationCoordinates();

        assertEquals(1, destinationCoords.x);
        assertEquals(2, destinationCoords.y);
        assertEquals(90f, tankPlayer.getRotation(), EPS);
        assertEquals(0f, tankPlayer.getMovementProgress(), EPS);
    }

    @Test
    void moveUpFail() {
        moveStrategy = mock(MoveStrategy.class);

        TankPlayer tankPlayer = new TankPlayer(new GridPoint2(1, 1));
        when(moveStrategy.canMove(tankPlayer, new GridPoint2(1, 2))).thenReturn(false);

        tankPlayer.setMoveStrategy(moveStrategy);

        tankPlayer.moveUp();
        GridPoint2 destinationCoords = tankPlayer.getDestinationCoordinates();

        assertEquals(1, destinationCoords.x);
        assertEquals(1, destinationCoords.y);
        assertEquals(90f, tankPlayer.getRotation(), EPS);
        assertEquals(1f, tankPlayer.getMovementProgress(), EPS);
    }

    @Test
    void moveRightSuccess() {
        moveStrategy = mock(MoveStrategy.class);

        TankPlayer tankPlayer = new TankPlayer(new GridPoint2(1, 1));
        when(moveStrategy.canMove(tankPlayer, new GridPoint2(2, 1))).thenReturn(true);

        tankPlayer.setMoveStrategy(moveStrategy);

        tankPlayer.moveRight();
        GridPoint2 destinationCoords = tankPlayer.getDestinationCoordinates();

        assertEquals(2, destinationCoords.x);
        assertEquals(1, destinationCoords.y);
        assertEquals(0f, tankPlayer.getRotation(), EPS);
        assertEquals(0f, tankPlayer.getMovementProgress(), EPS);
    }

    @Test
    void moveRightFail() {
        moveStrategy = mock(MoveStrategy.class);

        TankPlayer tankPlayer = new TankPlayer(new GridPoint2(1, 1));
        when(moveStrategy.canMove(tankPlayer, new GridPoint2(2, 1))).thenReturn(false);

        tankPlayer.setMoveStrategy(moveStrategy);

        tankPlayer.moveRight();
        GridPoint2 destinationCoords = tankPlayer.getDestinationCoordinates();

        assertEquals(1, destinationCoords.x);
        assertEquals(1, destinationCoords.y);
        assertEquals(0f, tankPlayer.getRotation(), EPS);
        assertEquals(1f, tankPlayer.getMovementProgress(), EPS);
    }

    @Test
    void moveDownSuccess() {
        moveStrategy = mock(MoveStrategy.class);

        TankPlayer tankPlayer = new TankPlayer(new GridPoint2(1, 1));
        when(moveStrategy.canMove(tankPlayer, new GridPoint2(1, 0))).thenReturn(true);

        tankPlayer.setMoveStrategy(moveStrategy);

        tankPlayer.moveDown();
        GridPoint2 destinationCoords = tankPlayer.getDestinationCoordinates();

        assertEquals(1, destinationCoords.x);
        assertEquals(0, destinationCoords.y);
        assertEquals(-90f, tankPlayer.getRotation(), EPS);
        assertEquals(0f, tankPlayer.getMovementProgress(), EPS);
    }

    @Test
    void moveDownFail() {
        moveStrategy = mock(MoveStrategy.class);

        TankPlayer tankPlayer = new TankPlayer(new GridPoint2(1, 1));
        when(moveStrategy.canMove(tankPlayer, new GridPoint2(1, 0))).thenReturn(false);

        tankPlayer.setMoveStrategy(moveStrategy);

        tankPlayer.moveDown();
        GridPoint2 destinationCoords = tankPlayer.getDestinationCoordinates();

        assertEquals(1, destinationCoords.x);
        assertEquals(1, destinationCoords.y);
        assertEquals(-90f, tankPlayer.getRotation(), EPS);
        assertEquals(1f, tankPlayer.getMovementProgress(), EPS);
    }

    @Test
    void moveLeftSuccess() {
        moveStrategy = mock(MoveStrategy.class);

        TankPlayer tankPlayer = new TankPlayer(new GridPoint2(1, 1));
        when(moveStrategy.canMove(tankPlayer, new GridPoint2(0, 1))).thenReturn(true);

        tankPlayer.setMoveStrategy(moveStrategy);

        tankPlayer.moveLeft();
        GridPoint2 destinationCoords = tankPlayer.getDestinationCoordinates();

        assertEquals(0, destinationCoords.x);
        assertEquals(1, destinationCoords.y);
        assertEquals(-180f, tankPlayer.getRotation(), EPS);
        assertEquals(0f, tankPlayer.getMovementProgress(), EPS);
    }

    @Test
    void moveLeftFail() {
        moveStrategy = mock(MoveStrategy.class);

        TankPlayer tankPlayer = new TankPlayer(new GridPoint2(1, 1));
        when(moveStrategy.canMove(tankPlayer, new GridPoint2(0, 1))).thenReturn(false);

        tankPlayer.setMoveStrategy(moveStrategy);

        tankPlayer.moveLeft();
        GridPoint2 destinationCoords = tankPlayer.getDestinationCoordinates();

        assertEquals(1, destinationCoords.x);
        assertEquals(1, destinationCoords.y);
        assertEquals(-180f, tankPlayer.getRotation(), EPS);
        assertEquals(1f, tankPlayer.getMovementProgress(), EPS);
    }
}