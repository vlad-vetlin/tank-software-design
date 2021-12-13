package ru.mipt.bit.platformer.util.control.AILibrarySupport;

import com.badlogic.gdx.math.GridPoint2;
import org.awesome.ai.Recommendation;
import ru.mipt.bit.platformer.util.actions.*;
import ru.mipt.bit.platformer.util.levels.Level;
import ru.mipt.bit.platformer.util.players.Tank;

/**
 * Adapter в системе портов и адаптеров
 * Application layer
 */
public class RecommendationAdapter {
    private final ActionCommand action;

    private final Tank tank;

    public RecommendationAdapter(Level level, Recommendation recommendation) {
        GridPoint2 point = new GridPoint2(recommendation.getActor().getX(), recommendation.getActor().getY());
        tank = level.getPlayerByCoords(point);

        switch (recommendation.getAction()) {
            case MoveNorth:
                this.action = new MoveUpCommand(tank);
                break;
            case MoveEast:
                this.action = new MoveRightCommand(tank);
                break;
            case MoveSouth:
                this.action = new MoveDownCommand(tank);
                break;
            case MoveWest:
                this.action = new MoveLeftCommand(tank);
                break;
            case Shoot:
            default:
                this.action = new FireCommand(tank);
        }
    }

    public Tank getTank() {
        return tank;
    }

    public ActionCommand getAction() {
        return action;
    }
}
