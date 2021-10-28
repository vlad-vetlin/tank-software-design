package ru.mipt.bit.platformer.util.control;

import org.awesome.ai.AI;
import org.awesome.ai.Recommendation;
import org.awesome.ai.strategy.NotRecommendingAI;
import ru.mipt.bit.platformer.util.control.AILibrarySupport.GameStateAdapter;
import ru.mipt.bit.platformer.util.control.AILibrarySupport.RecommendationAdapter;
import ru.mipt.bit.platformer.util.levels.Level;

import java.util.List;

public class SmartAiControl implements AllBotsControl {
    private AI ai = new NotRecommendingAI();

    @Override
    public void processMovement(Level level) {
        List<Recommendation> results = ai.recommend(new GameStateAdapter(level).getGameState());

        results.stream()
                .map((recommendation -> new RecommendationAdapter(level, recommendation)))
                .forEach(recommendation -> recommendation.getTankPlayer().move(recommendation.getAction()));
    }
}
