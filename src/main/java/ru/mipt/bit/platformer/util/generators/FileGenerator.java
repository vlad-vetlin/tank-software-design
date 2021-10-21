package ru.mipt.bit.platformer.util.generators;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.levels.Level;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class FileGenerator implements LevelGenerator {
    private GridPoint2 playerPosition;

    private final Collection<GridPoint2> obstacles;

    private final GridPoint2 bounds;

    public FileGenerator(String path) {
        // Если иксов много - берем последний
        // Если предмет вне размера карты - ок. Танк может просто выехать из тумана войны потом
        obstacles = new ArrayList<>();
        bounds = new GridPoint2();
        int i = 0;
        try(BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                bounds.x = line.length();
                for (int j = 0; j < line.length(); ++j) {
                    if (line.charAt(j) == 'X') {
                        playerPosition = new GridPoint2(j, i);
                    }

                    if (line.charAt(j) == 'T') {
                        obstacles.add(new GridPoint2(j, i));
                    }
                }
                ++i;
            }
        } catch (FileNotFoundException exception) {
            throw new RuntimeException("File not found");
        } catch (IOException ioException) {
            throw new RuntimeException("IOException");
        }

        if (playerPosition == null) {
            throw new RuntimeException("Player not found");
        }

        for (GridPoint2 obstacle : obstacles) {
            obstacle.y = i - obstacle.y - 1;
        }
        playerPosition.y = i - playerPosition.y - 1;
        bounds.y = i;
    }


    @Override
    public Level createLevel() {
        Level level = new Level(bounds);
        level.addPlayer(playerPosition);
        level.addObstacles(obstacles);

        return level;
    }
}
