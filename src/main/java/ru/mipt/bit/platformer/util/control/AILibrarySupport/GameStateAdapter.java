package ru.mipt.bit.platformer.util.control.AILibrarySupport;

import org.awesome.ai.state.GameState;
import org.awesome.ai.state.immovable.Obstacle;
import org.awesome.ai.state.movable.Bot;
import ru.mipt.bit.platformer.util.Movable;
import ru.mipt.bit.platformer.util.levels.Level;
import ru.mipt.bit.platformer.util.levels.ObjectsRepository;

import java.util.List;
import java.util.stream.Collectors;

public class GameStateAdapter {
    private final GameState gameState;

    public GameStateAdapter(Level level) {
        GameState.GameStateBuilder builder = GameState.builder();

        ObjectsRepository repository = level.getRepository();

        List<Obstacle> obstacles = repository
                .getObstacles().
                stream().
                map((obstacle) -> new ObstacleAdapter(obstacle).getObstacle())
                .collect(Collectors.toList());

        List<Bot> bots = repository
                .getEnemies()
                .stream()
                .map((enemy) -> new BotAdapter(enemy).getBot())
                .collect(Collectors.toList());

        builder.obstacles(obstacles);
        builder.bots(bots);
        builder.player(new PlayerAdapter(level.getPlayer()).getPlayer());
        builder.levelHeight(level.getHeight()).levelWidth(level.getWidth());

        gameState = builder.build();
    }

    public GameState getGameState() {
        return gameState;
    }
}
