package ru.mipt.bit.platformer.util.generators;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.levels.Level;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

public class SimpleRandomGenerator implements LevelGenerator {
    private static final Random random = new Random();

    private final GridPoint2 bounds;

    private final HashSet<GridPoint2> hashSet = new HashSet<>();

    private final int obstaclesCount;

    private final int enemiesCount;

    public SimpleRandomGenerator(GridPoint2 bounds, int obstaclesCount, int enemiesCount) {
        this.bounds = bounds;
        this.obstaclesCount = obstaclesCount;
        this.enemiesCount = enemiesCount;
    }

    private GridPoint2 randomPosition() {
        GridPoint2 position = new GridPoint2(random.nextInt(bounds.x), random.nextInt(bounds.y));

        if (hashSet.size() >= bounds.x * bounds.y) {
            throw new RuntimeException("Too many obstacles");
        }

        while (hashSet.contains(position)) {
            position = new GridPoint2(random.nextInt(bounds.x), random.nextInt(bounds.y));
        }

        hashSet.add(position);

        return position;
    }

    private GridPoint2 generatePlayer() {
        return randomPosition();
    }

    private Collection<GridPoint2> generateObstacles() {
        Collection<GridPoint2> obstacles = new ArrayList<>();

        for (int i = 0; i < obstaclesCount; i++) {
             obstacles.add(randomPosition());
        }

        return obstacles;
    }

    private Collection<GridPoint2> generateEnemies() {
        Collection<GridPoint2> enemies = new ArrayList<>();

        for (int i = 0; i < enemiesCount; ++i) {
            enemies.add(randomPosition());
        }

        return enemies;
    }

    @Override
    public Level createLevel() {
        return new Level(bounds, generatePlayer(), generateObstacles(), generateEnemies());
    }
}
