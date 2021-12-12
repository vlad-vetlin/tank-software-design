package ru.mipt.bit.platformer.util.players.moveStrategies;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.mipt.bit.platformer.util.levels.Level;
import ru.mipt.bit.platformer.util.players.Tank;

import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

class SimpleMoveStrategyTest {
    @Mock
    private Level level;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testTryMoveDiagonal() {
        MoveStrategy moveStrategy = new SimpleMoveStrategy(level);
        Tank tankPlayer = new Tank(new GridPoint2(1, 1), moveStrategy);

        assertFalse(moveStrategy.canMove(tankPlayer, new GridPoint2(2, 2)));
    }

    @Test
    public void testTryMoveDoubleY() {
        MoveStrategy moveStrategy = new SimpleMoveStrategy(level);
        Tank tankPlayer = new Tank(new GridPoint2(1, 1), moveStrategy);

        assertFalse(moveStrategy.canMove(tankPlayer, new GridPoint2(1, 3)));
    }

    @Test
    public void testTryMoveLessZeroX() {
        MoveStrategy moveStrategy = new SimpleMoveStrategy(level);
        Tank tankPlayer = new Tank(new GridPoint2(0, 0), moveStrategy);

        assertFalse(moveStrategy.canMove(tankPlayer, new GridPoint2(-1, 0)));
    }

    @Test
    public void testTryMoveLessZeroY() {
        MoveStrategy moveStrategy = new SimpleMoveStrategy(level);
        Tank tankPlayer = new Tank(new GridPoint2(0, 0), moveStrategy);

        assertFalse(moveStrategy.canMove(tankPlayer, new GridPoint2(0, -1)));
    }

    @Test
    public void testTryMoveMoreMaxX() {
        MoveStrategy moveStrategy = new SimpleMoveStrategy(level);
        Tank tankPlayer = new Tank(new GridPoint2(1, 1), moveStrategy);

        when(level.getWidth()).thenReturn(1);

        assertFalse(moveStrategy.canMove(tankPlayer, new GridPoint2(2, 1)));
    }


    @Test
    public void testTryMoveMoreMaxY() {
        MoveStrategy moveStrategy = new SimpleMoveStrategy(level);
        Tank tankPlayer = new Tank(new GridPoint2(1, 1), moveStrategy);

        when(level.getHeight()).thenReturn(1);

        assertFalse(moveStrategy.canMove(tankPlayer, new GridPoint2(1, 2)));
    }

    @Test
    public void testTryMoveDoubleX() {
        MoveStrategy moveStrategy = new SimpleMoveStrategy(level);
        Tank tankPlayer = new Tank(new GridPoint2(1, 1), moveStrategy);

        assertFalse(moveStrategy.canMove(tankPlayer, new GridPoint2(3, 1)));
    }
}