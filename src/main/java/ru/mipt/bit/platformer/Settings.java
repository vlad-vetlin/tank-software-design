package ru.mipt.bit.platformer;

import ru.mipt.bit.platformer.util.control.*;
import ru.mipt.bit.platformer.util.levels.Level;
import ru.mipt.bit.platformer.util.players.moveStrategies.BulletMoveStrategy;
import ru.mipt.bit.platformer.util.players.moveStrategies.MoveStrategy;
import ru.mipt.bit.platformer.util.players.moveStrategies.SimpleMoveStrategy;


/**
 * Adapter в системе портов и адаптеров
 * InfrastructureLayer
 */
public class Settings {
    public static final ActionGenerator PLAYER_ACTION_GENERATOR = new KeyboardActionGenerator();

    public static final ActionGenerator ENEMY_ACTION_GENERATOR = new RandomActionGenerator();

    public static final ActionGenerator BULLET_ACTION_GENERATOR = new MoveForwardActionGenerator();

    public static void updateActionGenerators(Level level) {
        PLAYER_ACTION_GENERATOR.setNewGameState(level);
        ENEMY_ACTION_GENERATOR.setNewGameState(level);
        BULLET_ACTION_GENERATOR.setNewGameState(level);
    }

    public static final MoveStrategy TANK_MOVE_STRATEGY = new SimpleMoveStrategy(null);

    public static final MoveStrategy BULLET_MOVE_STRATEGY = new BulletMoveStrategy(null);

    public static void setLevelToMoveStrategies(Level level) {
        TANK_MOVE_STRATEGY.setLevel(level);
        BULLET_MOVE_STRATEGY.setLevel(level);
    }
}
