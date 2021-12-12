package ru.mipt.bit.platformer.util.control;

import org.awesome.ai.AI;
import org.awesome.ai.Recommendation;
import org.awesome.ai.strategy.NotRecommendingAI;
import ru.mipt.bit.platformer.util.AbstractMovable;
import ru.mipt.bit.platformer.util.control.AILibrarySupport.GameStateAdapter;
import ru.mipt.bit.platformer.util.control.AILibrarySupport.RecommendationAdapter;
import ru.mipt.bit.platformer.util.levels.Level;
import ru.mipt.bit.platformer.util.players.Action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Port в системе портов и адаптеров
 * ApplicationLayer
 */
public final class SmartAiActionGenerator implements ActionGenerator {
    private final Map<UUID, Action> tankToAction = new HashMap<>();

    private final AI ai;

    public SmartAiActionGenerator() {
        ai = new NotRecommendingAI();
    }

    @Override
    public Action getRecommendation(AbstractMovable movable) {
        return tankToAction.getOrDefault(movable.getId(), Action.Nothing);
    }

    @Override
    public void setNewGameState(Level level) {
        tankToAction.clear();
        List<Recommendation> recommendations = ai.recommend(new GameStateAdapter(level).getGameState());
        for (Recommendation recommendation : recommendations) {
            RecommendationAdapter recommendationAdapter = new RecommendationAdapter(level, recommendation);

            tankToAction.put(recommendationAdapter.getTankPlayer().getId(), recommendationAdapter.getAction());
        }
    }
}
