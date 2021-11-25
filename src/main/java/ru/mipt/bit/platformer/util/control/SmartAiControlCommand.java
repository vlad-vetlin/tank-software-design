package ru.mipt.bit.platformer.util.control;

import org.awesome.ai.AI;
import org.awesome.ai.Recommendation;
import org.awesome.ai.strategy.NotRecommendingAI;
import ru.mipt.bit.platformer.util.ControlCommand;
import ru.mipt.bit.platformer.util.control.AILibrarySupport.GameStateAdapter;
import ru.mipt.bit.platformer.util.control.AILibrarySupport.RecommendationAdapter;
import ru.mipt.bit.platformer.util.levels.Level;

import java.util.List;

public class SmartAiControlCommand implements ControlCommand {
    private static final AI ai = new NotRecommendingAI();

    private final Level level;

    public SmartAiControlCommand(Level level) {
        this.level = level;
    }

    @Override
    public void execute() {
        List<Recommendation> results = ai.recommend(new GameStateAdapter(level).getGameState());

        results.stream()
                .map((recommendation -> new RecommendationAdapter(level, recommendation)))
                .forEach(recommendation -> recommendation.getTankPlayer().move(recommendation.getAction()));
    }
}
