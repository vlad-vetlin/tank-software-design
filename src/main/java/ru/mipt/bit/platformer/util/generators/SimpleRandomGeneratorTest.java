package ru.mipt.bit.platformer.util.generators;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.util.levels.Level;

import static org.junit.jupiter.api.Assertions.*;

class SimpleRandomGeneratorTest {
    private LevelGenerator generator = new SimpleRandomGenerator(new GridPoint2(10, 8), 79);

    @Test
    public void createLevelTest() {
        Level level = generator.createLevel();

        assertNotNull(level.getPlayer());

        int counter = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 8; j++) {
                if (level.hasObstacle(new GridPoint2(i, j))) {
                    ++counter;
                }
            }
        }

        assertEquals(79, counter);
    }

    @Test
    public void createNotValidLevelTest() {
        Exception exception = assertThrows(
                RuntimeException.class,
                () -> {
                    new SimpleRandomGenerator(new GridPoint2(10, 8), 10 * 8).createLevel();
                }
        );

        assertEquals("Too many obstacles", exception.getMessage());
    }
}