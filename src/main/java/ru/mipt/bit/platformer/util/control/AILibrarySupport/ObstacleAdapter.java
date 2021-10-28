package ru.mipt.bit.platformer.util.control.AILibrarySupport;

import com.badlogic.gdx.math.GridPoint2;
import org.awesome.ai.state.immovable.Obstacle;
import ru.mipt.bit.platformer.util.RenderableObject;

public class ObstacleAdapter {
    private final Obstacle obstacle;

    public ObstacleAdapter(RenderableObject renderableObstacle) {
        GridPoint2 point = renderableObstacle.getCoordinates();
        obstacle = new Obstacle(point.x, point.y);
    }

    public Obstacle getObstacle() {
        return obstacle;
    }
}
