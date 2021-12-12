package ru.mipt.bit.platformer.util.generators;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.util.levels.Level;
import ru.mipt.bit.platformer.util.players.Tank;

import static org.junit.jupiter.api.Assertions.*;

class FileGeneratorTest {

    private final LevelGenerator generator = new FileGenerator("src/main/resources/levels/testLevel");

    @Test
    void CreateLevelTest() {
        Level level = generator.createLevel();
        Tank player = level.getPlayer();

        assertEquals(new GridPoint2(0, 6), player.getCoordinates());

        for (int i = 0; i < 8; ++i) {
            assertNotNull(level.getRepository().getObject(new GridPoint2(i, 0)));
        }

        assertEquals(10, level.getWidth());
        assertEquals(8, level.getHeight());
    }

    @Test
    void CreateLevelTestFileNotFound() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            new FileGenerator("TEST");
        });

        assertEquals("File not found", exception.getMessage());
    }

    @Test
    void CreateLevelTestPlayerNotFound() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            new FileGenerator("src/main/resources/levels/testLevel2");
        });

        assertEquals("Player not found", exception.getMessage());
    }
}