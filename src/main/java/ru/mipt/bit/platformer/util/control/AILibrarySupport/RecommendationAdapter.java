package ru.mipt.bit.platformer.util.control.AILibrarySupport;

import com.badlogic.gdx.math.GridPoint2;
import org.awesome.ai.Recommendation;
import ru.mipt.bit.platformer.util.levels.Level;
import ru.mipt.bit.platformer.util.players.Action;
import ru.mipt.bit.platformer.util.players.TankPlayer;

public class RecommendationAdapter {
    private final TankPlayer tankPlayer;

    private final Action action;

    public RecommendationAdapter(Level level, Recommendation recommendation) {
        GridPoint2 point = new GridPoint2(recommendation.getActor().getX(), recommendation.getActor().getY());
        tankPlayer = level.getRepository().getPlayerByCoords(point);

        action = new ActionAdapter(recommendation.getAction()).getAction();
    }

    public TankPlayer getTankPlayer() {
        return tankPlayer;
    }

    public Action getAction() {
        return action;
    }
}
