package ru.mipt.bit.platformer.util.generators;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.util.levels.Level;

import static org.junit.jupiter.api.Assertions.*;

class SimpleRandomGeneratorTest {
    private final LevelGenerator generator = new SimpleRandomGenerator(new GridPoint2(10, 8), 78, 1);

    @Test
    public void createLevelTest() {
        Level level = generator.createLevel();

        assertNotNull(level.getPlayer());

        int counter = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 8; j++) {
                if (level.getObject(new GridPoint2(i, j)) != null) {
                    ++counter;
                }
            }
        }

        // with player
        assertEquals(80, counter);
    }

    @Test
    public void createNotValidLevelTest() {
        Exception exception = assertThrows(
                RuntimeException.class,
                () -> new SimpleRandomGenerator(new GridPoint2(10, 8), 10 * 8, 0).createLevel()
        );

        assertEquals("Too many obstacles", exception.getMessage());
    }
}